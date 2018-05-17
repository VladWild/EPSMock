package com.epam.eps.framework.support.risk;

import com.epam.eps.framework.core.EpsContext;
import com.epam.eps.framework.support.EpsBeanProcessor;

import java.lang.reflect.Field;
import java.util.MissingFormatArgumentException;

public class RiskZoneBeanProcessor implements EpsBeanProcessor {

	@Override
	public Object process(Object bean, EpsContext context) {
		Class<?> beanClass = bean.getClass();
		if (beanClass.isAnnotationPresent(RiskZone.class)) {
			RiskZone riskZoneAnnotation = beanClass
					.getAnnotation(RiskZone.class);
			String riskZoneId = riskZoneAnnotation.id();
			fillBorders(bean);
			context.addRiskZone(riskZoneId);
		}
		return bean;
	}

	private void fillBorders(Object bean)
			throws MissingFormatArgumentException {
		Class<?> beanClass = bean.getClass();
		int min = -1;
		int max = -1;
		for (Field field : beanClass.getDeclaredFields()) {
			if (field.isAnnotationPresent(Min.class)) {
				min = field.getAnnotation(Min.class).value();
				field.setAccessible(true);
				try {
					field.set(bean, min);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			} else if (field.isAnnotationPresent(Max.class)) {
				max = field.getAnnotation(Max.class).value();
				field.setAccessible(true);
				try {
					field.set(bean, max);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		if (!(min >= 0 && max >= 0 && min <= max)) {
			throw new MissingFormatArgumentException(String.format(
					"Must be @Min and @Max and min <= max, but: max=%d, min=%d at bean = %s",
					min, max, bean));
		}
	}

}
