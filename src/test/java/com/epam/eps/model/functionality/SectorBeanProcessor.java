package com.epam.eps.model.functionality;

import com.epam.eps.framework.core.Cell;
import com.epam.eps.framework.core.EpsContext;
import com.epam.eps.framework.support.EpsBeanProcessor;
import com.epam.eps.framework.support.FieldFormatUtil;
import com.epam.eps.framework.support.randomsector.RandomSector;

import java.lang.reflect.Field;

public class SectorBeanProcessor implements EpsBeanProcessor {
    @Override
    public Object process(Object bean, EpsContext context) {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(RandomSector.class)) {
                Cell[][] filledField = FieldFormatUtil.getField(CountGroupRisksTest.FIELD_STRING);
                field.setAccessible(true);
                try {
                    field.set(bean, filledField);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }
}


