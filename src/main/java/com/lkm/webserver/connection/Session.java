package com.lkm.webserver.connection;

import java.util.HashMap;

public class Session {
    private HashMap<String, String> session;

    public Session(HashMap<String, String> session) {
        this.session = session;
    }

    public Session() {
        this(new HashMap<>());
    }

    public void setAttribute(String key, String value) {
        session.put(key, value);
    }

    public String getAttribute(String key) {
        return session.get(key);
    }

    public void removeAttribute(String key) {
        session.remove(key);
    }
}
