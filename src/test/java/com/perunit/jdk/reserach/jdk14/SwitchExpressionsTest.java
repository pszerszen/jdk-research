package com.perunit.jdk.reserach.jdk14;

import java.time.DayOfWeek;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SwitchExpressionsTest {

    @EnumSource(DayOfWeek.class)
    @ParameterizedTest
    void testValueReturn(DayOfWeek day) {
        int oldStyle;
        switch (day) {
            case MONDAY:
            case FRIDAY:
            case SUNDAY:
                oldStyle = 6;
                break;
            case TUESDAY:
                oldStyle = 7;
                break;
            case THURSDAY:
            case SATURDAY:
                oldStyle = 8;
                break;
            case WEDNESDAY:
                oldStyle = 9;
                break;
            default:
                throw new IllegalStateException("What: " + day);
        }

        int semiOldStyle = switch (day) {
            case MONDAY, FRIDAY, SUNDAY:
                yield 6;
            case TUESDAY:
                yield 7;
            case THURSDAY, SATURDAY:
                yield 8;
            case WEDNESDAY:
                yield 9;
        };

        int newStyle = switch (day) {
            case MONDAY, FRIDAY, SUNDAY -> 6;
            case TUESDAY -> 7;
            case THURSDAY, SATURDAY -> 8;
            case WEDNESDAY -> 9;
        };

        assertEquals(oldStyle, semiOldStyle);
        assertEquals(semiOldStyle, newStyle);
    }
}
