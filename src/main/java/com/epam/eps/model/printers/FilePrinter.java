package com.epam.eps.model.printers;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FilePrinter implements Printer {
    private static final String PATH_FILE = "src\\main\\resources\\output";

    @Override
    public void print(String text) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(PATH_FILE), StandardCharsets.UTF_8))) {
            writer.write(text.toCharArray());
        }
    }
}

