package com.perunit.jdk.reserach.jdk16;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class RecordsTest {

    @Test
    void testExternalRecords() {
        assertDoesNotThrow(() -> {
            var point = new Point(3, 4);
            log.info("Point: {}", point);
        });

        assertThrows(IllegalArgumentException.class, () -> new Range(10, 9));
    }

    @Test
    void testInternalRecords() {
        record Pair(String key, String value){}

        var pair1 = new Pair("key1", "val");
        var pair2 = new Pair("key1", "val");

        assertEquals(pair1, pair2);
    }
}
