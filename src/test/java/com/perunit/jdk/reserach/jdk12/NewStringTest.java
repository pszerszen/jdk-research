package com.perunit.jdk.reserach.jdk12;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class NewStringTest {

    @Test
    void testTransform() {
        String strResult = "hello".transform(input -> input + " world!");
        assertThat(strResult).isEqualTo("hello world!");

        int numResult = "42".transform(Integer::parseInt);
        assertThat(numResult).isEqualTo(42);
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
            assertThat(actual).isEqualTo(INDENTED_STRING);
        }

        @Test
        void testNegativeArguments() {
            var expected = """
                 This is
                 a multiline
                 string.
                """;
            assertThat(INDENTED_STRING.indent(-2)).isEqualTo(expected);

            Stream.of(-3, -10).forEach(indent ->
                                           assertThat(INDENTED_STRING.indent(indent))
                                               .isEqualTo(MULTILINE_STRING));
        }
    }
}
