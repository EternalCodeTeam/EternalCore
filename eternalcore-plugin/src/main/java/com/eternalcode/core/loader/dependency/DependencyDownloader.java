package com.eternalcode.core.loader.dependency;

import com.eternalcode.core.loader.repository.Repository;
import com.google.common.io.ByteStreams;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DependencyDownloader {

    private static final int CONNECT_TIMEOUT_MILLIS = 15_000;
    private static final int READ_TIMEOUT_MILLIS = 30_000;

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
            this.verifyChecksum(repository, dependency, bytes);

            Path parent = file.getParent();

            Files.createDirectories(parent);
            Files.write(file, bytes, StandardOpenOption.CREATE);
        }
        catch (FileNotFoundException | NoSuchFileException fileNotFoundException) {
            throw new DependencyException("Dependency not found for repositoru: " + dependency.toMavenJar(repository).toString());
        }
        catch (IOException e) {
            throw new DependencyException(e);
        }

        return file;
    }

    private byte[] downloadJar(Repository repository, Dependency dependency) throws IOException {
        try (InputStream in = openStream(dependency.toMavenJar(repository).toURL().openConnection())) {
            byte[] bytes = ByteStreams.toByteArray(in);

            if (bytes.length == 0) {
                throw new IOException("Empty stream");
            }

            return bytes;
        }
    }

    /**
     * Verifies the downloaded artifact against the strongest checksum the repository publishes for it. Fails closed:
     * a mismatch, or the total absence of any published checksum, rejects this repository so the caller can fall back
     * to another one (and ultimately fail if none can vouch for the artifact). This prevents a tampered or corrupted
     * jar from being written to the local cache and loaded into the JVM.
     */
    private void verifyChecksum(Repository repository, Dependency dependency, byte[] jarBytes) {
        for (Checksum checksum : Checksum.values()) {
            String publishedChecksum = this.downloadChecksum(repository, dependency, checksum);

            if (publishedChecksum == null) {
                continue;
            }

            if (!checksum.matches(jarBytes, publishedChecksum)) {
                throw new DependencyException(
                    "Checksum mismatch (" + checksum.extension() + ") for " + dependency + " from " + repository
                        + " - refusing to load a potentially tampered dependency");
            }

            return;
        }

        throw new DependencyException(
            "No published checksum (sha512/sha256/sha1) available to verify " + dependency + " from " + repository);
    }

    private String downloadChecksum(Repository repository, Dependency dependency, Checksum checksum) {
        try (InputStream in = openStream(dependency.toMavenJarChecksum(repository, checksum.extension()).toURL().openConnection())) {
            byte[] bytes = ByteStreams.toByteArray(in);

            if (bytes.length == 0) {
                return null;
            }

            return new String(bytes, StandardCharsets.UTF_8);
        }
        catch (IOException exception) {
            // Checksum not served by this repository; the caller treats this as "cannot verify here".
            return null;
        }
    }

    private static InputStream openStream(URLConnection connection) throws IOException {
        connection.setConnectTimeout(CONNECT_TIMEOUT_MILLIS);
        connection.setReadTimeout(READ_TIMEOUT_MILLIS);
        return connection.getInputStream();
    }

}
