package com.perunit.jdk.reserach.jdk11;

import java.util.List;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class NewStringTest {

    private static final String STRING = "foo bar ";

    private static Stream<Arguments> repeatParams() {
        return Stream.of(
            Arguments.of(0, ""),
            Arguments.of(1, STRING),
            Arguments.of(2, STRING + STRING)
        );
    }

    @MethodSource("repeatParams")
    @ParameterizedTest
    void testRepeat(int count, String expected) {
        var actual = STRING.repeat(count);
        log.info(actual);

        assertEquals(expected, actual);
    }

    @Test
    void testIsBlank() {
        assertTrue("".isBlank());
        assertTrue(" ".isBlank());
        assertTrue(" \n ".isBlank());

        assertFalse(STRING.isBlank());
    }

    @Test
    void testLines() {
        var string = "foo\nbar\n\nfoo\nbar";

        var lines = string.lines().toList();

        assertEquals(
            List.of(
                "foo",
                "bar",
                "",
                "foo",
                "bar"),
            lines);
    }

    @Nested
    class StripTest {

        @Test
        void testSame() {
            var s = "\t abc \n";

            assertEquals("abc", s.trim());
            assertEquals("abc", s.strip());
        }

        @Test
        void testDifferent() {
            var c = '\u2000';
            var s = c + "abc" + c;

            assertTrue(Character.isWhitespace(c));
            assertEquals(s, s.trim());
            assertEquals("abc", s.strip());
        }

        @Test
        void testSideStrip() {
            var s = "\t abc \n";

            assertEquals("abc \n", s.stripLeading());
            assertEquals("\t abc", s.stripTrailing());
        }
    }
}
