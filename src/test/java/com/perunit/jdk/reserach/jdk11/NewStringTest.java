package com.perunit.jdk.reserach.jdk11;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testIsBlank() {
        assertTrue("".isBlank());
        assertTrue(" ".isBlank());
        assertTrue(" \n ".isBlank());

        assertThat(STRING).isNotBlank();
    }

    @Test
    void testLines() {
        var string = "foo\nbar\n\nfoo\nbar";

        var lines = string.lines().toList();

        assertThat(lines).isEqualTo(List.of(
            "foo",
            "bar",
            "",
            "foo",
            "bar"));
    }

    @Nested
    class StripTest {

        @Test
        void testSame() {
            var s = "\t abc \n";

            assertThat(s.trim()).isEqualTo("abc");
            assertThat(s.strip()).isEqualTo("abc");
        }

        @Test
        void testDifferent() {
            var c = '\u2000';
            var s = c + "abc" + c;

            assertThat(Character.isWhitespace(c)).isTrue();
            assertThat(s.trim()).isEqualTo(s);
            assertThat(s.strip()).isEqualTo("abc");
        }

        @Test
        void testSideStrip() {
            var s = "\t abc \n";

            assertThat(s.stripLeading()).isEqualTo("abc \n");
            assertThat(s.stripTrailing()).isEqualTo("\t abc");
        }
    }
}
