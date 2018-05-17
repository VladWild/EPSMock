package com.epam.eps.model.printers;

import java.io.IOException;

public interface Printer {
    void print(String text) throws IOException;
}
