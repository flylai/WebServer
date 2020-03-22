package com.lkm.webserver.request;

import java.util.HashMap;

public class RequestBody {
    private HashMap<String, String> postData;
    private HashMap<String, String> file;

    public RequestBody() {
        this(new HashMap<>(), new HashMap<>());
    }

    public RequestBody(HashMap<String, String> postData, HashMap<String, String> file) {
        this.postData = postData;
        this.file = file;
    }

    public void setPostData(HashMap<String, String> postData) {
        this.postData = postData;
    }

    public HashMap<String, String> getPostData() {
        return postData;
    }

    public HashMap<String, String> getFile() {
        return file;
    }

}
