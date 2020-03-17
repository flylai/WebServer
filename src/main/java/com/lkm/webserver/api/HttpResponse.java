package com.lkm.webserver.api;

import com.lkm.webserver.constant.HTTPStatus;

public interface HttpResponse {
    void put(String key, String value);

    String get(String key);

    String getBody();

    void setBody(String body);

    HTTPStatus getStatus();

    void setStatus(HTTPStatus status);
}
