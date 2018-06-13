package com.epam.eps.framework.support.risk;

import com.epam.eps.framework.core.EpsContext;
import com.epam.eps.framework.support.EpsBeanProcessor;
import com.epam.eps.framework.support.inject.InjectBeanProcessor;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.MissingFormatArgumentException;

public class RiskZoneBeanProcessor implements EpsBeanProcessor {
	private final static Logger logger = Logger.getLogger(RiskZoneBeanProcessor.class);

	@Override
	public Object process(Object bean, EpsContext context) {
		logger.debug("\"" + toString() + "\" is started");
		Class<?> beanClass = bean.getClass();
		if (beanClass.isAnnotationPresent(RiskZone.class)) {
			RiskZone riskZoneAnnotation = beanClass
					.getAnnotation(RiskZone.class);
			String riskZoneId = riskZoneAnnotation.id();
			fillBorders(bean);
			context.addRiskZone(riskZoneId);
			logger.debug("The risk zone with id \"" + riskZoneId +
					"\" was added in context");
		}
		return bean;
	}

	@Override
	public String toString(){
		return getClass().getSimpleName();
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
					logger.debug("In the risk zone \"" + bean.getClass().getSimpleName() +
							"\" was injected in field \"min\" value " + min);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					logger.error("Injection in field " + "\"" + field.getName() + "\"" +
							" is impossible. Exception: " + e.toString());
					e.printStackTrace();
				}
			} else if (field.isAnnotationPresent(Max.class)) {
				max = field.getAnnotation(Max.class).value();
				field.setAccessible(true);
				try {
					field.set(bean, max);
					logger.debug("In the risk zone \"" + bean.getClass().getSimpleName() +
							"\" was injected in field \"max\" value " + max);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					logger.error("Injection in field " + "\"" + field.getName() + "\"" +
							" is impossible. Exception: " + e.toString());
					e.printStackTrace();
				}
			}
		}
		if (!(min >= 0 && max >= 0 && min <= max)) {
			logger.error("The format for entering the minimum and maximum group size" +
					" does not meet the requirement");
			throw new MissingFormatArgumentException(String.format(
					"Must be @Min and @Max and min <= max, but: max=%d, min=%d at bean = %s",
					min, max, bean));
		}
	}

}
