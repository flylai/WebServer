package com.lkm.webserver.connection;

import com.lkm.webserver.request.RequestHeaders;
import com.lkm.webserver.request.RequestLine;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class Connection {
    private long lastConnectTime;
    private SocketChannel socketChannel;

    private int length;
    private ArrayList<byte[]> httpMessage;

    private RequestLine requestLine;
    private RequestHeaders requestHeaders;

    public Connection(SocketChannel socketChannel, ArrayList<byte[]> httpMessage) {
        this.lastConnectTime = System.currentTimeMillis();
        this.socketChannel = socketChannel;
        this.httpMessage = httpMessage;
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

    public int getLength() {
        return length;
    }

    public void addLength(int length) {
        this.length += length;
    }

    public void resetLength() {
        length = 0;
    }

    public ArrayList<byte[]> getHttpMessage() {
        return httpMessage;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public void setRequestLine(RequestLine requestLine) {
        this.requestLine = requestLine;
    }

    public RequestHeaders getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(RequestHeaders requestHeaders) {
        this.requestHeaders = requestHeaders;
    }
}
