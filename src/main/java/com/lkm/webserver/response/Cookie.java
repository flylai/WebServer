package com.lkm.webserver.response;

public class Cookie {
    private String value;
    private String expires;
    private String path;
    private String domain;
    private int maxAge;

    public Cookie(String value, String expires, String path, String domain, int maxAge) {
        this.value = value;
        this.expires = expires;
        this.path = path;
        this.domain = domain;
        this.maxAge = maxAge;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }
}
