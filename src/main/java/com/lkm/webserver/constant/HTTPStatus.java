package com.lkm.webserver.constant;

public enum HTTPStatus {
    /*
     * See also https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
     */
    OK(200, "OK"),
    Moved_Permanently(302, "Moved Permanently"),
    Bad_Request(400, "Bad Request"),
    Forbidden(403, "Forbidden"),
    Not_Found(404, "Not Found");

    HTTPStatus(int code, String message) {
    }
}