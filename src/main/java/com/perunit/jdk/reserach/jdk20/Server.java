package com.perunit.jdk.reserach.jdk20;

import com.sun.net.httpserver.Request;

public record Server(RestAdapter restAdapter) {

    public static final ScopedValue<User> LOGGED_IN_USER = ScopedValue.newInstance();

    public void serve(Request request) {
        User loggedInUser = authenticateUser(request);
        ScopedValue.where(LOGGED_IN_USER, loggedInUser)
            .run(() -> restAdapter.processRequest(request));
    }

    private User authenticateUser(Request request) {
        return new User(true);
    }
}
