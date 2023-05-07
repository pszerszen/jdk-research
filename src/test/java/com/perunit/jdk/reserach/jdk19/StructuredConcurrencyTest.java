package com.perunit.jdk.reserach.jdk19;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import jdk.incubator.concurrent.StructuredTaskScope;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        assertThatThrownBy(() -> {
            try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
                Future<Shelter> shelter = scope.fork(this::getShelter);
                Future<List<Dog>> dogs = scope.fork(this::getDogsWithException);
                scope.throwIfFailed(e -> new RuntimeException(ERROR_MESSAGE));
                scope.join();
                Response response = new Response(shelter.resultNow(), dogs.resultNow());

                assertResponseCorrect(response);
            }
        }).isInstanceOf(RuntimeException.class)
            .hasMessage(ERROR_MESSAGE);
    }

    @Test
    void whenSlowTasksReachesDeadline_thenCorrect() {
        assertThatThrownBy(() -> {
            try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
                Future<Shelter> shelter = scope.fork(this::getShelter);
                Future<List<Dog>> dogs = scope.fork(this::getDogsSlowly);
                scope.throwIfFailed(e -> new RuntimeException(ERROR_MESSAGE));
                scope.join();
                scope.joinUntil(Instant.now().plusMillis(50));
                Response response = new Response(shelter.resultNow(), dogs.resultNow());

                assertResponseCorrect(response);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void whenResultNow_thenCorrect() {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Future<Shelter> shelter = scope.fork(this::getShelter);
            Future<List<Dog>> dogs = scope.fork(this::getDogs);
            scope.join();

            Response response = new Response(shelter.resultNow(), dogs.resultNow());

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
