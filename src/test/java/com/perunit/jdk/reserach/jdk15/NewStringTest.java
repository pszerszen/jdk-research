package com.perunit.jdk.reserach.jdk15;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class NewStringTest {

    private static final String TEMPLATE = """
                                           {
                                             "name": "%s",
                                             "lastName": "%s",
                                             "age": "%d"
                                           }""";

    @Test
    void testFormatted() {
        var params = new Object[]{"John", "Doe", 21};

        assertThat(TEMPLATE.formatted(params))
            .isEqualTo(String.format(TEMPLATE, params));
    }

    @Test
    void testStripIndent() {
        assertThat("""
                                 {
                                   "name": "%s",
                                   "lastName": "%s",
                                   "age": "%d"
                                 }""".stripIndent())
            .isEqualTo(TEMPLATE);
    }

}
