package com.epam.eps.framework.support.randomsector;

import com.epam.eps.framework.core.Cell;
import com.epam.eps.framework.core.EpsContext;
import com.epam.eps.framework.core.EpsResourceBundleAnnotationContext;
import com.epam.eps.framework.support.EpsBeanProcessor;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;

public class RandomSectorBeanProcessor implements EpsBeanProcessor {

	private final static Logger logger = Logger.getLogger(RandomSectorBeanProcessor.class);

	@Override
	public Object process(Object bean, EpsContext context) {
		Field[] declaredFields = bean.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			if (field.isAnnotationPresent(RandomSector.class)) {
				RandomSector annotation = field
						.getAnnotation(RandomSector.class);
				int width = annotation.width();
				int height = annotation.height();
				double fillFactor = annotation.fillFactor();
				logger.info("Creating a random field");
				Cell[][] filledField = getFilledField(width, height,
						fillFactor);
				field.setAccessible(true);
				try {
					field.set(bean, filledField);
					logger.info("In field " + "\"" + field.getName() + "\"" +
							" was injected with a random created field");
				} catch (IllegalArgumentException | IllegalAccessException e) {
					logger.error("Injection in field " + "\"" + field.getName() + "\"" +
							" is impossible. Exception: " + e.toString());
					e.printStackTrace();
				}
			}
		}
		return bean;
	}

	private Cell[][] getFilledField(int width, int height, double fillFactor) {
		Cell[][] filledField = new Cell[height][width];

		for (int i = 0; i < filledField.length; i++) {
			for (int j = 0; j < filledField[i].length; j++) {
				double probability = Math.random();

				if (probability < fillFactor) {
					filledField[i][j] = new Cell(j, i);
				}
			}
		}

		return filledField;
	}
}


