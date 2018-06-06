/**
 *
 */
package com.epam.eps;

import com.epam.eps.framework.core.EpsResourceBundleAnnotationContext;
import com.epam.eps.model.Report;
import com.epam.eps.model.printers.FactoryPrinters;
import com.epam.eps.model.printers.Printer;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.util.Locale;

/**
 * @author Alexander_Lotsmanov
 * <h2>Input requirements</h2>
 * <ol>
 * <li>������ ���������� � �������� ���������� <strong>JavaSE</strong>
 * (Core, Exceptions, Data Handling, Collections Framework)</li>
 * <li>���������� ������ Java (Class, Object, Abstract Class, Interface)
 * � ������� <strong>API</strong></li>
 * <li>�������� ��������� <strong>���</strong>, <strong>Design
 * principles</strong> (SOLID, YAGNY, KISS, Low Coupling, High
 * Cohesion)</li>
 * <li>����� ������������� ���������� ������ � ���������� JVM</li>
 * <li>������ ������ � <strong>IDE Eclipce</strong>
 * (<strong>IDEA</strong>)</li>
 * <li>������ ������ � <strong>Git</strong></li>
 * <li>������ ������ � <strong>Maven</strong></li>
 * </ol>
 * <h2>Output requirements</h2>
 * <ol>
 * <li>������ ��������� ��������� ��� ����� <strong>Application
 * Context</strong></li>
 * <p>
 * <li>���������������� ���������� � �������
 * <strong>Annotations</strong></li>
 * <li>��������� ���������� <strong>Reflection</strong> � Java</li>
 * <li>��������� <strong>Dependency Injection</strong> �
 * <strong>Inversion of Control</strong></li>
 * <li>����������������� ����������</li>
 * <li>������ � <strong>.properties</strong> �
 * <strong>ResourceBundle</strong></li>
 * <li>��������� ���������� � ������� ���������������� ���������
 * ������</li>
 * </ol>
 */
public class EmergencyPreventionSystem {
    private static final String REPORT_ID = "eps.bean.id.simpleReport";

    private final static Logger logger = Logger.getLogger(EmergencyPreventionSystem.class);

    private void run() throws IOException {
        Locale.setDefault(new Locale("en"));
        EpsResourceBundleAnnotationContext epsResourceBundleAnnotationContext = new EpsResourceBundleAnnotationContext(
                "config");
        epsResourceBundleAnnotationContext.init();

        Report report = (Report) epsResourceBundleAnnotationContext.getEPSBean(REPORT_ID);

        Printer printer = FactoryPrinters.getTypePrinter(FactoryPrinters.FILE);
        printer.print(report.getReport());
    }

    public static void main(String[] args) {
        //PropertyConfigurator.configure("src/main/resources/log4j.properties");
        PropertyConfigurator.configure("src/main/resources/log4j.xml");
        logger.info("start application");
        logger.debug("cdcce wcetyhwc");
        logger.debug("cdcce wceewewdewdewdtyhwc");
        logger.trace("trace");
        logger.error("error message ");

        EmergencyPreventionSystem emergencyPreventionSystem = new EmergencyPreventionSystem();
        try {
            emergencyPreventionSystem.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
