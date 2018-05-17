package com.epam.eps.framework.mocks;

import java.util.ListResourceBundle;

public class ConfigMock extends ListResourceBundle {
	public Object[][] getContents() {
		return new Object[][] {
				{ "eps.bean.processors.common",
						""//
								+ "com.epam.eps.framework.mocks.Common1BeanProcessorMock, "
								+ "com.epam.eps.framework.mocks.Common2BeanProcessorMock" },
				{ "eps.bean.processors.special",
						""//
								+ "com.epam.eps.framework.mocks.Special1BeanProcessorMock, "
								+ "com.epam.eps.framework.mocks.Special2BeanProcessorMock" },
				{ "eps.bean.definitions", "com.epam.eps.model.SimpleReport" },
				{ "eps.bean.id.risk.target",
						"com.epam.eps.framework.mocks.TargetRiskMock" },
				{ "eps.bean.id.risk.none",
						"com.epam.eps.framework.mocks.NoneRisk" } };
	}
}

