package com.perunit.jdk.reserach.jdk19;


import static org.assertj.core.api.Assertions.assertThat;

import com.perunit.jdk.reserach.jdk16.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecordPatternsTest {

    private static final Point point = new Point(12, 13);

    private Object obj;

    @BeforeEach
    void setUp() {
        obj = point;
    }

    @Test
    void testRecordPatterns() {
        if (obj instanceof Point(int x, int y)) {
            assertThat(x + y).isEqualTo(25);
        }
    }
}
