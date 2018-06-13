package com.epam.eps.framework.support.inject;

import com.epam.eps.framework.core.EpsContext;
import com.epam.eps.framework.core.EpsResourceBundleAnnotationContext;
import com.epam.eps.framework.support.EpsBeanProcessor;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;

public class InjectBeanProcessor implements EpsBeanProcessor {
	private final static Logger logger = Logger.getLogger(InjectBeanProcessor.class);

	@Override
	public Object process(Object bean, EpsContext context) {
		logger.debug("\"" + toString() + "\" is started");
		Field[] declaredFields = bean.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			if (field.isAnnotationPresent(Inject.class)) {
				String id = field.getAnnotation(Inject.class).value();
				Object objectForInjection = context.getEPSBean(id);
				field.setAccessible(true);
				try {
					field.set(bean, objectForInjection);
					logger.debug("In field " + "\"" + field.getName() + "\"" +
					" was injected with the object " + "\"" +
							objectForInjection.toString() + "\"");
				} catch (IllegalArgumentException | IllegalAccessException e) {
					logger.error("Injection in field " + "\"" + field.getName() + "\"" +
							" is impossible. Exception: " + e.toString());
					e.printStackTrace();
				}
			}
		}
		return bean;
	}

	@Override
	public String toString(){
		return getClass().getSimpleName();
	}
}

