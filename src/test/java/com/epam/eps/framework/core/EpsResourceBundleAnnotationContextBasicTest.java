package com.epam.eps.framework.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class EpsResourceBundleAnnotationContextBasicTest {

	private EpsResourceBundleAnnotationContext epsContext;

	@Before
	public void createContext() {
		epsContext = new EpsResourceBundleAnnotationContext(
				"com.epam.eps.framework.mocks.ConfigMock");
	}

	@After
	public void destroyContext() {
		epsContext = null;
	}

	@Test
	public void initBundleConfigContextTest() {
		assertNotNull("Bundle with config must be initialized",
				epsContext.bundleConfig);
		assertNotNull("epsCommonBeanProcessors must be initialized",
				epsContext.epsCommonBeanProcessors);
		assertNotNull("epsSpecialBeanProcessors must be initialized",
				epsContext.epsSpecialBeanProcessors);
		assertNotNull("epsCommonBeanDefinitions must be initialized",
				epsContext.epsCommonBeanDefinitions);
		assertNotNull("riskZones must be initialized", epsContext.riskZones);
	}

	@Test(expected = MissingResourceException.class)
	public void failedIllegalBundleConfigTest() {
		new EpsResourceBundleAnnotationContext("illegal.config");
	}

	@Test
	public void registeredEpsCommonBeanProcessorsTest() {
		assertEquals(epsContext.epsCommonBeanProcessors.size(), 0);

		epsContext.registeredEpsCommonBeanProcessors();
		String[] commonDefinitions = epsContext.bundleConfig
				.getString("eps.bean.processors.common")
				.split(EpsResourceBundleAnnotationContext.CONFIG_BEANS_DELIMITER);

		assertEquals(epsContext.epsCommonBeanProcessors.size(),
				commonDefinitions.length);
	}

	@Test
	public void registeredEpsSpecialBeanProcessorsTest() {
		assertEquals(epsContext.epsSpecialBeanProcessors.size(), 0);

		epsContext.registeredEpsSpecialBeanProcessors();
		String[] specialDefinitions = epsContext.bundleConfig
				.getString("eps.bean.processors.special")
				.split(EpsResourceBundleAnnotationContext.CONFIG_BEANS_DELIMITER);

		assertEquals(epsContext.epsSpecialBeanProcessors.size(),
				specialDefinitions.length);
	}

	@Test
	public void registeredEpsBeanDefinitionsTest() {
		assertEquals(epsContext.epsCommonBeanDefinitions.size(), 0);

		epsContext.registeredEpsBeanDefinitions();
		String[] beansDefinitions = epsContext.bundleConfig
				.getString("eps.bean.definitions")
				.split(EpsResourceBundleAnnotationContext.CONFIG_BEANS_DELIMITER);

		assertEquals(epsContext.epsCommonBeanDefinitions.size(),
				beansDefinitions.length);
	}

	@Test
	public void getEPSBeanByStringIdTest() {
		final String BEAN_CLASSNAME = "com.epam.eps.framework.mocks.TargetRiskMock";
		final String BEAN_ID = "eps.bean.id.risk.target";
		epsContext.epsCommonBeanDefinitions = new HashMap<>();
		epsContext.bundleConfig = ResourceBundle
				.getBundle("com.epam.eps.framework.mocks.ConfigMock");
		Object beanMock = mock(Object.class);
		epsContext.epsCommonBeanDefinitions.put(BEAN_CLASSNAME, beanMock);

		assertEquals(epsContext.getEPSBean(BEAN_ID), beanMock);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getEPSBeanByWrongStringIdTest() {
		final String BEAN_CLASSNAME = "com.epam.eps.framework.core.TargetRiskMock";
		final String BEAN_ID = "eps.bean.id.risk.wrong";
		epsContext.epsCommonBeanDefinitions = new HashMap<>();
		epsContext.bundleConfig = ResourceBundle
				.getBundle("com.epam.eps.framework.mocks.ConfigMock");
		Object beanMock = mock(Object.class);
		epsContext.epsCommonBeanDefinitions.put(BEAN_CLASSNAME, beanMock);

		epsContext.getEPSBean(BEAN_ID);
	}

	@Test
	public void getEPSBeanByTypeTest() {
		epsContext.epsCommonBeanDefinitions = new HashMap<>();
		final Object beanMock = new Object();
		final String BEAN_CLASSNAME = "classname";
		epsContext.epsCommonBeanDefinitions.put(BEAN_CLASSNAME, beanMock);

		assertEquals(epsContext.getEPSBean(Object.class), beanMock);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getEPSBeanByWrongTypeTest() {
		epsContext.epsCommonBeanDefinitions = new HashMap<>();
		final Object beanMock = new Object();
		final String BEAN_CLASSNAME = "classname";
		epsContext.epsCommonBeanDefinitions.put(BEAN_CLASSNAME, beanMock);

		epsContext.getEPSBean(String.class);
	}

	@Test
	public void addRiskZoneTest() {
		epsContext.riskZones = new HashSet<String>();
		final String RISK_ZONE_ID = "risk.Zone.Id";

		assertFalse(epsContext.riskZones.contains(RISK_ZONE_ID));

		epsContext.addRiskZone(RISK_ZONE_ID);

		assertTrue(epsContext.riskZones.contains(RISK_ZONE_ID));
	}

	@Test
	public void doInitTest() {
		class InitMock {
			private int invokeCount;

			@Init
			private void init() {
				invokeCount++;
			}
		}
		InitMock initMock = new InitMock();
		epsContext = new EpsResourceBundleAnnotationContext(
				"com.epam.eps.framework.mocks.ConfigMock");
		epsContext.epsCommonBeanDefinitions = new HashMap<>();
		epsContext.epsCommonBeanDefinitions.put("", initMock);

		epsContext.doInit();

		assertEquals(1, initMock.invokeCount);
	}
}
