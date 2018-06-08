package com.epam.eps.framework.core;

import com.epam.eps.EmergencyPreventionSystem;
import com.epam.eps.framework.support.EpsBeanProcessor;
import com.epam.eps.framework.support.risk.Max;
import com.epam.eps.framework.support.risk.Min;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.MissingResourceException;

import java.util.ResourceBundle;
import java.util.Set;

public class EpsResourceBundleAnnotationContext implements EpsContext {
	static final String CONFIG_BEANS_DELIMITER = ",\\s?";
	ResourceBundle bundleConfig;
	Map<String, EpsBeanProcessor> epsCommonBeanProcessors;
	Map<String, EpsBeanProcessor> epsSpecialBeanProcessors;
	Map<String, Object> epsCommonBeanDefinitions;
	Set<String> riskZones;

	private final static Logger logger = Logger.getLogger(EpsResourceBundleAnnotationContext.class);

	EpsResourceBundleAnnotationContext() {
	}

	public EpsResourceBundleAnnotationContext(String baseName) {
		logger.warn("Getting the config file");
		bundleConfig = ResourceBundle.getBundle(baseName);
		logger.info("Creating collections processors, definitions and risk zones");
		epsCommonBeanProcessors = new LinkedHashMap<>();
		epsSpecialBeanProcessors = new LinkedHashMap<>();
		epsCommonBeanDefinitions = new LinkedHashMap<>();
		riskZones = new HashSet<>();
	}

	@Override
	public void init() {
		logger.info("Registered bean processors");
		registeredEpsBeanProcessors();
		logger.warn("Registered bean definitions");
		registeredEpsBeanDefinitions();
		logger.info("The tuning of definitions in common beans");
		tuningEpsBeans(epsCommonBeanProcessors);
		logger.info("The tuning of definitions in special beans");
		tuningEpsBeans(epsSpecialBeanProcessors);
		logger.info("Calling methods with the \"Init\" annotation");
		doInit();
	}

	void doInit() {
		for (Object bean : epsCommonBeanDefinitions.values()) {
			for (Method method : bean.getClass().getDeclaredMethods()) {
				if (method.isAnnotationPresent(Init.class)) {
					try {
						method.setAccessible(true);
						method.invoke(bean);
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						logger.error("Initialization is not possible. Exception: " + e.toString());
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void registeredEpsBeanProcessors() {
		logger.warn("Registered common bean processors");
		registeredEpsCommonBeanProcessors();
		logger.warn("Registered special bean processors");
		registeredEpsSpecialBeanProcessors();
	}

	void registeredEpsSpecialBeanProcessors() {
		String specialDefinitionsList = bundleConfig
				.getString("eps.bean.processors.special");
		epsSpecialBeanProcessors
				.putAll(generateBeansFromDefinitions(specialDefinitionsList));
	}

	void registeredEpsCommonBeanProcessors() {
		String commonDefinitionsList = bundleConfig
				.getString("eps.bean.processors.common");
		epsCommonBeanProcessors
				.putAll(generateBeansFromDefinitions(commonDefinitionsList));
	}

	private Map<String, EpsBeanProcessor> generateBeansFromDefinitions(
			String definitionsList) {
		Map<String, EpsBeanProcessor> epsBeans = new LinkedHashMap<>();
		String[] epsBeanDefinitionsClassNames = definitionsList
				.split(CONFIG_BEANS_DELIMITER);
		for (String epsBeanDefinitionsClassName : epsBeanDefinitionsClassNames) {
			if (!epsBeanDefinitionsClassName.isEmpty()) {
			    logger.info("Generate processor - " + epsBeanDefinitionsClassName);
				epsBeans.put(epsBeanDefinitionsClassName, getObject(
						epsBeanDefinitionsClassName, EpsBeanProcessor.class));
			}
		}
		return epsBeans;
	}

	void registeredEpsBeanDefinitions() {
		String beansList = bundleConfig.getString("eps.bean.definitions");
		String[] epsBeansClassNames = beansList.split(CONFIG_BEANS_DELIMITER);
		for (String epsBeansClassName : epsBeansClassNames) {
			if (!epsBeansClassName.isEmpty()) {
                logger.info("Generate definition - " + epsBeansClassName);
				epsCommonBeanDefinitions.put(epsBeansClassName,
						getObject(epsBeansClassName, Object.class));
			}
		}
	}

	void tuningEpsBeans(Map<String, EpsBeanProcessor> epsBeanProcessors) {
		Set<Entry<String, EpsBeanProcessor>> processors = epsBeanProcessors
				.entrySet();
		Set<Entry<String, Object>> beans = epsCommonBeanDefinitions.entrySet();
		for (Entry<String, EpsBeanProcessor> processorEntry : processors) {
			for (Entry<String, Object> beanEntry : beans) {
				Object processedBean = beanEntry.getValue();
                logger.info("Turning definition \"" + processedBean.getClass().getSimpleName() +
                        "\" in \"" + processorEntry.getValue().getClass().getSimpleName() + "\" processor");
				processedBean = processorEntry.getValue().process(processedBean,
						this);
				beanEntry.setValue(processedBean);
			}
		}
	}

	@Override
	public Object getEPSBean(String id) throws IllegalArgumentException {
		String beanClassName;
		try {
			beanClassName = bundleConfig.getString(id);
			if (!epsCommonBeanDefinitions.containsKey(beanClassName)) {
			    logger.error(String.format("Hasn't object with id %s.", id));
				throw new IllegalArgumentException(
						String.format("Hasn't object with id %s.", id));

			}
		} catch (MissingResourceException e) {
            logger.error(String.format(
                    "Hasn't definition of object with id \"%s\" in config-file \"%s\".",
                    id, bundleConfig.getBaseBundleName()) + " Exception" + e.toString());
			throw new IllegalArgumentException(String.format(
					"Hasn't definition of object with id \"%s\" in config-file \"%s\".",
					id, bundleConfig.getBaseBundleName()), e);
		}
		return epsCommonBeanDefinitions.get(beanClassName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEPSBean(Class<T> type) throws IllegalArgumentException {
		Set<Entry<String, Object>> entrySet = epsCommonBeanDefinitions
				.entrySet();
		for (Entry<String, Object> definitionEntry : entrySet) {
			Object bean = definitionEntry.getValue();
			if (type.isInstance(bean)) {
				return (T) bean;
			}
		}
        logger.error("Hasn't object of class " + type);
		throw new IllegalArgumentException("Hasn't object of class " + type);
	}

	@SuppressWarnings("unchecked")
	private <T> T getObject(String className, Class<T> type) {
		T instance = null;
		try {
			instance = (T) Class.forName(className).newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
            logger.error("Error in getting object. Exception " + e.toString());
			e.printStackTrace();
		}
		return instance;
	}

	@Override
	public Map<String, EpsBeanProcessor> getEpsBeanProcessors() {
		return epsCommonBeanProcessors;
	}

	@Override
	public Map<String, Object> getEpsBeanDefinitions() {
		return epsCommonBeanDefinitions;
	}

	@Override
	public void addRiskZone(String riskZoneId) {
		riskZones.add(riskZoneId);
	}

	@Override
	public void takeAccountGroup(Group group) {
		for (String riskZoneId : riskZones) {
			Object bean = epsCommonBeanDefinitions
					.get(bundleConfig.getString(riskZoneId));
			Class<?> beanClass = bean.getClass();
			int min = -1;
			int max = -1;
			for (Field field : beanClass.getDeclaredFields()) {
				if (field.isAnnotationPresent(Min.class)) {
					min = field.getAnnotation(Min.class).value();
				} else if (field.isAnnotationPresent(Max.class)) {
					max = field.getAnnotation(Max.class).value();
				}
			}
			if (group.getCells().size() >= min
					&& group.getCells().size() <= max) {
				Method[] methods = bean.getClass().getDeclaredMethods();
				for (Method method : methods) {
					if (method.isAnnotationPresent(AddAnotherOneGroup.class)) {
						try {
							method.invoke(bean, group);
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException e) {
                            logger.error("Error in adding a group. Exception " + e.toString());
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
