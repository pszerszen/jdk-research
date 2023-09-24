package com.perunit.jdk.reserach.jdk21;

import org.junit.jupiter.api.Test;

import java.util.*;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;

class SequencedCollectionsTest {

    @Test
    void testList() {
        SequencedCollection<Integer> tested = new ArrayList<>();

        tested.add(1);
        tested.addFirst(0);
        tested.addLast(2);
        tested.addFirst(-1);
        tested.addLast(3);

        assertThat(tested.getFirst()).isEqualTo(-1);
        assertThat(tested.getLast()).isEqualTo(3);
    }

    @Test
    void testSet() {
        SequencedSet<Integer> tested = new LinkedHashSet<>();

        tested.add(1);
        tested.addFirst(0);
        tested.addLast(2);
        tested.addFirst(-1);
        tested.addLast(3);

        assertThat(tested.getFirst()).isEqualTo(-1);
        assertThat(tested.getLast()).isEqualTo(3);
    }

    @Test
    void testMap() {
        SequencedMap<Integer, String> tested = new LinkedHashMap<>();
        tested.put(1, "One");
        tested.put(2, "Two");
        tested.put(3, "Three");

        assertThat(tested.firstEntry()).isEqualTo(entry(1, "One"));
        assertThat(tested.lastEntry()).isEqualTo(entry(3, "Three"));

        var first = tested.pollFirstEntry();   //1=One
        var last = tested.pollLastEntry();    //3=Three
        assertThat(tested).doesNotContain(first, last);

        tested.putFirst(1, "One");
        tested.putLast(3, "Three");

        assertThat(tested)
                .containsEntry(1, "One")
                .containsEntry(2, "Two")
                .containsEntry(3, "Three");

        var reversed = tested.reversed();
        assertThat(reversed.firstEntry()).isEqualTo(entry(3, "Three"));
        assertThat(reversed.lastEntry()).isEqualTo(entry(1, "One"));
    }
}
