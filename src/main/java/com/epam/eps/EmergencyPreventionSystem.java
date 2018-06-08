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
 * <li>Знание синтаксиса и основных механизмов <strong>JavaSE</strong>
 * (Core, Exceptions, Data Handling, Collections Framework)</li>
 * <li>Абстракция данных Java (Class, Object, Abstract Class, Interface)
 * и понятие <strong>API</strong></li>
 * <li>Глубокое понимание <strong>ООП</strong>, <strong>Design
 * principles</strong> (SOLID, YAGNY, KISS, Low Coupling, High
 * Cohesion)</li>
 * <li>Общее представление устройства памяти и механизмов JVM</li>
 * <li>Навыки работы с <strong>IDE Eclipce</strong>
 * (<strong>IDEA</strong>)</li>
 * <li>Основы работы с <strong>Git</strong></li>
 * <li>Основы работы с <strong>Maven</strong></li>
 * </ol>
 * <h2>Output requirements</h2>
 * <ol>
 * <li>Должно появиться понимание что такое <strong>Application
 * Context</strong></li>
 * <p>
 * <li>Конфигурирование приложения с помощью
 * <strong>Annotations</strong></li>
 * <li>Понимание механизмов <strong>Reflection</strong> в Java</li>
 * <li>Понимание <strong>Dependency Injection</strong> и
 * <strong>Inversion of Control</strong></li>
 * <li>Интернациализация приложения</li>
 * <li>Работа с <strong>.properties</strong> и
 * <strong>ResourceBundle</strong></li>
 * <li>Настройка приложения с помощью конфигурационных текстовых
 * файлов</li>
 * </ol>
 */
public class EmergencyPreventionSystem {
    private static final String REPORT_ID = "eps.bean.id.simpleReport";

    private final static Logger logger = Logger.getLogger(EmergencyPreventionSystem.class);

    private void run() throws IOException {
        Locale.setDefault(new Locale("en"));

        logger.info("Creating a Context");
        EpsResourceBundleAnnotationContext epsResourceBundleAnnotationContext = new EpsResourceBundleAnnotationContext(
                "config");

        logger.info("Initializing the Context");
        epsResourceBundleAnnotationContext.init();

        logger.info("Get report of application \"Emergency Prevention System\" from context");
        Report report = (Report) epsResourceBundleAnnotationContext.getEPSBean(REPORT_ID);

        logger.info("Creating printer for the report");
        Printer printer = FactoryPrinters.getTypePrinter(FactoryPrinters.FILE);
        logger.info("Printing report");
        printer.print(report.getReport());
    }

    public static void main(String[] args) {
        //PropertyConfigurator.configure("src/main/resources/log4j.properties");
        PropertyConfigurator.configure("src/main/resources/log4j.xml");

        logger.info("Application \"Emergency Prevention System\" is start");

        EmergencyPreventionSystem emergencyPreventionSystem = new EmergencyPreventionSystem();
        try {
            logger.info("Application \"Emergency Prevention System\" is run");
            emergencyPreventionSystem.run();
        } catch (IOException e) {
            logger.error("The output file is not found. Exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
