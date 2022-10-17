package com.perunit.jdk.reserach.jdk18;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JavadocSnippets {

    /**
     * How to write a text file with Java 7:
     * <p>
     * {@snippet :
     * try (BufferedWriter writer = Files.newBufferedWriter(path)) {
     *   writer.write(text);
     * }
     *}
     *
     * @param path destination
     * @param text to be written
     */
    private void method(Path path, String text) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(text);
        }
    }
}
