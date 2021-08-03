package com.perunit.jdk.reserach.jdk16;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StreamTest {

    @Test
    void testToList() {
        var input = List.of("123", "abc", "!@#");

        var oldStyle = input.stream().collect(Collectors.toList());
        var newStyle = input.stream().toList();

        assertEquals(oldStyle, newStyle);
    }

    @Test
    void testMapMulti() {
        var input = List.of("123", "abc", "!@#");

        var objects = input.stream()
            .mapMulti((s, consumer) -> {
                consumer.accept(s);
                consumer.accept(s);
                consumer.accept(s);
            })
            .toList();
        assertEquals(input.size() * 3, objects.size());
    }

}
