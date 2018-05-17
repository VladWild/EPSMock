package com.epam.eps.framework.core;

import java.util.Map;

import com.epam.eps.framework.support.EpsBeanProcessor;

public interface EpsContext {
	Map<String, EpsBeanProcessor> getEpsBeanProcessors();

	Map<String, Object> getEpsBeanDefinitions();

	Object getEPSBean(String id) throws IllegalArgumentException;

	<T> T getEPSBean(Class<T> type) throws IllegalArgumentException;

	void addRiskZone(String riskZoneId);

	void takeAccountGroup(Group group);

	void init();
}
