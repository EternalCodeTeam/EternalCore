package com.eternalcode.core.loader.dependency;

import com.eternalcode.core.loader.classloader.IsolatedClassLoader;
import com.eternalcode.core.loader.classloader.IsolatedClassLoaderImpl;
import com.eternalcode.core.loader.pom.DependencyScanner;
import com.eternalcode.core.loader.pom.PomXmlScanner;
import com.eternalcode.core.loader.relocation.Relocation;
import com.eternalcode.core.loader.relocation.RelocationHandler;
import com.eternalcode.core.loader.repository.Repository;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class DependencyLoaderImpl implements DependencyLoader {

    private static final String LOCAL_REPOSITORY_PATH = "localRepository";

    private final Logger logger;
    private final DependencyDownloader downloadDependency;
    private final RelocationHandler relocationHandler;
    private final DependencyScanner pomXmlScanner;

    private final Map<Dependency, Path> loaded = new HashMap<>();

    public DependencyLoaderImpl(Logger logger, File dataFolder, List<Repository> repositories) {
        Path localRepositoryPath = this.setupCacheDirectory(dataFolder);
        Repository localRepository = Repository.localRepository(localRepositoryPath);

        List<Repository> allRepositories = new ArrayList<>();
        allRepositories.add(localRepository);
        allRepositories.addAll(repositories);

        this.logger = logger;
        this.pomXmlScanner = new PomXmlScanner(allRepositories, localRepository);
        this.downloadDependency = new DependencyDownloader(logger, localRepository, allRepositories);
        this.relocationHandler = RelocationHandler.create(this);
    }

    @Override
    public DependencyLoadResult load(List<Dependency> dependencies, List<Relocation> relocations) {
        return this.load(new IsolatedClassLoaderImpl(), dependencies, relocations);
    }

    @Override
    public DependencyLoadResult load(ClassLoader loader, List<Dependency> dependencies, List<Relocation> relocations, URL... urls) {
        return this.load(new IsolatedClassLoaderImpl(loader, urls), dependencies, relocations);
    }

    @Override
    public DependencyLoadResult load(IsolatedClassLoader loader, List<Dependency> dependencies, List<Relocation> relocations) {
        DependencyCollector collector = new DependencyCollector();

        this.logger.info("Searching for dependencies");
        for (Dependency dependency : dependencies) {
            collector = this.pomXmlScanner.findAllChildren(collector, dependency);
        }

        collector.scannedDependencies(dependencies);
        this.logger.info("Found " + collector.scannedDependencies().size() + " dependencies");

        List<Path> paths = new ArrayList<>();

        for (Dependency dependency : collector.scannedDependencies()) {
            Path loaded = this.loaded.get(dependency);

            if (loaded != null) {
                // TODO: jeśli już wcześniej pobrano zależność to można zweryfikować czy relokacja jest poprawna (może jakiś plik z informacjami o relokacjach?)
                loader.addPath(loaded);
                paths.add(loaded);
                continue;
            }

            Path downloadedDependencyPath = this.downloadDependency.downloadDependency(dependency);

            if (this.relocationHandler == null) {
                this.loaded.put(dependency, downloadedDependencyPath);
                loader.addPath(downloadedDependencyPath);
                paths.add(downloadedDependencyPath);
                continue;
            }

            // TODO: to jest trochę blocking można to zrobić w parallel streamie
            Path relocatedDependency = this.relocationHandler.relocateDependency(downloadedDependencyPath, dependency, relocations);

            this.loaded.put(dependency, relocatedDependency);
            loader.addPath(relocatedDependency);
            paths.add(relocatedDependency);
        }

        return new DependencyLoadResult(loader, collector.scannedDependencies(), paths);
    }

    @Override
    public void close() {
        try {
            this.relocationHandler.close();
        }
        catch (Exception exception) {
            throw new DependencyException("Failed to close relocation handler", exception);
        }
    }

    private Path setupCacheDirectory(File dataFolder) {
        Path cacheDirectory = dataFolder.toPath().resolve(LOCAL_REPOSITORY_PATH);
        try {
            Files.createDirectories(cacheDirectory);
        }
        catch (FileAlreadyExistsException ignored) {
        }
        catch (IOException ioException) {
            throw new DependencyException("Unable to create " + LOCAL_REPOSITORY_PATH + " directory", ioException);
        }

        return cacheDirectory;
    }

}
