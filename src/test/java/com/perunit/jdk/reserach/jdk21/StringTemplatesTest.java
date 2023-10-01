package com.perunit.jdk.reserach.jdk21;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringTemplatesTest {

    private static final String NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final int AGE = 32;

    @Test
    void testSimple() {
        var tested = STR. "Greetings \{ NAME }" ;

        assertThat(tested).isEqualTo("Greetings John");
    }

    @Test
    void testTextBlock() {
        var tested = STR. """
                {
                  "name": "\{ NAME }",
                  "lastName": "\{ LAST_NAME }",
                  "age": \{ AGE }
                }""" ;

        assertThat(tested).isEqualTo("""
                {
                  "name": "John",
                  "lastName": "Doe",
                  "age": 32
                }""");
    }

    @Test
    void testArithmeticExpressions() {
        int x = 10, y = 20;
        String tested = STR. "\{ x } + \{ y } = \{ x + y }" ;

        assertThat(tested).isEqualTo("10 + 20 = 30");
    }

}
