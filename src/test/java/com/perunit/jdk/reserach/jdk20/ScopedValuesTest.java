package com.perunit.jdk.reserach.jdk20;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.sun.net.httpserver.Request;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ScopedValuesTest {

    Repository repository;
    UseCase useCase;
    RestAdapter restAdapter;
    Server server;

    @BeforeEach
    void setUp() {
        repository = new Repository();
        useCase = new UseCase(repository);
        restAdapter = new RestAdapter(useCase);
        server = new Server(restAdapter);
    }

    @Test
    void testScopedValue(@Mock Request request) {
        server.serve(request);

        assertThrows(NoSuchElementException.class, Server.LOGGED_IN_USER::get);
    }
}
