package com.eternalcode.core.loader.dependency;

import com.eternalcode.core.loader.repository.Repository;
import com.google.common.io.ByteStreams;
import io.sentry.Sentry;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DependencyDownloader {

    private final Logger logger;
    private final Repository localRepository;
    private final List<Repository> repositories;

    public DependencyDownloader(Logger logger, Repository localRepository, List<Repository> repositories) {
        this.logger = logger;
        this.repositories = repositories;
        this.localRepository = localRepository;
    }


    public Path downloadDependency(Dependency dependency) {
        try {
            return this.tryDownloadDependency(dependency);
        }
        catch (URISyntaxException exception) {
            Sentry.captureException(exception);
            throw new DependencyException(exception);
        }
    }

    private Path tryDownloadDependency(Dependency dependency) throws URISyntaxException {
        Path localPath = dependency.toMavenJar(this.localRepository).toPath();

        if (Files.exists(localPath)) {
            return localPath;
        }

        List<DependencyException> exceptions = new ArrayList<>();

        for (Repository repository : this.repositories) {
            try {
                Path path = this.downloadJarAndSave(repository, dependency, localPath);
                this.logger.info("Downloaded " + dependency + " from " + repository);
                return path;
            }
            catch (DependencyException exception) {
                Sentry.captureException(exception);
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
            Path parent = file.getParent();

            Files.createDirectories(parent);
            Files.write(file, bytes, StandardOpenOption.CREATE);
        }
        catch (FileNotFoundException | NoSuchFileException fileNotFoundException) {
            Sentry.captureException(fileNotFoundException);
            throw new DependencyException("Dependency not found for repositoru: " + dependency.toMavenJar(repository).toString());
        }
        catch (IOException e) {
            Sentry.captureException(e);
            throw new DependencyException(e);
        }

        return file;
    }

    private byte[] downloadJar(Repository repository, Dependency dependency) throws IOException {
        URLConnection connection = dependency.toMavenJar(repository).toURL().openConnection();

        try (InputStream in = connection.getInputStream()) {
            byte[] bytes = ByteStreams.toByteArray(in);

            if (bytes.length == 0) {
                throw new IOException("Empty stream");
            }

            return bytes;
        }
    }

}
