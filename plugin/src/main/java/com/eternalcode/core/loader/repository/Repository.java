package com.eternalcode.core.loader.repository;

public class Repository {

    private final String url;

    private Repository(String url) {
        this.url = url;
    }

    public String url() {
        return url;
    }

    public String urlSlash() {
        return url + "/";
    }

    public static Repository of(String url) {
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }

        return new Repository(url);
    }

}