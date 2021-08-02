package com.perunit.jdk.reserach.jdk12;

import java.text.CompactNumberFormat;
import java.text.NumberFormat.Style;
import java.util.Locale;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompactNumberFormatTest {

    @CsvSource({
        "100,100",
        "1000,1 tysiąc",
        "100000,100 tysięcy",
        "1000000,1 milion"
    })
    @ParameterizedTest
    void testLongNumberFormatPl(long input, String expected) {
        var numberFormat = CompactNumberFormat.getCompactNumberInstance(new Locale("pl", "PL"), Style.LONG);

        assertEquals(expected, numberFormat.format(input));
    }

    @CsvSource({
        "100,100",
        "1000,1K",
        "100000,100K",
        "1000000,1M"
    })
    @ParameterizedTest
    void testShortNumberFormatEn(long input, String expected) {
        var numberFormat = CompactNumberFormat.getCompactNumberInstance(Locale.ENGLISH, Style.SHORT);

        assertEquals(expected, numberFormat.format(input));
    }
}
