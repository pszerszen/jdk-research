package com.perunit.jdk.reserach.jdk16;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class StreamTest {

    @Test
    void testToList() {
        var input = List.of("123", "abc", "!@#");

        var oldStyle = input.stream().collect(Collectors.toList());
        var newStyle = input.stream().toList();

        assertThat(newStyle).isEqualTo(oldStyle);
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
        assertThat(objects).hasSize(input.size() * multiplier);
        assertThat(objects).isEqualTo(List.of(
            "123", "123", "123",
            "abc", "abc", "abc",
            "!@#", "!@#", "!@#"));
    }

}
