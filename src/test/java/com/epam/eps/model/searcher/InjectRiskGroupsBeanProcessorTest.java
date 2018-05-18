package com.epam.eps.model.searcher;

import com.epam.eps.framework.core.Cell;
import com.epam.eps.framework.core.EpsContext;
import com.epam.eps.framework.core.Group;
import com.epam.eps.framework.core.ReviewSector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InjectRiskGroupsBeanProcessorTest {

	private InjectRiskGroupsBeanProcessor processor;
	@Mock
	private EpsContext context;
	@Mock
	private ReviewSector reviewSectorMock;

	private Cell[][] field = {
			{ new Cell(0, 0), null, new Cell(2, 0), new Cell(3, 0), null, null,
					null, new Cell(7, 0), new Cell(8, 0), null,
					new Cell(10, 0) },
			{ new Cell(0, 1), new Cell(1, 1), null, new Cell(3, 1), null,
					new Cell(5, 1), new Cell(6, 1), new Cell(7, 1),
					new Cell(8, 1), null, new Cell(10, 1) },
			{ new Cell(0, 2), new Cell(1, 2), null, null, new Cell(4, 2),
					new Cell(5, 2), new Cell(6, 2), null, null, new Cell(9, 2),
					null },
			{ new Cell(0, 3), new Cell(1, 3), null, new Cell(3, 3),
					new Cell(4, 3), null, new Cell(6, 3), new Cell(7, 3),
					new Cell(8, 3), null, null },
			{ null, new Cell(1, 4), null, null, null, null, new Cell(6, 4),
					null, new Cell(8, 4), null, new Cell(10, 4) },
			{ new Cell(0, 5), null, null, null, new Cell(4, 5), new Cell(5, 5),
					null, null, new Cell(8, 5), new Cell(9, 5),
					new Cell(10, 5) },
			{ new Cell(0, 6), null, null, null, new Cell(4, 6), new Cell(5, 6),
					new Cell(6, 6), null, new Cell(8, 6), null,
					new Cell(10, 6) },
			{ null, null, null, null, null, null, null, new Cell(7, 7),
					new Cell(8, 7), null, new Cell(10, 7) },
			{ new Cell(0, 8), new Cell(1, 8), null, new Cell(3, 8), null, null,
					null, null, new Cell(8, 8), null, null },
			{ null, new Cell(1, 9), new Cell(2, 9), new Cell(3, 9),
					new Cell(4, 9), new Cell(5, 9), null, new Cell(7, 9), null,
					new Cell(9, 9), new Cell(10, 9) },
			{ null, new Cell(1, 10), null, null, new Cell(4, 10),
					new Cell(5, 10), null, new Cell(7, 10), null, null,
					new Cell(10, 10) } };
	private final HashSet<Cell> occupied = new HashSet<Cell>(Arrays.asList(
			new Cell(0, 0), new Cell(8, 8), new Cell(0, 1), new Cell(4, 5),
			new Cell(0, 2), new Cell(4, 6), new Cell(0, 3), new Cell(0, 5),
			new Cell(4, 9), new Cell(0, 6), new Cell(4, 10), new Cell(0, 8),
			new Cell(9, 2), new Cell(5, 1), new Cell(9, 5), new Cell(5, 2),
			new Cell(1, 1), new Cell(5, 5), new Cell(9, 9), new Cell(1, 2),
			new Cell(5, 6), new Cell(1, 3), new Cell(1, 4), new Cell(5, 9),
			new Cell(5, 10), new Cell(1, 8), new Cell(1, 9), new Cell(1, 10),
			new Cell(10, 0), new Cell(10, 1), new Cell(10, 4), new Cell(6, 1),
			new Cell(10, 5), new Cell(6, 2), new Cell(10, 6), new Cell(6, 3),
			new Cell(10, 7), new Cell(2, 0), new Cell(6, 4), new Cell(10, 9),
			new Cell(6, 6), new Cell(10, 10), new Cell(2, 9), new Cell(7, 0),
			new Cell(7, 1), new Cell(7, 3), new Cell(3, 0), new Cell(3, 1),
			new Cell(3, 3), new Cell(7, 7), new Cell(7, 9), new Cell(7, 10),
			new Cell(3, 8), new Cell(3, 9), new Cell(8, 0), new Cell(8, 1),
			new Cell(8, 3), new Cell(8, 4), new Cell(8, 5), new Cell(4, 2),
			new Cell(8, 6), new Cell(4, 3), new Cell(8, 7)));

	private Group[] groups = { new Group(new Cell(8, 8), new Cell(7, 0),
			new Cell(7, 1), new Cell(9, 5), new Cell(7, 3), new Cell(5, 1),
			new Cell(5, 2), new Cell(7, 7), new Cell(3, 3), new Cell(8, 0),
			new Cell(8, 1), new Cell(10, 4), new Cell(8, 3), new Cell(6, 1),
			new Cell(10, 5), new Cell(8, 4), new Cell(6, 2), new Cell(10, 6),
			new Cell(8, 5), new Cell(6, 3), new Cell(10, 7), new Cell(8, 6),
			new Cell(6, 4), new Cell(4, 2), new Cell(8, 7), new Cell(4, 3)),
			new Group(new Cell(3, 0), new Cell(3, 1), new Cell(2, 0)),
			new Group(new Cell(9, 2)),
			new Group(new Cell(0, 0), new Cell(1, 1), new Cell(0, 1),
					new Cell(1, 2), new Cell(0, 2), new Cell(1, 3),
					new Cell(1, 4), new Cell(0, 3)),
			new Group(new Cell(6, 6), new Cell(4, 5), new Cell(4, 6),
					new Cell(5, 5), new Cell(5, 6)),
			new Group(new Cell(7, 9), new Cell(7, 10)),
			new Group(new Cell(0, 5), new Cell(0, 6)),
			new Group(new Cell(4, 9), new Cell(4, 10), new Cell(2, 9),
					new Cell(0, 8), new Cell(5, 9), new Cell(5, 10),
					new Cell(3, 8), new Cell(3, 9), new Cell(1, 8),
					new Cell(1, 9), new Cell(1, 10)),
			new Group(new Cell(10, 10), new Cell(9, 9), new Cell(10, 9)),
			new Group(new Cell(10, 0), new Cell(10, 1)) };

	@SuppressWarnings("unused")
	private static final String FIELD_STRING = ""// it is for example
			+ "{|X| - |X||X| -  -  - |X||X| - |X|}"
			+ "{|X||X| - |X| - |X||X||X||X| - |X|}"
			+ "{|X||X| -  - |X||X||X| -  - |X| - }"
			+ "{|X||X| - |X||X| - |X||X||X| -  - }"
			+ "{ - |X| -  -  -  - |X| - |X| - |X|}"
			+ "{|X| -  -  - |X||X| -  - |X||X||X|}"
			+ "{|X| -  -  - |X||X||X| - |X| - |X|}"
			+ "{ -  -  -  -  -  -  - |X||X| - |X|}"
			+ "{|X||X| - |X| -  -  -  - |X| -  - }"
			+ "{ - |X||X||X||X||X| - |X| - |X||X|}"
			+ "{ - |X| -  - |X||X| - |X| -  - |X|}";

	private static class TargetClassMock {
		@InjectRiskGroups(viewFieldId = "viewFieldId")
		Group[] groups = {};
	}

	@Before
	public void createProcessor() {
		processor = new InjectRiskGroupsBeanProcessor();
	}

	@Test
	public void processTest() {
		TargetClassMock bean = new TargetClassMock();
		when(context.getEPSBean("viewFieldId")).thenReturn(reviewSectorMock);
		when(reviewSectorMock.getOccupied()).thenReturn(occupied);
		for (Cell cell : occupied) {
			when(reviewSectorMock.getNeighbors(cell))
					.thenReturn(neighborSet(cell));
		}

		TargetClassMock readyBean = (TargetClassMock) processor.process(bean,
				context);

		assertTrue(Arrays.asList(readyBean.groups)
				.containsAll(Arrays.asList(groups)));
		assertTrue(Arrays.asList(groups)
				.containsAll(Arrays.asList(readyBean.groups)));

		for (Group group : groups) {
			verify(context, times(1)).takeAccountGroup(group);
		}
	}

	private Set<Cell> neighborSet(Cell cell) {
		Set<Cell> neighbors = new HashSet<>();
		int x = cell.getX();
		int y = cell.getY();
		int height = field.length;
		int width = field[0].length;

		if (((y + 1) < height) && (field[y + 1][x] != null)) {
			neighbors.add(field[y + 1][x]);
		}
		if (((y - 1) >= 0) && (field[y - 1][x] != null)) {
			neighbors.add(field[y - 1][x]);
		}
		if (((x + 1) < width) && (field[y][x + 1] != null)) {
			neighbors.add(field[y][x + 1]);
		}
		if (((x - 1) >= 0) && (field[y][x - 1] != null)) {
			neighbors.add(field[y][x - 1]);
		}
		return neighbors;
	}
}

