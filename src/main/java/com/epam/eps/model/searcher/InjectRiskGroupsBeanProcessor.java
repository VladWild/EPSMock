package com.epam.eps.model.searcher;

import com.epam.eps.EmergencyPreventionSystem;
import com.epam.eps.framework.core.EpsContext;
import com.epam.eps.framework.core.Group;
import com.epam.eps.framework.support.EpsBeanProcessor;
import com.epam.eps.model.algorithm.FactoryAlgorithms;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;

public class InjectRiskGroupsBeanProcessor implements EpsBeanProcessor {
    private final static Logger logger = Logger.getLogger(InjectRiskGroupsBeanProcessor.class);

    @Override
    public Object process(Object bean, EpsContext context) {
        logger.debug("\"" + toString() + "\" is started");
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(InjectRiskGroups.class)) {
                String id = field.getAnnotation(InjectRiskGroups.class).viewFieldId();
                logger.debug("Getting groups on the field");
                Group groups[] = FactoryAlgorithms.getTypeAlgorithm(FactoryAlgorithms.WIDTH_CYCLES,
                        context.getEPSBean(id)).getGroups();
                logger.debug("Added all groups in context");
                for (Group group : groups) {
                    context.takeAccountGroup(group);
                }
                field.setAccessible(true);
                try {
                    field.set(bean, groups);
                    logger.debug("In field " + "\"" + field.getName() + "\"" +
                            " was injected all groups");
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                    logger.error("Injection in field " + "\"" + field.getName() + "\"" +
                            " is impossible. Exception: " + e.toString());
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


