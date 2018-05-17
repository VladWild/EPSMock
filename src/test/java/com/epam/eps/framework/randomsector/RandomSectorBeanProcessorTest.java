package com.epam.eps.framework.randomsector;

import com.epam.eps.framework.core.Cell;
import com.epam.eps.framework.core.EpsContext;
import com.epam.eps.framework.support.randomsector.RandomSector;
import com.epam.eps.framework.support.randomsector.RandomSectorBeanProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class RandomSectorBeanProcessorTest {

	private static final int WIDTH = 10;
	private static final int HEIGHT = 10;
	private static final double PROBABILITY = .8;
	private static final int LIMIT = 5;
	private static long ACTUAL = Math.round(WIDTH * HEIGHT * PROBABILITY);

	private RandomSectorBeanProcessor processor;

	@Before
	public void createProcessor() {
		processor = new RandomSectorBeanProcessor();
	}

	private class TargetClassMock {
		@RandomSector(width = WIDTH, height = HEIGHT, fillFactor = PROBABILITY)
		Cell[][] field;
	}

	@Test
	public void processTest() {
		TargetClassMock targetBean = new TargetClassMock();

		TargetClassMock readyBean = (TargetClassMock) processor
				.process(targetBean, mock(EpsContext.class));
		int count = getFilledCount(readyBean);

		assertEquals(readyBean.field.length, WIDTH);
		assertEquals(readyBean.field[0].length, HEIGHT);
		assertTrue(count >= ACTUAL - LIMIT && count <= ACTUAL + LIMIT);
	}

	private int getFilledCount(TargetClassMock readyBean) {
		int count = 0;
		for (Cell[] iterable_element : readyBean.field) {
			for (Cell cell : iterable_element) {
				if (cell != null) {
					count++;
				}
			}
		}
		return count;
	}
}

