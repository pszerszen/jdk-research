package com.perunit.jdk.reserach.jdk19;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RecordPatternsTest {

    private final Object object = new Location("Home", new GPSPoint(1.0, 2.0));

    @Test
    @DisplayName("Test Pattern Matching in old way")
    void testOldWay() {
        // old code
        if (object instanceof Location l) {
            assertThat(l)
                    .returns("Home", Location::name)
                    .extracting(Location::gpsPoint)
                    .returns(1.0, GPSPoint::lat)
                    .returns(2.0, GPSPoint::lng);
        }
    }

    @Test
    @DisplayName("Test Patten Matching on high level")
    void testHighLevelMatching() {
        if (object instanceof Location(var name, var gpsPoint)) {
            assertThat(name).isEqualTo("Home");
            assertThat(gpsPoint)
                    .returns(1.0, GPSPoint::lat)
                    .returns(2.0, GPSPoint::lng);
        }
    }

    @Test
    @DisplayName("Test Pattern Matching on low level")
    void testLowLevelMatching() {
        if (object instanceof Location(var name, GPSPoint(var lat, var lng))) {
            assertThat(name).isEqualTo("Home");
            assertThat(lat).isEqualTo(1.0);
            assertThat(lng).isEqualTo(2.0);
        }
    }

    @Test
    @DisplayName("Test Pattern Matching on super deep level")
    void givenObject_whenTestGenericTypeInstanceOf_shouldMatch() {
        var locationWrapper = new LocationWrapper<>((Location) object, "Description");
        if (locationWrapper instanceof LocationWrapper<Location>(
                Location(var name, GPSPoint(var lat, var lng)), var description
        )) {
            assertThat(name).isEqualTo("Home");
            assertThat(lat).isEqualTo(1.0);
            assertThat(lng).isEqualTo(2.0);
            assertThat(description).isEqualTo("Description");
        }
    }

    private static Stream<Arguments> switchPatternMatching() {
        var gpsPoint = new GPSPoint(1.0, 2.0);
        var location = new Location("Home", gpsPoint);
        var wrapper = new LocationWrapper<>(location, "Description");
        return Stream.of(
                Arguments.of(gpsPoint, "1.0"),
                Arguments.of(location, location.name()),
                Arguments.of(wrapper, wrapper.description()),
                Arguments.of(new Dog("Skippy"), "default"));
    }

    @DisplayName("Test switch pattern matching")
    @ParameterizedTest(name = "{0} should give {1}")
    @MethodSource("switchPatternMatching")
    void testSwitchPatternMatching(Object object, String expected) {
        String result = switch (object) {
            case Location l -> l.name();
            case LocationWrapper<?> w -> w.description();
            case GPSPoint g -> String.valueOf(g.lat());
            default -> "default";
        };
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> switchPatternMatching2() {
        var gpsPoint = new GPSPoint(1.0, 2.0);
        var location = new Location("Home", gpsPoint);
        var otherLocation = new Location("Other", new GPSPoint(1.0, 2.0));
        var wrapper = new LocationWrapper<>(location, "Description");
        var otherWrapper = new LocationWrapper<>(otherLocation, "qwerty");
        return Stream.of(
                Arguments.of(gpsPoint, "1.0"),
                Arguments.of(location, "Test"),
                Arguments.of(otherLocation, otherLocation.name()),
                Arguments.of(wrapper, wrapper.t().name()),
                Arguments.of(otherWrapper, wrapper.description()));
    }

    @DisplayName("Test guarded pattern matching in switch")
    @ParameterizedTest(name = "{0} should give {1}")
    @MethodSource({"switchPatternMatching2"})
    void testGuardedSwitchPatternMatching(Object object, String expected) {
        val result = switch (object) {
            case Location(var name, var ignored)
                    when name.equals("Home") -> "Test";
            case Location(var name, var ignored) -> name;
            case LocationWrapper<?>(Location(var name, GPSPoint(var lat, var lon)), var description)
                    when description.equals("Description") -> name;
            case LocationWrapper<?>(Location(var name, GPSPoint(var lat, var lon)), var description) -> description;
            case GPSPoint(var lat, var lon) -> String.valueOf(lat);
            default -> "default";
        };
    }
}
