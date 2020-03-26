package com.lkm.webserver.connection;

import java.util.HashMap;

public class Session {
    private HashMap<String, String> session;
    private long lastUpdate;

    public Session(HashMap<String, String> session) {
        this.session = session;
        updateLastUpdate();
    }

    public Session() {
        this(new HashMap<>());
    }

    public void setAttribute(String key, String value) {
        session.put(key, value);
        updateLastUpdate();
    }

    public String getAttribute(String key) {
        updateLastUpdate();
        return session.get(key);
    }

    public void removeAttribute(String key) {
        updateLastUpdate();
        session.remove(key);
    }

    private void updateLastUpdate() {
        lastUpdate = System.currentTimeMillis();
    }

    public long getLastUpdate() {
        return lastUpdate;
    }
}
