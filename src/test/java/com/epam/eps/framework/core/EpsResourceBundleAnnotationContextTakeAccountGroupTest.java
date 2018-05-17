package com.epam.eps.framework.core;

import com.epam.eps.framework.mocks.NoneRisk;
import com.epam.eps.framework.mocks.TargetRiskMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EpsResourceBundleAnnotationContextTakeAccountGroupTest {

	private EpsResourceBundleAnnotationContext epsContext;
	@Mock
	private Set<Cell> occupiedCells;
	@Spy
	private Group groupSpy;
	private NoneRisk noneRisk;
	private TargetRiskMock targetRisk;

	@Before
	@SuppressWarnings("unchecked")
	public void createContext() {
		epsContext = new EpsResourceBundleAnnotationContext(
				"com.epam.eps.framework.mocks.ConfigMock");
		epsContext.riskZones = new HashSet<>(Arrays
				.asList("eps.bean.id.risk.none", "eps.bean.id.risk.target"));
		epsContext.epsCommonBeanDefinitions = mock(HashMap.class);
		targetRisk = new TargetRiskMock();
		noneRisk = new NoneRisk();
	}

	@Test
	public void takeAccountTest() {
		when(groupSpy.getCells()).thenReturn(occupiedCells);
		when(occupiedCells.size()).thenReturn(10);
		when(epsContext.epsCommonBeanDefinitions
				.get("com.epam.eps.framework.mocks.NoneRisk"))
						.thenReturn(noneRisk);
		when(epsContext.epsCommonBeanDefinitions
				.get("com.epam.eps.framework.mocks.TargetRiskMock"))
						.thenReturn(targetRisk);
		assertEquals(targetRisk.getGroups().size(), 0);
		assertEquals(noneRisk.getGroups().size(), 0);

		epsContext.takeAccountGroup(groupSpy);

		assertEquals(targetRisk.getGroups().size(), 1);
		assertEquals(noneRisk.getGroups().size(), 0);
	}
}
