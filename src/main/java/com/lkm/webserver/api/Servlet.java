package com.lkm.webserver.api;

import com.lkm.webserver.request.Request;
import com.lkm.webserver.response.Response;

public interface Servlet {

    void doGet(Request request, Response response);

    void doPost(Request request, Response response);
}
