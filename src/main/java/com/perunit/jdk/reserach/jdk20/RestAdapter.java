package com.perunit.jdk.reserach.jdk20;

import com.sun.net.httpserver.Request;
import java.util.UUID;

public record RestAdapter(UseCase useCase) {

    public void processRequest(Request request) {
        // ...
        UUID id = extractId(request);
        useCase.invoke(id);
        // ...
    }

    private UUID extractId(Request request) {
        return UUID.randomUUID();
    }
}
