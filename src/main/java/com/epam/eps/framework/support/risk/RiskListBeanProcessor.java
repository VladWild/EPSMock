package com.epam.eps.framework.support.risk;

import com.epam.eps.framework.core.EpsContext;
import com.epam.eps.framework.support.EpsBeanProcessor;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author Alexander_Lotsmanov
 */
public class RiskListBeanProcessor implements EpsBeanProcessor {

	@Override
	public Object process(Object bean, EpsContext context) {
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
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return bean;
	}

}
