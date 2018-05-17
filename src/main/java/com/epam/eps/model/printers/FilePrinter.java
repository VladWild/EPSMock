package com.epam.eps.model.printers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FilePrinter implements Printer {
    private static final String PATH_FILE = "src\\main\\resources\\output";

    @Override
    public void print(String text) throws IOException {
        File file = new File(PATH_FILE);
        FileWriter writer = new FileWriter(file);
        writer.write(text);
        writer.close();
    }
}

