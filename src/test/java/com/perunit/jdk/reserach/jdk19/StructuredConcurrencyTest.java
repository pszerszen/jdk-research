package com.perunit.jdk.reserach.jdk19;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.StructuredTaskScope;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StructuredConcurrencyTest {

    private static final String ERROR_MESSAGE = "Failed to get the result";

    @Test
    @DisplayName("Test unstructured concurrency")
    void whenGet_thenCorrect() {
        Future<Shelter> shelter;
        Future<List<Dog>> dogs;
        try (var executorService = Executors.newFixedThreadPool(3)) {
            shelter = executorService.submit(this::getShelter);
            dogs = executorService.submit(this::getDogs);
            Shelter theShelter = shelter.get();   // Join the shelter
            List<Dog> theDogs = dogs.get();  // Join the dogs
            Response response = new Response(theShelter, theDogs);

            assertResponseCorrect(response);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void whenThrowingException_thenCorrect() {
        ThrowingCallable callable = () -> {
            try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
                var shelter = scope.fork(this::getShelter);
                var dogs = scope.fork(this::getDogsWithException);
                scope.join();
                scope.throwIfFailed(e -> new RuntimeException(ERROR_MESSAGE));
                Response response = new Response(shelter.get(), dogs.get());

                assertResponseCorrect(response);
            }
        };
        assertThatThrownBy(callable).isInstanceOf(RuntimeException.class)
            .hasMessage(ERROR_MESSAGE);
    }

    @Test
    void whenSlowTasksReachesDeadline_thenCorrect() {
        ThrowingCallable callable = () -> {
            try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
                var shelter = scope.fork(this::getShelter);
                var dogs = scope.fork(this::getDogsSlowly);
                scope.throwIfFailed(e -> new RuntimeException(ERROR_MESSAGE));
                scope.join();
                scope.joinUntil(Instant.now().plusMillis(50));
                Response response = new Response(shelter.get(), dogs.get());

                assertResponseCorrect(response);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        assertThatThrownBy(callable).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void whenResultNow_thenCorrect() {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var shelter = scope.fork(this::getShelter);
            var dogs = scope.fork(this::getDogs);
            scope.join();

            Response response = new Response(shelter.get(), dogs.get());

            assertResponseCorrect(response);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Shelter getShelter() {
        return new Shelter("Shelter");
    }

    private List<Dog> getDogs() {
        return List.of(new Dog("Buddy"), new Dog("Simba"));
    }

    private List<Dog> getDogsWithException() {
        throw new RuntimeException(ERROR_MESSAGE);
    }

    private List<Dog> getDogsSlowly() throws InterruptedException {
        Thread.sleep(1500);
        throw new RuntimeException(ERROR_MESSAGE);
    }


    private static void assertResponseCorrect(Response response) {
        assertThat(response).isNotNull()
            .extracting(Response::shelter)
            .isNotNull();
        assertThat(response)
            .extracting(Response::dogs)
            .isNotNull()
            .asList()
            .hasSize(2);
    }
}
