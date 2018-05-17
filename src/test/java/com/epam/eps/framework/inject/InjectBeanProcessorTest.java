package com.epam.eps.framework.inject;

import com.epam.eps.framework.core.EpsContext;
import com.epam.eps.framework.support.inject.Inject;
import com.epam.eps.framework.support.inject.InjectBeanProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InjectBeanProcessorTest {

	@Mock
	private EpsContext context;
	@Mock
	private Object beanForInject;
	private InjectBeanProcessor processor;

	@Before
	public void createProcessor() {
		processor = new InjectBeanProcessor();
	}

	@Test
	public void processTest() {
		final String TARGET_BEAN_ID = "target.bean.id";
		class TargetClassMock {
			@Inject(TARGET_BEAN_ID)
			private Object target;

			public Object getTarget() {
				return target;
			}
		}
		TargetClassMock targetBean = new TargetClassMock();
		when(context.getEPSBean(TARGET_BEAN_ID)).thenReturn(beanForInject);

		TargetClassMock readyBean = (TargetClassMock) processor
				.process(targetBean, context);

		assertEquals(readyBean.getTarget(), beanForInject);
		verify(context, times(1)).getEPSBean(TARGET_BEAN_ID);
	}

	@Test
	public void dontProcessTest() {
		class WithoutInjectClassMock {
			private Object field;

			public Object getField() {
				return field;
			}
		}
		WithoutInjectClassMock withoutInjectBean = new WithoutInjectClassMock();

		WithoutInjectClassMock readyBean = (WithoutInjectClassMock) processor
				.process(withoutInjectBean, context);

		assertNull(readyBean.getField());
	}
}

