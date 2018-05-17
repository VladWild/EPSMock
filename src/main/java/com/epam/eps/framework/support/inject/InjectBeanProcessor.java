package com.epam.eps.framework.support.inject;

import com.epam.eps.framework.core.EpsContext;
import com.epam.eps.framework.support.EpsBeanProcessor;

import java.lang.reflect.Field;

public class InjectBeanProcessor implements EpsBeanProcessor {

	@Override
	public Object process(Object bean, EpsContext context) {
		Field[] declaredFields = bean.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			if (field.isAnnotationPresent(Inject.class)) {
				String id = field.getAnnotation(Inject.class).value();
				Object objectForInjection = context.getEPSBean(id);
				field.setAccessible(true);
				try {
					field.set(bean, objectForInjection);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return bean;
	}
}

