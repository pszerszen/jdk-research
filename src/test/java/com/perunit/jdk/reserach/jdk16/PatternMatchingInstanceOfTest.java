package com.perunit.jdk.reserach.jdk16;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class PatternMatchingInstanceOfTest {

    @Test
    void testPatternMatching() {
        var list = List.of("abc", "123", "!@#");
        assertThat(collectionsAwareToStringNew(list))
            .isEqualTo(collectionsAwareToStringOld(list));

        var map = Map.of(
            "k1", "val1",
            "k2", 123,
            "k3", 9.09
                        );
        assertThat(collectionsAwareToStringNew(map))
            .isEqualTo(collectionsAwareToStringOld(map));
        record Point(int x, int y) {}
        var point = new Point(3, 4);
        assertThat(collectionsAwareToStringNew(point))
            .isEqualTo(collectionsAwareToStringOld(point));
    }

    private String collectionsAwareToStringOld(Object o) {
        if (o instanceof Collection) {
            Collection<?> c = (Collection<?>) o;
            return c.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ", "[", "]"));
        } else if (o instanceof Map) {
            Map<?, ?> m = (Map<?, ?>) o;
            return m.entrySet().stream()
                .map(e -> e.getKey() + " -> " + e.getValue())
                .collect(Collectors.joining(", ", "{", "}"));
        } else {
            return o.toString();
        }
    }

    private String collectionsAwareToStringNew(Object o) {
        if (o instanceof Collection<?> c) {
            return c.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ", "[", "]"));
        } else if (o instanceof Map<?, ?> m) {
            return m.entrySet().stream()
                .map(e -> e.getKey() + " -> " + e.getValue())
                .collect(Collectors.joining(", ", "{", "}"));
        } else {
            return o.toString();
        }
    }
}
