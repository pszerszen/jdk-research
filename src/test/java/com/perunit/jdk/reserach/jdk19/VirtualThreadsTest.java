package com.perunit.jdk.reserach.jdk19;

import static java.lang.Thread.sleep;

import java.time.Duration;
import java.util.concurrent.Executors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

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
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var bathTime = executor.submit(this::takeABath);
            var boilingWater = executor.submit(this::boilWater);

            boilingWater.get();
            bathTime.get();
        }
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
