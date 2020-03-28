package com.lkm.webserver.request;

import java.util.HashMap;

public class RequestBody {
    private HashMap<String, String> postData;
    private HashMap<String, byte[]> file;

    public RequestBody() {
        this(new HashMap<>(), new HashMap<>());
    }

    public RequestBody(HashMap<String, String> postData, HashMap<String, byte[]> file) {
        this.postData = postData;
        this.file = file;
    }

    public void setPostData(HashMap<String, String> postData) {
        this.postData = postData;
    }

    public HashMap<String, String> getPostData() {
        return postData;
    }

    public String getPostData(String key) {
        return postData.get(key);
    }

    public HashMap<String, byte[]> getFile() {
        return file;
    }

    public byte[] getFile(String key) {
        return file.get(key);
    }

}
