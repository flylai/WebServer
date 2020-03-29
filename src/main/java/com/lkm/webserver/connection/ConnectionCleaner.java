package com.lkm.webserver.connection;

import com.lkm.webserver.constant.Misc;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionCleaner {
    public static void clearSocket() throws InterruptedException {
        ConcurrentHashMap<Integer, Connection> connectionPool = ConnectionPool.getConnectionPool();
        while (true) {
            long curTime = System.currentTimeMillis();
            AtomicInteger cnt = new AtomicInteger();
            connectionPool.forEach((k, v) -> {
                if (curTime - v.getLastConnectTime() > Misc.EXPIRES_TIME) {
                    if (v.getSocketChannel() != null) {
                        try {
                            v.getSocketChannel().close();
                            cnt.getAndIncrement();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        connectionPool.remove(k);
                    }
                }
            });
            if (cnt.get() > 0) {
                System.out.println("close " + cnt + " socket(s)");
            }
            Thread.sleep(Misc.CLEANER_SLEEP_TIMEOUT);
        }
    }

    public static void clearSession() throws InterruptedException {
        ConcurrentHashMap<String, Session> session = ConnectionPool.getSession();
        AtomicInteger cnt = new AtomicInteger();
        while (true) {
            long curTime = System.currentTimeMillis();
            session.forEach((k, v) -> {
                if (curTime - v.getLastUpdate() > Misc.SESSION_TIMEOUT) {
                    session.remove(k);
                    cnt.incrementAndGet();
                }
            });
            if (cnt.get() > 0) {
                System.out.println("remove " + cnt + " session(s)");
            }
            Thread.sleep(Misc.CLEANER_SLEEP_TIMEOUT);
        }
    }
}
