package com.epam.eps.model.searcher;

import com.epam.eps.framework.core.EpsContext;
import com.epam.eps.framework.core.Group;
import com.epam.eps.framework.support.EpsBeanProcessor;
import com.epam.eps.model.algorithm.FactoryAlgorithms;

import java.lang.reflect.Field;

public class InjectRiskGroupsBeanProcessor implements EpsBeanProcessor {

    @Override
    public Object process(Object bean, EpsContext context) {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(InjectRiskGroups.class)) {
                String id = field.getAnnotation(InjectRiskGroups.class).viewFieldId();
                Group groups[] = FactoryAlgorithms.getTypeAlgorithm(FactoryAlgorithms.OPTIMAL3,
                        context.getEPSBean(id)).getGroups();
                for (Group group : groups) {
                    context.takeAccountGroup(group);
                }
                field.setAccessible(true);
                try {
                    field.set(bean, groups);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return bean;
    }
}


