package com.lkm.webserver.connection;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionPool {
    private final static ConcurrentHashMap<Integer, Connection> connectionPool = new ConcurrentHashMap<>();
    private final static ConcurrentHashMap<String, Session> session = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<Integer, Connection> getConnectionPool() {
        return connectionPool;
    }

    public static boolean isSessionValid(String sessionId) {
        return session.containsKey(sessionId);
    }

    public static void addSession(String sessionId) {
        session.put(sessionId, new Session());
    }

    public static void deleteSession(String sessionId) {
        session.remove(sessionId);
    }

    public static String getAttribute(String sessionId, String key) {
        Session tmp = session.get(sessionId);
        if (tmp != null) {
            return session.get(sessionId).getAttribute(key);
        }
        return "";
    }

    public static void setAttribute(String sessionId, String key, String value) {
        session.get(sessionId).setAttribute(key, value);
    }

    public static void removeSocket(SocketChannel socketChannel) {
        if (connectionPool.containsKey(socketChannel.hashCode())) {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        connectionPool.remove(socketChannel.hashCode());
    }
}

