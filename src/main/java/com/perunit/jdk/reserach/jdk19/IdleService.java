package com.perunit.jdk.reserach.jdk19;

import java.time.Duration;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IdleService {

    @SneakyThrows
    public void doStuff() {
        Thread.sleep(Duration.ofSeconds(1L));
        // pretending to do something
    }
}
