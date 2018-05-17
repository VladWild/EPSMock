package com.epam.eps.model.functionality;

import com.epam.eps.framework.core.EpsResourceBundleAnnotationContext;
import com.epam.eps.framework.core.Group;
import com.epam.eps.model.algorithm.FactoryAlgorithms;
import com.epam.eps.model.risk.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Locale;
import java.util.MissingResourceException;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CountGroupRisksTest {
    private static final String RISK_NONE_ID = "eps.bean.id.risk.none";
    private static final String RISK_MINOR_ID = "eps.bean.id.risk.minor";
    private static final String RISK_NORMAL_ID = "eps.bean.id.risk.normal";
    private static final String RISK_MAJOR_ID = "eps.bean.id.risk.major";
    private static final String RISK_CRITICAL_ID = "eps.bean.id.risk.critical";

    private EpsResourceBundleAnnotationContext epsResourceBundleAnnotationContext;

    @SuppressWarnings("unused")
    static final String FIELD_STRING = ""// it is for example
            + "{|X| -  -  - |X||X||X||X| -  - }"
            + "{|X||X| - |X||X| -  -  -  - |X|}"
            + "{|X||X||X| -  - |X||X| -  -  - }"
            + "{|X| -  - |X| -  - |X||X||X| - }"
            + "{ - |X| - |X| - |X||X| -  - |X|}"
            + "{|X||X| - |X||X| -  - |X| -  - }"
            + "{|X||X||X| -  - |X| - |X| - |X|}"
            + "{|X||X| - |X||X| -  - |X| -  - }"
            + "{|X| - |X||X||X| -  -  -  -  - }"
            + "{|X||X| -  -  -  - |X||X| - |X|}";

    @Before
    public void createContext() {
        Locale.setDefault(new Locale("en"));
        epsResourceBundleAnnotationContext = new EpsResourceBundleAnnotationContext(
                "test_config");
        try {
            epsResourceBundleAnnotationContext.init();
        } catch (IllegalArgumentException | MissingResourceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void countGroupNoneRiskTest(){
        NoneRisk noneRisk = (NoneRisk) epsResourceBundleAnnotationContext.getEPSBean(RISK_NONE_ID);
        assertEquals(6, noneRisk.getGroups().size());
    }

    @Test
    public void countGroupMinorRiskTest(){
        MinorRisk minorRisk = (MinorRisk) epsResourceBundleAnnotationContext.getEPSBean(RISK_MINOR_ID);
        assertEquals(2, minorRisk.getGroups().size());
    }

    @Test
    public void countGroupNormalRiskTest(){
        NormalRisk normalRisk = (NormalRisk) epsResourceBundleAnnotationContext.getEPSBean(RISK_NORMAL_ID);
        assertEquals(2, normalRisk.getGroups().size());
    }

    @Test
    public void countGroupMajorRiskTest(){
        MajorRisk majorRisk = (MajorRisk) epsResourceBundleAnnotationContext.getEPSBean(RISK_MAJOR_ID);
        System.out.println(majorRisk.getGroups());
        assertEquals(2, majorRisk.getGroups().size());
    }

    @Test
    public void countGroupCriticalRiskTest(){
        CriticalRisk criticalRisk = (CriticalRisk) epsResourceBundleAnnotationContext.getEPSBean(RISK_CRITICAL_ID);
        assertEquals(1, criticalRisk.getGroups().size());
    }
}

