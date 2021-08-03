package com.perunit.jdk.reserach.jdk17;

import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Slf4j
class PatternMatchingSwitchTest {

    @TestFactory
    Stream<DynamicTest> testSwitchPatternMatching() {
        return Stream.of(new Circle(), new Rectangle(), new Square())
            .map(shape -> DynamicTest.dynamicTest(shape.getClass().getCanonicalName(), () -> {
                var msg = switch (shape) {
                    case Circle c ->  "Circle: " + c;
                    case Rectangle r -> "Rectangle: " + r;
                    case Square s -> "Square: " + s;
                    default -> "Something else";
                };

                log.info(msg);
                assertNotEquals("Something else", msg);
            }));
    }
}
