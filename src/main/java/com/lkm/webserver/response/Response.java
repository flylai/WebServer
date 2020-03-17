package com.lkm.webserver.response;

import com.lkm.webserver.api.HttpResponse;
import com.lkm.webserver.constant.HTTPStatus;
import com.lkm.webserver.constant.Misc;

import java.util.HashMap;

public class Response implements HttpResponse {
    private HashMap<String, String> headers;
    private String body;
    private HTTPStatus status;

    public Response() {
        headers = new HashMap<>();
    }

    public Response(HashMap<String, String> headers) {
        this.headers = headers;
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
    public String getBody() {
        return body;
    }

    @Override
    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public HTTPStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(HTTPStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final String[] message = {status.toString() + Misc.CRLF};
        headers.forEach((k, v) -> message[0] += k + ":" + v + Misc.CRLF);
        message[0] += "" +
                "content-length:" + body.getBytes().length +
                Misc.CRLF + Misc.CRLF +
                body + "\r\n0\r\n\r\n";
        return message[0];
    }
}
