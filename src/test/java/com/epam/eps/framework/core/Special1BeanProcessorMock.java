package com.epam.eps.framework.core;

import com.epam.eps.framework.support.EpsBeanProcessor;

public class Special1BeanProcessorMock implements EpsBeanProcessor {

	@Override
	public Object process(Object bean, EpsContext context) {
		return bean;
	}

}
