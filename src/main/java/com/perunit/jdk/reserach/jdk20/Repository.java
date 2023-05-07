package com.perunit.jdk.reserach.jdk20;

import java.util.UUID;

public record Repository() {

    public Data getData(UUID id) {
        Data data = findById(id);
        User loggedInUser = Server.LOGGED_IN_USER.get();
        if (loggedInUser.isAdmin()) {
            enrichDataWithAdminInfos(data);
        }
        return data;
    }

    private void enrichDataWithAdminInfos(Data data) {
        data.admin().set(true);
    }

    private Data findById(UUID id) {
        return new Data(id);
    }
}
