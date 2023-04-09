package com.eternalcode.core.loader.dependency;

import com.eternalcode.core.loader.repository.Repository;
import com.google.common.io.ByteStreams;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DependencyDownloader {

    private final Path cacheDirectory;
    private final List<Repository> repositories;

    public DependencyDownloader(File dataFolder, List<Repository> repositories) {
        this.repositories = repositories;
        this.cacheDirectory = this.setupCacheDirectory(dataFolder);
    }

    public Path downloadDependency(Dependency dependency) {
        Path file = this.cacheDirectory.resolve(dependency.toJarFileName());

        if (Files.exists(file)) {
            return file;
        }

        List<DependencyException> exceptions = new ArrayList<>();

        for (Repository repository : this.repositories) {
            try {
                return this.downloadJarAndSave(repository, dependency, file);
            }
            catch (DependencyException exception) {
                exceptions.add(exception);
            }
        }

        DependencyException exception = new DependencyException("Failed to download dependency " + dependency);

        for (DependencyException dependencyException : exceptions) {
            exception.addSuppressed(dependencyException);
        }

        throw exception;
    }

    private Path downloadJarAndSave(Repository repository, Dependency dependency, Path file) {
        try {
            byte[] bytes = this.downloadJar(repository, dependency);

            Files.write(file, bytes);
        }
        catch (IOException e) {
            throw new DependencyException(e);
        }

        return file;
    }

    private byte[] downloadJar(Repository repository, Dependency dependency) throws IOException {
        URLConnection connection = new URL(dependency.toMavenJarPath(repository)).openConnection();

        try (InputStream in = connection.getInputStream()) {
            byte[] bytes = ByteStreams.toByteArray(in);

            if (bytes.length == 0) {
                throw new IOException("Empty stream");
            }

            return bytes;
        }
    }

    private Path setupCacheDirectory(File dataFolder) {
        Path cacheDirectory = dataFolder.toPath().resolve("libs");
        try {
            Files.createDirectories(cacheDirectory);
        }
        catch (FileAlreadyExistsException ignored) {
        }
        catch (IOException ioException) {
            throw new DependencyException("Unable to create libs/ directory", ioException);
        }

        return cacheDirectory;
    }

}
