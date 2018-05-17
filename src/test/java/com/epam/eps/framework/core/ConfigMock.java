package com.epam.eps.framework.core;

import java.util.ListResourceBundle;

public class ConfigMock extends ListResourceBundle {
	public Object[][] getContents() {
		return new Object[][] {
				{ "eps.bean.processors.common",
						""//
								+ "com.epam.eps.framework.core.Common1BeanProcessorMock, "
								+ "com.epam.eps.framework.core.Common2BeanProcessorMock" },
				{ "eps.bean.processors.special",
						""//
								+ "com.epam.eps.framework.core.Special1BeanProcessorMock, "
								+ "com.epam.eps.framework.core.Special2BeanProcessorMock" },
				{ "eps.bean.definitions", "com.epam.eps.model.SimpleReport" },
				{ "eps.bean.id.risk.target",
						"com.epam.eps.framework.core.TargetRiskMock" },
				{ "eps.bean.id.risk.none",
						"com.epam.eps.framework.core.NoneRisk" } };
	}
}


