package com.lkm.webserver.api;

import com.lkm.webserver.constant.HTTPStatus;
import com.lkm.webserver.response.Cookie;

public interface HttpResponse {
    void setHeader(String key, String value);

    String getBodyByString();

    byte[] getBodyByByte();

    void setBody(String body);

    void setBody(byte[] body);

    HTTPStatus getStatus();

    void setStatus(HTTPStatus status);

    void startSession(HttpRequest request);

    void setSession(String key, String value);

    void removeSession();

    void setCookies(Cookie cookie);

    void setCookies(String name, String value);

    void writeToBrowser();
}
