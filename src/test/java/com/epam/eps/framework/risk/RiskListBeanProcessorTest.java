package com.epam.eps.framework.risk;

import com.epam.eps.framework.core.EpsContext;
import com.epam.eps.framework.support.risk.RiskList;
import com.epam.eps.framework.support.risk.RiskListBeanProcessor;
import com.epam.eps.model.risk.Risk;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RiskListBeanProcessorTest {

	private RiskListBeanProcessor processor;

	@Mock
	private EpsContext context;
	@Mock
	private Object first;
	@Mock
	private Object second;
	@Mock
	private Object third;

	private static class TargetClassMock {

		private static final String EPS_BEAN_ID_RISK_FIRST = "eps.bean.id.risk.first";
		private static final String EPS_BEAN_ID_RISK_SECOND = "eps.bean.id.risk.second";
		private static final String EPS_BEAN_ID_RISK_THIRD = "eps.bean.id.risk.third";

		@RiskList(id = { EPS_BEAN_ID_RISK_FIRST, EPS_BEAN_ID_RISK_SECOND,
				EPS_BEAN_ID_RISK_THIRD })
		List<Risk> risks;
	}

	@Before
	public void createProcessor() {
		processor = new RiskListBeanProcessor();
	}

	@Test
	public void processTest() {
		TargetClassMock targetBean = new TargetClassMock();

		when(context.getEPSBean(TargetClassMock.EPS_BEAN_ID_RISK_FIRST))
				.thenReturn(first);
		when(context.getEPSBean(TargetClassMock.EPS_BEAN_ID_RISK_SECOND))
				.thenReturn(second);
		when(context.getEPSBean(TargetClassMock.EPS_BEAN_ID_RISK_THIRD))
				.thenReturn(third);

		TargetClassMock readyBean = (TargetClassMock) processor
				.process(targetBean, context);

		assertTrue(readyBean.risks
				.containsAll(Arrays.asList(first, second, third)));
	}
}
