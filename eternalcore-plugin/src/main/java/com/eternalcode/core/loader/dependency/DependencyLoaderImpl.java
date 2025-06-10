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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
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
        this.localRepository = new LocalRepository(setupCacheDirectory(dataFolder));

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
        DependencyCollector resolved = resolveDependencies(dependencies);
        List<DependencyLoadEntry> downloaded = downloadDependencies(relocations, resolved);
        return loadDependencies(loader, downloaded);
    }

    private DependencyCollector resolveDependencies(List<Dependency> dependencies) {
        DependencyCollector collector = new DependencyCollector();
        this.logger.info("Resolving dependencies...");

        runParallel(dependencies, dependency -> this.dependencyScanner.findAllChildren(collector, dependency));
        collector.addScannedDependencies(dependencies);

        this.logger.info("Resolved " + collector.getScannedDependencies().size() + " dependencies");
        return collector;
    }

    private List<DependencyLoadEntry> downloadDependencies(List<Relocation> relocations, DependencyCollector collector) {
        return runParallel(collector.getScannedDependencies(), dependency -> {
            Path loaded = this.loaded.get(dependency);

            if (loaded != null) {
                return new DependencyLoadEntry(dependency, loaded);
            }

            Path downloadedDependencyPath = this.dependencyDownloader.downloadDependency(dependency);
            if (this.relocationHandler == null) {
                return new DependencyLoadEntry(dependency, downloadedDependencyPath);
            }

            Path relocatedDependency = this.relocationHandler.relocateDependency(localRepository, downloadedDependencyPath, dependency, relocations);

            return new DependencyLoadEntry(dependency, relocatedDependency);
        });
    }

    private DependencyLoadResult loadDependencies(IsolatedClassLoader loader, List<DependencyLoadEntry> downloaded) {
        for (DependencyLoadEntry dependencyLoadEntry : downloaded) {
            loader.addPath(dependencyLoadEntry.path());
            this.loaded.put(dependencyLoadEntry.dependency(), dependencyLoadEntry.path());
        }

        return new DependencyLoadResult(loader, downloaded);
    }

    private <E, R> List<R> runParallel(Collection<E> list, Function<E, R> function) {
        List<CompletableFuture<R>> futures = list.stream()
            .map(dependency -> CompletableFuture.supplyAsync(() -> function.apply(dependency), this.executor))
            .toList();

        return CompletableFutures.allAsList(futures)
            .orTimeout(60, TimeUnit.MINUTES)
            .join();
    }

    @Override
    public void close() {
        try {
            this.relocationHandler.close();
        } catch (Exception exception) {
            throw new DependencyException("Failed to close relocation handler", exception);
        }
    }

    private static Path setupCacheDirectory(File dataFolder) {
        Path cacheDirectory = dataFolder.toPath().resolve(LOCAL_REPOSITORY_PATH);
        try {
            Files.createDirectories(cacheDirectory);
        } catch (FileAlreadyExistsException ignored) {
        } catch (IOException ioException) {
            throw new DependencyException("Unable to create " + LOCAL_REPOSITORY_PATH + " directory", ioException);
        }

        return cacheDirectory;
    }

}
