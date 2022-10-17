package com.perunit.jdk.reserach.jdk12;

import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;

import java.text.CompactNumberFormat;
import java.text.NumberFormat.Style;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@Slf4j
class CompactNumberFormatTest {

    private static final Locale POLISH = new Locale("pl", "PL");

    @CsvSource({
        "100,100",
        "1000,1 tysiąc",
        "100000,100 tysięcy",
        "1000000,1 milion"
    })
    @ParameterizedTest
    void testLongNumberFormatPl(long input, String expected) {
        var numberFormat = CompactNumberFormat.getCompactNumberInstance(POLISH, Style.LONG);
        var formatted = numberFormat.format(input);

        log.info("CompactNumberFormat formatted {} to {}", input, formatted);
        assertThat(formatted).isEqualTo(expected);
    }

    @CsvSource({
        "100,100",
        "1000,1K",
        "100000,100K",
        "1000000,1M"
    })
    @ParameterizedTest
    void testShortNumberFormatEn(long input, String expected) {
        var numberFormat = CompactNumberFormat.getCompactNumberInstance(ENGLISH, Style.SHORT);
        var formatted = numberFormat.format(input);

        log.info("CompactNumberFormat formatted {} to {}", input, formatted);
        assertThat(formatted).isEqualTo(expected);
    }
}
