package com.epam.eps.framework.core;

import com.epam.eps.framework.support.EpsBeanProcessor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EpsResourceBundleAnnotationContextTuningBeansTest {

	private final int PROCESSORS_COUNT = 2;
	private final int BEANS_COUNT = 3;
	private final int PROCESS_METHOD_INVOKES_COUNT = PROCESSORS_COUNT
			* BEANS_COUNT;
	private EpsResourceBundleAnnotationContext epsContext;
	@Mock
	private EpsBeanProcessor processorMock;
	@Mock
	private Object beanMock;
	@Mock
	private Object processedBeanMock;
	private Map<String, Object> beanDefinitionsMap;

	@Before
	public void createContext() {
		epsContext = new EpsResourceBundleAnnotationContext(
				"com.epam.eps.framework.mocks.ConfigMock");
		epsContext.epsCommonBeanDefinitions = new HashMap<>();
		for (int i = 0; i < BEANS_COUNT; i++) {
			epsContext.epsCommonBeanDefinitions.put("" + i, beanMock);
		}
		beanDefinitionsMap = new HashMap<>();
		for (Entry<String, Object> entry : epsContext.epsCommonBeanDefinitions
				.entrySet()) {
			beanDefinitionsMap.put(entry.getKey(), processedBeanMock);
		}
	}

	@After
	public void destroyContext() {
		epsContext = null;
	}

	@Test
	public void tuningEachEpsBeansByEachProcessorTest() {
		Map<String, EpsBeanProcessor> specialProcsMap = new HashMap<>();
		for (int i = 0; i < PROCESSORS_COUNT; i++) {
			specialProcsMap.put("" + i, processorMock);
		}

		when(processorMock.process(beanMock, epsContext)).thenReturn(beanMock);

		epsContext.tuningEpsBeans(specialProcsMap);

		verify(processorMock, times(PROCESS_METHOD_INVOKES_COUNT))
				.process(beanMock, epsContext);

	}

	@Test
	public void refreshEachEpsBeansAfterTuningByEachProcessorTest() {
		Map<String, EpsBeanProcessor> processorsMap = new HashMap<>();
		processorsMap.put("0", processorMock);

		when(processorMock.process(beanMock, epsContext))
				.thenReturn(processedBeanMock);

		epsContext.tuningEpsBeans(processorsMap);

		assertTrue(epsContext.epsCommonBeanDefinitions.entrySet()
				.containsAll(beanDefinitionsMap.entrySet()));

	}

}
