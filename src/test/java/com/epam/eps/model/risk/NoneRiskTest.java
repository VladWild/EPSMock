package com.epam.eps.model.risk;

import com.epam.eps.framework.core.AddAnotherOneGroup;
import com.epam.eps.framework.core.EpsResourceBundleAnnotationContext;
import com.epam.eps.framework.core.Group;
import com.epam.eps.framework.support.risk.Max;
import com.epam.eps.framework.support.risk.Min;
import com.epam.eps.framework.support.risk.RiskZone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class NoneRiskTest {
	private static final String BEAN_OUT_OF_CONTEXT_MSG = "Bean of risk zone \"%s\" with id=\"%s\" out of the context \"%s\".";

	@Test
	public void hasRiskZoneAnnotationTest() {
		assertTrue(NoneRisk.class.isAnnotationPresent(RiskZone.class));
	}

	@Test
	public void hasOnlyOneMinAnnotationTest() {
		final int ANNOTATIONS_COUNT = 1;
		List<Field> annotatedField = new ArrayList<>();
		for (Field field : NoneRisk.class.getDeclaredFields()) {
			if (field.isAnnotationPresent(Min.class)) {
				annotatedField.add(field);
			}
		}
		assertEquals(annotatedField.size(), ANNOTATIONS_COUNT);
		assertTrue(annotatedField.get(0).isAnnotationPresent(Min.class));
	}

	@Test
	public void hasOnlyOneMaxAnnotationTest() {
		final int ANNOTATIONS_COUNT = 1;
		List<Field> annotatedField = new ArrayList<>();
		for (Field field : NoneRisk.class.getDeclaredFields()) {
			if (field.isAnnotationPresent(Max.class)) {
				annotatedField.add(field);
			}
		}
		assertEquals(annotatedField.size(), ANNOTATIONS_COUNT);
		assertTrue(annotatedField.get(0).isAnnotationPresent(Max.class));
	}

	@Test
	public void hasOnlyOneAddAnotherOneGroupAnnotationTest() {
		final int ANNOTATIONS_COUNT = 1;
		List<Method> annotatedMethods = new ArrayList<>();
		for (Method method : NoneRisk.class.getDeclaredMethods()) {
			if (method.isAnnotationPresent(AddAnotherOneGroup.class)) {
				annotatedMethods.add(method);
			}
		}
		assertEquals(annotatedMethods.size(), ANNOTATIONS_COUNT);
		assertTrue(annotatedMethods.get(0)
				.isAnnotationPresent(AddAnotherOneGroup.class));
	}

	@Test
	public void beanInContextTest() throws Exception {
		EpsResourceBundleAnnotationContext epsResourceBundleAnnotationContext = new EpsResourceBundleAnnotationContext(
				"config");
		Object epsBean;
		final String id = NoneRisk.class.isAnnotationPresent(RiskZone.class)
				? NoneRisk.class.getAnnotation(RiskZone.class).id() : "";
		final String msg = String.format(BEAN_OUT_OF_CONTEXT_MSG,
				NoneRisk.class, id,
				epsResourceBundleAnnotationContext.getClass());
		try {
			epsResourceBundleAnnotationContext.init();
		} catch (IllegalArgumentException | MissingResourceException e) {
			e.printStackTrace();
		}
		try {
			epsBean = epsResourceBundleAnnotationContext.getEPSBean(id);
		} catch (IllegalArgumentException e) {
			epsBean = null;
		}

		assertNotNull(msg, epsBean);
	}

	@Test
	public void addAnotherOneGroupTest() throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Group groupMock = mock(Group.class);
		Risk noneRiskMock = new NoneRisk();
		boolean groupAdded = false;
		for (Method method : NoneRisk.class.getDeclaredMethods()) {
			if (method.isAnnotationPresent(AddAnotherOneGroup.class)) {
				method.invoke(noneRiskMock, groupMock);
				groupAdded = noneRiskMock.getGroups().contains(groupMock);
			}
		}
		assertTrue(groupAdded);
	}

	@Test
	public void getMaxTest() throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		final int EXPECTED_MAX_VALUE = 3;
		int actualMax = -1;
		Risk noneRiskMock = new NoneRisk();
		for (Field field : NoneRisk.class.getDeclaredFields()) {
			if (field.isAnnotationPresent(Max.class)) {
				field.setAccessible(true);
				field.set(noneRiskMock, EXPECTED_MAX_VALUE);
				actualMax = noneRiskMock.getMax();
			}
		}
		assertEquals(EXPECTED_MAX_VALUE, actualMax);
	}

	@Test
	public void getMinTest() throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		final int ACTUAL_MIN_VALUE = 10;
		int actualMin = -1;
		Risk noneRiskMock = new NoneRisk();
		for (Field field : NoneRisk.class.getDeclaredFields()) {
			if (field.isAnnotationPresent(Min.class)) {
				field.setAccessible(true);
				field.set(noneRiskMock, ACTUAL_MIN_VALUE);
				actualMin = noneRiskMock.getMin();
			}
		}
		assertEquals(actualMin, ACTUAL_MIN_VALUE);
	}
}
