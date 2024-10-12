package com.eternalcode.core.loader.dependency;

import com.eternalcode.core.loader.classloader.IsolatedClassLoader;
import com.eternalcode.core.loader.classloader.IsolatedClassLoaderImpl;
import com.eternalcode.core.loader.pom.DependencyScanner;
import com.eternalcode.core.loader.pom.PomXmlScanner;
import com.eternalcode.core.loader.relocation.Relocation;
import com.eternalcode.core.loader.relocation.RelocationHandler;
import com.eternalcode.core.loader.repository.Repository;

import com.spotify.futures.CompletableFutures;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class DependencyLoaderImpl implements DependencyLoader {

    private static final String LOCAL_REPOSITORY_PATH = "localRepository";

    private final Executor executor;
    private final Logger logger;
    private final DependencyDownloader downloadDependency;
    private final RelocationHandler relocationHandler;
    private final DependencyScanner pomXmlScanner;
    private final Repository localRepository;

    private final Map<Dependency, Path> loaded = new HashMap<>();

    public DependencyLoaderImpl(Logger logger, File dataFolder, List<Repository> repositories) {
        Path localRepositoryPath = this.setupCacheDirectory(dataFolder);
        this.localRepository = Repository.localRepository(localRepositoryPath);

        List<Repository> allRepositories = new ArrayList<>();
        allRepositories.add(localRepository);
        allRepositories.addAll(repositories);

        this.executor = Executors.newCachedThreadPool();
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

        collector.addScannedDependencies(dependencies);
        this.logger.info("Found " + collector.getScannedDependencies().size() + " dependencies");

        List<CompletableFuture<DependencyLoadEntry>> futures = new ArrayList<>();

        for (Dependency dependency : collector.getScannedDependencies()) {
            CompletableFuture<DependencyLoadEntry> pathToDependency = CompletableFuture.supplyAsync(() -> {
                Path loaded = this.loaded.get(dependency);

                if (loaded != null) {
                    return new DependencyLoadEntry(dependency, loaded);
                }

                Path downloadedDependencyPath = this.downloadDependency.downloadDependency(dependency);

                if (this.relocationHandler == null) {
                    return new DependencyLoadEntry(dependency, downloadedDependencyPath);
                }

                Path relocatedDependency = this.relocationHandler.relocateDependency(localRepository, downloadedDependencyPath, dependency, relocations);

                return new DependencyLoadEntry(dependency, relocatedDependency);
            }, this.executor);

            futures.add(pathToDependency);
        }

        List<DependencyLoadEntry> dependencyLoadEntries = CompletableFutures.allAsList(futures)
            .orTimeout(60, TimeUnit.MINUTES)
            .join();

        for (DependencyLoadEntry dependencyLoadEntry : dependencyLoadEntries) {
            loader.addPath(dependencyLoadEntry.path());
            this.loaded.put(dependencyLoadEntry.dependency(), dependencyLoadEntry.path());
        }

        return new DependencyLoadResult(loader, dependencyLoadEntries);
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
