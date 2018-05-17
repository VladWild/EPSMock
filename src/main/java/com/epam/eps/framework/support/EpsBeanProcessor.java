package com.epam.eps.framework.support;

import com.epam.eps.framework.core.EpsContext;

public interface EpsBeanProcessor {
	Object process(Object bean, EpsContext context);
}
