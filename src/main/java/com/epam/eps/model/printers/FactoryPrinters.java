package com.epam.eps.model.printers;

public enum FactoryPrinters {
    CONSOLE {
        @Override
        protected Printer getPrinter() {
            return new ConsolePrinter();
        }
    }, FILE {
        @Override
        protected Printer getPrinter() {
            return new FilePrinter();
        }
    };

    abstract protected Printer getPrinter();

    public static Printer getTypePrinter(FactoryPrinters typePrinters){
        return typePrinters.getPrinter();
    }
}

