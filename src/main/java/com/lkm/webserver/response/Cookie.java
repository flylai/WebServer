package com.lkm.webserver.response;

public class Cookie {
    private String key;
    private String value;
    private String expires;
    private String path;
    private String domain;
    private int maxAge;

    public static class Builder {
        private final String key;
        private final String value;
        private String expires = "";
        private String path = "";
        private String domain = "";
        private int maxAge = -2;

        public Builder(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public Builder expires(String expires) {
            this.expires = expires;
            return this;
        }

        public Builder domain(String domain) {
            this.domain = domain;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Cookie build() {
            return new Cookie(this);
        }

        public Builder maxAge(int maxAge) {
            this.maxAge = maxAge;
            return this;
        }
    }


    private Cookie(Builder builder) {
        this.key = builder.key;
        this.value = builder.value;
        this.expires = builder.expires;
        this.path = builder.path;
        this.domain = builder.domain;
        this.maxAge = builder.maxAge;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getExpires() {
        return expires;
    }

    public String getPath() {
        return path;
    }

    public String getDomain() {
        return domain;
    }

    public int getMaxAge() {
        return maxAge;
    }
}
