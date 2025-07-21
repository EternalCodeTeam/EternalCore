package com.eternalcode.core.loader.repository;

import java.net.MalformedURLException;
import java.nio.file.Path;

public class LocalRepository extends Repository {

    public LocalRepository(Path repositoryFolder) {
        super(assertFolder(repositoryFolder));
    }

    private static String assertFolder(Path repositoryFolder) {
        try {
            return repositoryFolder.toUri().toURL().toString();
        }
        catch (MalformedURLException exception) {
            throw new RuntimeException(exception);
        }
    }

}
