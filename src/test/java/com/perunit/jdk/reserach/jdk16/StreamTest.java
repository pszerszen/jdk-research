package com.perunit.jdk.reserach.jdk16;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
        var multiplier = 3;

        var objects = input.stream()
            .mapMulti((value, processor) ->
                IntStream.iterate(0, i -> i < multiplier, i -> i + 1)
                    .forEach(i -> processor.accept(value)))
            .toList();
        assertEquals(input.size() * multiplier, objects.size());
        assertEquals(List.of(
                "123", "123", "123",
                "abc", "abc", "abc",
                "!@#", "!@#", "!@#")
            , objects);
    }

}
