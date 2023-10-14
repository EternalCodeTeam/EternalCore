package com.eternalcode.core.loader.repository;

import java.net.MalformedURLException;
import java.nio.file.Path;

public class Repository {

    private final String url;

    private Repository(String url) {
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

    public static Repository localRepository(Path repositoryFolder) {
        try {
            return Repository.of(repositoryFolder.toUri().toURL().toString());
        }
        catch (MalformedURLException exception) {
            throw new RuntimeException(exception);
        }
    }

}
