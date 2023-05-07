package com.perunit.jdk.reserach.jdk20;

import java.util.UUID;

public record UseCase(Repository repository) {

    public void invoke(UUID id) {
        // ...
        Data data = repository.getData(id);
        // ...
    }
}
