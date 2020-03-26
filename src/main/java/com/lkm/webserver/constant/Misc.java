package com.lkm.webserver.constant;

public class Misc {
    public final static int PORT = 8080;
    public final static int BUFFER_SIZE = 1024;
    public final static int EXPIRES_TIME = 5;
    public final static int MAX_CONNECTION_POOL_SIZE = 100;
    // 10s
    public final static int BLOCK_TIMEOUT = 10 * 1000;
    // 1h
    public final static int SESSION_TIMEOUT = 360 * 1000;
    // 20s
    public final static long CLEANER_SLEEP_TIMEOUT = 20 * 1000L;
    public final static String CRLF = "\r\n";
    public final static String WWW_ROOT = "/home/lkm/www";
    public final static String WWW_CLASSES = WWW_ROOT + "/WEB-INF/classes";
    public final static String SERVER_NAME = "WSX";
    public final static String SESSION_NAME = "JSESSIONID";
}
