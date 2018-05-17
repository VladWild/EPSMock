package com.epam.eps.model.algorithm;

import com.epam.eps.framework.core.Cell;
import com.epam.eps.framework.core.EpsContext;
import com.epam.eps.framework.core.Group;
import com.epam.eps.framework.core.ReviewSector;
import com.epam.eps.framework.support.FieldFormatUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchWidthSetUseTest {
    @Mock
    private EpsContext context;
    @Mock
    private ReviewSector reviewSectorMock;

    @SuppressWarnings("unused")
    private static final String FIELD_STRING = ""// it is for example
            + "{|X| -  -  - |X||X||X||X| -  - }"
            + "{|X||X| - |X||X| -  -  -  - |X|}"
            + "{|X||X||X| -  - |X||X| -  -  - }"
            + "{|X| -  - |X| -  - |X||X||X| - }"
            + "{ - |X| - |X| - |X||X| -  - |X|}"
            + "{|X||X||X||X||X| -  - |X| -  - }"
            + "{|X||X| -  - |X||X| - |X| - |X|}"
            + "{|X||X||X||X||X| -  - |X| -  - }"
            + "{|X| - |X||X||X| -  -  -  -  - }"
            + "{|X||X| -  -  -  -  -  -  - |X|}";

    @Test
    public void countGroupRiskTest() {
        final int COUNT_GROUPS = 9;
        when(context.getEPSBean("viewMyFieldId")).thenReturn(reviewSectorMock);
        Cell[][] field = FieldFormatUtil.getField(FIELD_STRING);
        Set<Cell> occupied = Arrays.stream(field)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        when(reviewSectorMock.getOccupied()).thenReturn(occupied);
        occupied.forEach(cell -> when(reviewSectorMock.getNeighbors(cell))
                .thenReturn(neighborSet(cell, field)));

        Group[] groups = FactoryAlgorithms.getTypeAlgorithm(FactoryAlgorithms.WIDTH_SET,
                context.getEPSBean("viewMyFieldId")).getGroups();

        assertEquals(groups.length, COUNT_GROUPS);
    }

    private Set<Cell> neighborSet(Cell cell, Cell[][] field) {
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
