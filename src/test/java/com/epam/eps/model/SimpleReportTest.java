package com.epam.eps.model;

        import com.epam.eps.framework.core.Cell;
        import com.epam.eps.framework.core.EpsResourceBundleAnnotationContext;
        import com.epam.eps.framework.support.FieldFormatUtil;
        import org.junit.Before;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.mockito.runners.MockitoJUnitRunner;

        import java.util.MissingResourceException;
        import java.util.regex.Pattern;

        import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SimpleReportTest {
    private static final String BEAN_OUT_OF_CONTEXT_MSG = "Bean \"%s\" out of the context \"%s\".";

    private static final String REPORT_DONT_MATCH_TO_REGEXP_MSG = "Incorrect report from \"%s\". Don't match to \"%s\" regexp.";

    private Report epsReport;
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
            epsReport = epsResourceBundleAnnotationContext
                    .getEPSBean(Report.class);
        } catch (IllegalArgumentException e) {
            epsReport = null;
        }
    }

    @Test
    public void beanInContextTest() {
        final String msg = String.format(BEAN_OUT_OF_CONTEXT_MSG, Report.class,
                epsResourceBundleAnnotationContext.getClass());
        assertNotNull(msg, epsReport);
    }

    @Test
    public void getReportTest() {
        final String regex = ""//
                + "(?s).\\nEMERGENCY PREVENTION SYSTEM(?s).\\n-{80}(?s).\\n-{80}(?s).\\n"//
                + "Field's width is M = \\d+(?s).\\n"//
                + "Field's height is N = \\d+((?s).\\n){2}"//
                + "Entered ranks plank with following borders:(?s).\\n"//
                + "NoneRisk = \\d{1,10}-\\d{1,10};(?s).\\n"//
                + "MinorRisk = \\d{1,10}-\\d{1,10};(?s).\\n"//
                + "NormalRisk = \\d{1,10}-\\d{1,10};(?s).\\n"//
                + "MajorRisk = \\d{1,10}-\\d{1,10};(?s).\\n"//
                + "CriticalRisk = \\d{1,10}-\\d{1,10};((?s).\\n){2}"//
                + "-{80}(?s).\\n(?s).*[ \\d\\n|X-]*"//
                + "Risk groups report:(?s).\\n-{80}(?s).\\n"//
                + "NoneRisk: \\d+ groups;(?s).\\n"//
                + "MinorRisk: \\d+ groups;(?s).\\n"//
                + "NormalRisk: \\d+ groups;(?s).\\n"//
                + "MajorRisk: \\d+ groups;(?s).\\n"//
                + "CriticalRisk: \\d+ groups;";
        if (epsReport != null) {
            final String msg = String.format(REPORT_DONT_MATCH_TO_REGEXP_MSG,
                    epsReport.getClass(), regex);
            assertTrue(msg, Pattern.compile(regex)
                    .matcher(epsReport.getReport()).find());
        } else {
            assert epsReport != null;
        }
    }

    @Test
    public void getSectorTest() {
        final String ENTER = String.format("%n");
        final String fieldString = ""//
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
        final String expected = ""//
                + "     0  1  2  3  4  5  6  7  8  9 10" + ENTER
                + "  0 |X| - |X||X| -  -  - |X||X| - |X|" + ENTER
                + "  1 |X||X| - |X| - |X||X||X||X| - |X|" + ENTER
                + "  2 |X||X| -  - |X||X||X| -  - |X| - " + ENTER
                + "  3 |X||X| - |X||X| - |X||X||X| -  - " + ENTER
                + "  4  - |X| -  -  -  - |X| - |X| - |X|" + ENTER
                + "  5 |X| -  -  - |X||X| -  - |X||X||X|" + ENTER
                + "  6 |X| -  -  - |X||X||X| - |X| - |X|" + ENTER
                + "  7  -  -  -  -  -  -  - |X||X| - |X|" + ENTER
                + "  8 |X||X| - |X| -  -  -  - |X| -  - " + ENTER
                + "  9  - |X||X||X||X||X| - |X| - |X||X|" + ENTER
                + " 10  - |X| -  - |X||X| - |X| -  - |X|" + ENTER;
        Cell[][] field = FieldFormatUtil.getField(fieldString);
        if (epsReport != null) {
            assertEquals(expected, epsReport.getSector(field));
        } else {
            assert epsReport != null;
        }
    }
}


