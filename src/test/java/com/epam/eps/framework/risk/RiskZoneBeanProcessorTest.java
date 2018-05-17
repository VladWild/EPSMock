package com.epam.eps.framework.risk;

import com.epam.eps.framework.core.EpsContext;
import com.epam.eps.framework.support.risk.Max;
import com.epam.eps.framework.support.risk.Min;
import com.epam.eps.framework.support.risk.RiskZone;
import com.epam.eps.framework.support.risk.RiskZoneBeanProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.MissingFormatArgumentException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RiskZoneBeanProcessorTest {

	private RiskZoneBeanProcessor processor;

	@Mock
	private EpsContext context;

	@Before
	public void createProcessor() {
		processor = new RiskZoneBeanProcessor();
	}

	@Test
	public void addToRisksTest() {
		final int MIN = 0;
		final int MAX = 10;
		@RiskZone(id = RiskZoneMock.EPS_BEAN_ID_RISK)
		class RiskZoneMock {

			static final String EPS_BEAN_ID_RISK = "eps.bean.id.risk.somerisk";

			@Min(MIN)
			int min;

			@Max(MAX)
			int max;
		}
		RiskZoneMock targetBean = new RiskZoneMock();
		processor.process(targetBean, context);

		verify(context).addRiskZone(RiskZoneMock.EPS_BEAN_ID_RISK);
	}

	@Test
	public void addMaxMinTest() {
		final int MIN = 0;
		final int MAX = 10;
		@RiskZone(id = "")
		class RiskZoneMock {
			@Min(MIN)
			int min;

			@Max(MAX)
			int max;
		}
		RiskZoneMock targetBean = new RiskZoneMock();

		RiskZoneMock readyBean = (RiskZoneMock) processor.process(targetBean,
				context);

		assertEquals(readyBean.min, MIN);
		assertEquals(readyBean.max, MAX);
	}

	@Test(expected = MissingFormatArgumentException.class)
	public void maxMoreThenMinTest() {
		@RiskZone(id = "")
		class MinMoreThenMaxMock {
			@Min(10)
			int min;
			@Max(0)
			int max;
		}
		MinMoreThenMaxMock mock = new MinMoreThenMaxMock();
		processor.process(mock, context);
	}

	@Test(expected = MissingFormatArgumentException.class)
	public void notNegotiveMaxMinTest() {

		@RiskZone(id = "")
		class NegotiveMinMaxMock {
			@Min(-10)
			int min;
			@Max(-1)
			int max;
		}
		NegotiveMinMaxMock targetBean = new NegotiveMinMaxMock();

		processor.process(targetBean, context);
	}
}
