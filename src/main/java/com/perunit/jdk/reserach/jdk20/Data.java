package com.perunit.jdk.reserach.jdk20;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public record Data(UUID id, AtomicBoolean admin) {

    public Data(UUID id) {
        this(id, new AtomicBoolean(false));
    }
}
