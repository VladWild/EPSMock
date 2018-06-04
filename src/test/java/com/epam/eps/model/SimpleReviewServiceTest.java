package com.epam.eps.model;

import com.epam.eps.framework.core.Cell;
import com.epam.eps.framework.core.EpsResourceBundleAnnotationContext;
import com.epam.eps.framework.support.FieldFormatUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.MissingResourceException;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SimpleReviewServiceTest {
    private static final String BEAN_OUT_OF_CONTEXT_MSG = "Bean \"%s\" out of the context \"%s\".";

    private ReviewSector reviewSector;
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
            reviewSector = epsResourceBundleAnnotationContext
                    .getEPSBean(ReviewSector.class);
        } catch (IllegalArgumentException e) {
            reviewSector = null;
        }
    }

    @Test
    public void beanInContextTest() {
        final String msg = String.format(BEAN_OUT_OF_CONTEXT_MSG,
                EpsService.class,
                epsResourceBundleAnnotationContext.getClass());

        assertNotNull(msg, reviewSector);
    }

    @Test
    public void getNeighborsTest() {
        assertNotNull(reviewSector);
        final String fieldString = ""//
                + "{|X| - |X||X| -  -  - }"//
                + "{|X||X| - |X| - |X||X|}"//
                + "{ -  - |X||X||X| -  - }"//
                + "{|X||X| - |X| - |X| - }";
        reviewSector.setSector(FieldFormatUtil.getField(fieldString));

        assertTrue(reviewSector.getNeighbors(new Cell(0, 0))
                .containsAll(Arrays.asList(new Cell(0, 1))));
        assertTrue(reviewSector.getNeighbors(new Cell(3, 2))
                .containsAll(Arrays.asList(new Cell(3, 1), new Cell(3, 3),
                        new Cell(2, 2), new Cell(4, 2))));
        assertTrue(reviewSector.getNeighbors(new Cell(5, 3)).isEmpty());
    }

    @Test
    public void getWidthTest() {
        assertNotNull(reviewSector);
        final String fieldString = ""//
                + "{|X| - |X||X| -  -  - }"//
                + "{|X||X| - |X| - |X||X|}"//
                + "{ -  - |X||X||X| -  - }"//
                + "{|X||X| - |X| - |X| - }";
        reviewSector.setSector(FieldFormatUtil.getField(fieldString));

        assertEquals(7, reviewSector.getWidth());
    }

    @Test
    public void getHeightTest() {
        assertNotNull(reviewSector);
        final String fieldString = ""//
                + "{|X| - |X||X| -  -  - }"//
                + "{|X||X| - |X| - |X||X|}"//
                + "{ -  - |X||X||X| -  - }"//
                + "{|X||X| - |X| - |X| - }";
        reviewSector.setSector(FieldFormatUtil.getField(fieldString));

        assertEquals(4, reviewSector.getHeight());
    }

    @Test
    public void setSectorTest() {
        assertNotNull(reviewSector);
        final String fieldString = ""//
                + "{|X| - |X||X| -  -  - }"//
                + "{|X||X| - |X| - |X||X|}"//
                + "{ -  - |X||X||X| -  - }"//
                + "{|X||X| - |X| - |X| - }";
        final Cell[][] expected = FieldFormatUtil.getField(fieldString);
        reviewSector.setSector(expected);
        assertTrue(reviewSector.getOccupied().containsAll(Arrays.asList(//
                new Cell(0, 0), new Cell(2, 0), new Cell(3, 0), new Cell(0, 1),
                new Cell(1, 1), new Cell(3, 1), new Cell(5, 1), new Cell(6, 1),
                new Cell(2, 2), new Cell(3, 2), new Cell(4, 2), new Cell(0, 3),
                new Cell(1, 3), new Cell(3, 3), new Cell(5, 3))));

    }
}
