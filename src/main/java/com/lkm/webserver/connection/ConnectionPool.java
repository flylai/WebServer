package com.lkm.webserver.connection;

import java.util.concurrent.ConcurrentHashMap;

public class ConnectionPool {
    private final static ConcurrentHashMap<Integer, Connection> connectionPool = new ConcurrentHashMap<>();
    private final static ConcurrentHashMap<String, Session> session = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<Integer, Connection> getConnectionPool() {
        return connectionPool;
    }

    public static void addSession(String sessionId) {
        session.put(sessionId, new Session());
    }

    public static void deleteSession(String sessionId) {
        session.remove(sessionId);
    }

    public static String getAttribute(String sessionId, String key) {
        return session.get(sessionId).getAttribute(key);
    }

    public static void setAttribute(String sessionId, String key, String value) {
        session.get(sessionId).setAttribute(key, value);
    }

}
