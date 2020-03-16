package com.lkm.webserver.connection;

import java.util.concurrent.ConcurrentHashMap;

public class ConnectionPool {
    private final static ConcurrentHashMap<Integer, Connection> connectionPool = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<Integer, Connection> getConnectionPool() {
        return connectionPool;
    }

}
