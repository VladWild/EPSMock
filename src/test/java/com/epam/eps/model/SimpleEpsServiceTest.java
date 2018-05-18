package com.epam.eps.model;

import com.epam.eps.framework.core.EpsResourceBundleAnnotationContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.MissingResourceException;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEpsServiceTest {
	private static final String BEAN_OUT_OF_CONTEXT_MSG = "Bean \"%s\" out of the context \"%s\".";

	private static final String DONT_INJECT_RISK_GROUPS_MSG = "Don't jnject risk groups to bean \"%s\" from context \"%s\"";

	private EpsService epsService;
	private EpsResourceBundleAnnotationContext epsResourceBundleAnnotationContext;

	@Before
	public void createContext() {
		epsResourceBundleAnnotationContext = new EpsResourceBundleAnnotationContext(
				"config");
		try {
			epsResourceBundleAnnotationContext.init();
		} catch (IllegalArgumentException | MissingResourceException e) {
			e.printStackTrace();
		}
		try {
			epsService = epsResourceBundleAnnotationContext
					.getEPSBean(EpsService.class);
		} catch (IllegalArgumentException e) {
			epsService = null;
		}
	}

	@Test
	public void beanInContextTest() {
		final String msg = String.format(BEAN_OUT_OF_CONTEXT_MSG,
				EpsService.class,
				epsResourceBundleAnnotationContext.getClass());

		assertNotNull(msg, epsService);
	}

	@Test
	public void riskGroupsFilledTest() {
		if (epsService != null) {
			final String msg = String.format(DONT_INJECT_RISK_GROUPS_MSG,
					epsService.getClass(),
					epsResourceBundleAnnotationContext.getClass());
			assertNotNull(msg, epsService.getRiskGroups());
		} else {
			assert epsService != null;
		}

	}
}
