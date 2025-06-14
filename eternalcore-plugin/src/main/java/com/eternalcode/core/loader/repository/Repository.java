package com.eternalcode.core.loader.repository;

public class Repository {

    private final String url;

    protected Repository(String url) {
        this.url = url;
    }

    public String url() {
        return this.url;
    }

    public static Repository of(String url) {
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }

        return new Repository(url);
    }

    @Override
    public String toString() {
        return this.url;
    }

}
