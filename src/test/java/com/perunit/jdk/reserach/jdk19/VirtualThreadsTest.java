package com.perunit.jdk.reserach.jdk19;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;
import static java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor;
import static org.mockito.Mockito.*;

@Slf4j
class VirtualThreadsTest {

    @Test
    void testThreadBuilders() {
        Runnable printThread = () -> log.info("{}", Thread.currentThread());

        var virtualThreadFactory = Thread.ofVirtual().factory();
        var kernelThreadFactory = Thread.ofPlatform().factory();

        var virtualThread = virtualThreadFactory.newThread(printThread);
        var kernelThread = kernelThreadFactory.newThread(printThread);

        virtualThread.start();
        kernelThread.start();
    }

    @Test
    @SneakyThrows
    void testConcurrentMorningRoutineUsingExecutors() {
        try (var executor = newVirtualThreadPerTaskExecutor()) {
            var bathTime = executor.submit(this::takeABath);
            var boilingWater = executor.submit(this::boilWater);

            boilingWater.get();
            bathTime.get();
        }
    }

    @Test
    void testManyVirtualThreads() {
        var iterations = 10_000;
        var idleService = spy(IdleService.class);
        try (var executor = newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, iterations).forEach(i -> executor.submit(idleService::doStuff));
        }
        verify(idleService, times(iterations)).doStuff();
    }

    @SneakyThrows
    private void takeABath() {
        log.info("I'm going to take a bath");
        sleep(Duration.ofMillis(500L));
        log.info("I'm done with the bath");
    }

    @SneakyThrows
    private void boilWater() {
        log.info("I'm going to boil some water");
        sleep(Duration.ofSeconds(1L));
        log.info("I'm done with the water");
    }
}
