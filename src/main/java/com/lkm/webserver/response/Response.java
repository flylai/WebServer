package com.lkm.webserver.response;

import com.lkm.webserver.api.HttpResponse;
import com.lkm.webserver.constant.HTTPStatus;
import com.lkm.webserver.constant.Misc;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;

public class Response implements HttpResponse {
    private HashMap<String, String> headers;
    private HashMap<String, Cookie> cookies;
    private byte[] body;
    private HTTPStatus status;
    private SocketChannel socketChannel;

    public Response(SocketChannel socketChannel) {
        this(new HashMap<>(), socketChannel);
    }

    public Response(HashMap<String, String> headers, SocketChannel socketChannel) {
        this.headers = headers;
        this.socketChannel = socketChannel;
        cookies = new HashMap<>();
        put("server", Misc.SERVER_NAME);
    }

    @Override
    public void put(String key, String value) {
        key = key.toLowerCase();
        headers.put(key, value);
    }

    @Override
    public String get(String key) {
        String value = headers.get(key);
        return value == null ? "" : value;
    }

    @Override
    public String getBodyByString() {
        return new String(body);
    }

    @Override
    public byte[] getBodyByByte() {
        return body;
    }

    @Override
    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public void setBody(String body) {
        this.body = body.getBytes();
    }

    @Override
    public HTTPStatus getStatus() {
        return status;
    }

    public void setCookies(String name, String value, String expires, String path, String domain, int maxAge) {
        cookies.put(name, new Cookie(value, expires, path, domain, maxAge));
    }

    public void setCookies(String name, String value) {
        setCookies(name, value, "", "", "", -2);
    }

    @Override
    public void setStatus(HTTPStatus status) {
        this.status = status;
    }

    public void writeToBrowser() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(status.toString());
        stringBuilder.append(Misc.CRLF);
        headers.forEach((k, v) -> {
            stringBuilder.append(k);
            stringBuilder.append(": ");
            stringBuilder.append(v);
            stringBuilder.append(Misc.CRLF);
        });
        cookies.forEach((k, v) -> {
            stringBuilder.append("set-cookie: ");
            stringBuilder.append(k).append("=").append(v.getValue()).append(";");
            if (!v.getDomain().isEmpty()) {
                stringBuilder.append("domain=").append(v.getDomain()).append(";");
            }
            if (!v.getExpires().isEmpty()) {
                stringBuilder.append("expires=").append(v.getExpires()).append(";");
            }
            if (v.getMaxAge() != -2) {
                stringBuilder.append("max-age=").append(v.getMaxAge()).append(";");
            }
            if (!v.getPath().isEmpty()) {
                stringBuilder.append("path=").append(v.getPath()).append(";");
            }
            stringBuilder.append(Misc.CRLF);
        });
        stringBuilder.append("content-length: ");
        stringBuilder.append(body.length);
        stringBuilder.append(Misc.CRLF);
        stringBuilder.append(Misc.CRLF);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(String.valueOf(stringBuilder).getBytes());
            byteArrayOutputStream.write(body);
            socketChannel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
