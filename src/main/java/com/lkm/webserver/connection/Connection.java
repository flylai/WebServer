package com.lkm.webserver.connection;

import com.lkm.webserver.request.Request;

import java.io.ByteArrayOutputStream;
import java.nio.channels.SocketChannel;

public class Connection {
    private long lastConnectTime;
    private SocketChannel socketChannel;

    private ByteArrayOutputStream httpMessage;

    private Request request;
    private boolean running;

    public Connection(SocketChannel socketChannel) {
        this.lastConnectTime = System.currentTimeMillis();
        this.socketChannel = socketChannel;
        this.httpMessage = new ByteArrayOutputStream();
    }

    public long getLastConnectTime() {
        return lastConnectTime;
    }

    public void updateLastConnectTime() {
        this.lastConnectTime = System.currentTimeMillis();
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public ByteArrayOutputStream getHttpMessage() {
        return httpMessage;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
