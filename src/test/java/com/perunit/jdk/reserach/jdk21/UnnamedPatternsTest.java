package com.perunit.jdk.reserach.jdk21;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class UnnamedPatternsTest {

    @Test
    void testExceptionCatch() {
        assertDoesNotThrow(() -> {
            var str = "NaN";
            try {
                Integer.parseInt(str);
            } catch (NumberFormatException _) {
            }
        });
    }

    private static Stream<Arguments> switchArguments() {
        return Stream.of(
                Arguments.of(Byte.MIN_VALUE, "Input is a number"),
                Arguments.of(Short.MIN_VALUE, "Input is a number"),
                Arguments.of(Integer.MIN_VALUE, "Input is a number"),
                Arguments.of(Long.MIN_VALUE, "Input is a number"),
                Arguments.of(Float.MIN_VALUE, "Input is a floating-point number"),
                Arguments.of(Double.MIN_VALUE, "Input is a floating-point number"),
                Arguments.of("   ", "Input is a string"),
                Arguments.of(List.of(), "Object type not expected"));
    }

    @ParameterizedTest
    @MethodSource("switchArguments")
    void testSwitch(Object obj, String expected) {
        var result = switch (obj) {
            case Byte _, Short _, Integer _, Long _ -> "Input is a number";
            case Float _, Double _ -> "Input is a floating-point number";
            case String _ -> "Input is a string";
            default -> "Object type not expected";
        };
        assertThat(result).isEqualTo(expected);
    }
}
