package com.lkm.webserver.api;

public interface Servlet {

    void doGet(HttpRequest request, HttpResponse response);

    void doPost(HttpRequest request, HttpResponse response);
}
