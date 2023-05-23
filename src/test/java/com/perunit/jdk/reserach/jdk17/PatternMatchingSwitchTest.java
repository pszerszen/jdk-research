package com.perunit.jdk.reserach.jdk17;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class PatternMatchingSwitchTest {

    @TestFactory
    Stream<DynamicTest> testSwitchPatternMatching() {
        return Stream.of(new Circle(), new Rectangle(), new Square(), new Diamond(), new Trapeze(), new Parallelogram())
            .map(shape -> DynamicTest.dynamicTest(shape.getClass().getCanonicalName(), () -> {
                var msg = switch (shape) {
                    case Circle c -> "Circle: " + c;
                    case Square s -> "Square: " + s;
                    case Diamond d -> "Diamond: " + d;
                    case Rectangle r -> "Rectangle: " + r;
                    case Parallelogram p -> "Parallelogram: " + p;
                    case Trapeze t -> "Trapeze: " + t;
                    default -> "Something else"; // not needed if Shape is sealed-type
                };

                log.info(msg);
                assertThat(msg).isNotEqualTo("Something else");
            }));
    }
}
