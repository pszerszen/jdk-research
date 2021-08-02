package com.perunit.jdk.reserach.jdk12;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NewStringTest {

    @Test
    void testTransform() {
        String strResult = "hello".transform(input -> input + " world!");
        assertEquals("hello world!", strResult);

        int numResult = "42".transform(Integer::parseInt);
        assertEquals(42, numResult);
    }

    @Nested
    class IndentTest {

        private static final String MULTILINE_STRING = """
            This is
            a multiline
            string.
            """;

        private static final String INDENTED_STRING = """
               This is
               a multiline
               string.
            """;

        @Test
        void testPositiveArgument() {
            var actual = MULTILINE_STRING.indent(3);
            assertEquals(INDENTED_STRING, actual);
        }

        @Test
        void testNegativeArguments() {
            var expected = """
                 This is
                 a multiline
                 string.
                """;
            assertEquals(expected, INDENTED_STRING.indent(-2));

            assertEquals(MULTILINE_STRING, INDENTED_STRING.indent(-3));
            assertEquals(MULTILINE_STRING, INDENTED_STRING.indent(-10));
        }
    }
}
