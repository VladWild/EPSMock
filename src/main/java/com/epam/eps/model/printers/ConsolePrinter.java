package com.epam.eps.model.printers;

import com.epam.eps.model.searcher.InjectRiskGroupsBeanProcessor;
import org.apache.log4j.Logger;

public class ConsolePrinter implements Printer{
    private final static Logger logger = Logger.getLogger(ConsolePrinter.class);

    @Override
    public void print(String text) {
        logger.info("Output text in console");
        System.out.println(text);
    }
}
