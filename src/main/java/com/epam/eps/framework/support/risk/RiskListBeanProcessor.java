package com.epam.eps.framework.support.risk;

import com.epam.eps.framework.core.EpsContext;
import com.epam.eps.framework.support.EpsBeanProcessor;
import com.epam.eps.framework.support.inject.InjectBeanProcessor;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author Alexander_Lotsmanov
 */
public class RiskListBeanProcessor implements EpsBeanProcessor {
	private final static Logger logger = Logger.getLogger(RiskListBeanProcessor.class);

	@Override
	public Object process(Object bean, EpsContext context) {
        logger.debug("\"" + toString() + "\" is started");
		Field[] declaredFields = bean.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			if (field.isAnnotationPresent(RiskList.class)) {
				String[] ids = field.getAnnotation(RiskList.class).id();
				Object[] risks = new Object[ids.length];
				for (int i = 0; i < ids.length; i++) {
					risks[i] = context.getEPSBean(ids[i]);
				}
				field.setAccessible(true);
				try {
					field.set(bean, Arrays.asList(risks));
					logger.debug("In field " + "\"" + field.getName() + "\"" +
							" the following risks were injected " +
							Arrays.stream(risks).map(Object::toString).
									reduce("", (accomulator, element) ->
                                            accomulator + "\"" + element + "\" "));
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
