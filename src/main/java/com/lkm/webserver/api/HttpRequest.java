package com.lkm.webserver.api;

import com.lkm.webserver.constant.RequestMethod;

import java.util.HashMap;


public interface HttpRequest {

    HashMap<String, String> getArgv();

    HashMap<String, String> getHeaders();

    String getHeader(String key);

    RequestMethod getMethod();

    String getQueryString();

    String getRequestUri();

    HashMap<String, String> getCookies();

    String getCookie(String key);


}
