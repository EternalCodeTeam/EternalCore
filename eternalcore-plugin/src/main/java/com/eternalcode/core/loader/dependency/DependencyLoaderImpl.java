package com.eternalcode.core.loader.dependency;

import com.eternalcode.core.loader.classloader.IsolatedClassLoader;
import com.eternalcode.core.loader.classloader.IsolatedClassLoaderImpl;
import com.eternalcode.core.loader.pom.DependencyScanner;
import com.eternalcode.core.loader.pom.PomXmlScanner;
import com.eternalcode.core.loader.relocation.Relocation;
import com.eternalcode.core.loader.relocation.RelocationCacheResolver;
import com.eternalcode.core.loader.relocation.RelocationHandler;
import com.eternalcode.core.loader.repository.LocalRepository;
import com.eternalcode.core.loader.repository.Repository;

import com.spotify.futures.CompletableFutures;
import java.io.File;
import java.io.IOException;
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
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class DependencyLoaderImpl implements DependencyLoader {

    private static final String LOCAL_REPOSITORY_PATH = "localRepository";

    private final Logger logger;
    private final Executor executor;

    private final DependencyDownloader dependencyDownloader;
    private final RelocationHandler relocationHandler;
    private final DependencyScanner dependencyScanner;
    private final LocalRepository localRepository;

    private final Map<Dependency, Path> loaded = new HashMap<>();

    public DependencyLoaderImpl(Logger logger, File dataFolder, List<Repository> repositories) {
        this.logger = logger;
        Path localRepositoryPath = this.setupCacheDirectory(dataFolder);
        this.localRepository = new LocalRepository(localRepositoryPath);

        List<Repository> allRepositories = new ArrayList<>();
        allRepositories.add(localRepository);
        allRepositories.addAll(repositories);

        this.executor = Executors.newFixedThreadPool(allRepositories.size() * 5);
        this.dependencyScanner = new PomXmlScanner(allRepositories, localRepository);
        this.dependencyDownloader = new DependencyDownloader(logger, localRepository, allRepositories);

        this.relocationHandler = RelocationHandler.create(this, new RelocationCacheResolver(this.localRepository));
    }

    @Override
    public DependencyLoadResult load(List<Dependency> dependencies, List<Relocation> relocations) {
        return this.load(new IsolatedClassLoaderImpl(), dependencies, relocations);
    }

    @Override
    public DependencyLoadResult load(IsolatedClassLoader loader, List<Dependency> dependencies, List<Relocation> relocations) {
        DependencyCollector collector = new DependencyCollector();

        this.logger.info("Resolving dependencies...");
        for (Dependency dependency : dependencies) {
            collector = this.dependencyScanner.findAllChildren(collector, dependency);
        }

        collector.addScannedDependencies(dependencies);
        this.logger.info("Found " + collector.getScannedDependencies().size() + " dependencies");

        List<CompletableFuture<DependencyLoadEntry>> futures = new ArrayList<>();
        AtomicLong totalSize = new AtomicLong(0);

        for (Dependency dependency : collector.getScannedDependencies()) {
            CompletableFuture<DependencyLoadEntry> pathToDependency = CompletableFuture.supplyAsync(() -> {
                Path loaded = this.loaded.get(dependency);

                if (loaded != null) {
                    return new DependencyLoadEntry(dependency, loaded);
                }

                Path downloadedDependencyPath = this.dependencyDownloader.downloadDependency(dependency);
                if (this.relocationHandler == null) {
                    return new DependencyLoadEntry(dependency, downloadedDependencyPath);
                }

                long nanos = System.nanoTime();
                Path relocatedDependency = this.relocationHandler.relocateDependency(localRepository, downloadedDependencyPath, dependency, relocations);
                long endNanos = System.nanoTime();
                long durationNanos = endNanos - nanos;
                totalSize.addAndGet(durationNanos);

                return new DependencyLoadEntry(dependency, relocatedDependency);
            }, this.executor);

            futures.add(pathToDependency);
        }

        List<DependencyLoadEntry> dependencyLoadEntries = CompletableFutures.allAsList(futures)
            .orTimeout(60, TimeUnit.MINUTES)
            .join();

        this.logger.info("Finished loading dependencies in " + TimeUnit.NANOSECONDS.toMillis(totalSize.get()) + " ms");

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
