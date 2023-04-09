package com.eternalcode.core.loader.dependency;

import com.eternalcode.core.loader.classloader.IsolatedClassLoader;
import com.eternalcode.core.loader.pom.PomXmlScanner;
import com.eternalcode.core.loader.relocation.Relocation;
import com.eternalcode.core.loader.relocation.RelocationHandler;
import com.eternalcode.core.loader.repository.Repository;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DependencyLoaderImpl implements DependencyLoader {

    private final DependencyDownloader downloadDependency;
    private final RelocationHandler relocationHandler;

    private final List<Repository> repositories = new ArrayList<>();
    private final Map<Dependency, Path> loaded = new HashMap<>();

    public DependencyLoaderImpl(File dataFolder, List<Repository> repositories) {
        this.repositories.addAll(repositories);
        this.downloadDependency = new DependencyDownloader(dataFolder, repositories);
        this.relocationHandler = RelocationHandler.create(this);
    }

    @Override
    public DependencyLoadResult load(List<Dependency> dependencies, List<Relocation> relocations) {
        return this.load(new IsolatedClassLoader(), dependencies, relocations);
    }

    @Override
    public DependencyLoadResult load(ClassLoader loader, List<Dependency> dependencies, List<Relocation> relocations, URL... urls) {
        return this.load(new IsolatedClassLoader(loader, urls), dependencies, relocations);
    }

    private DependencyLoadResult load(IsolatedClassLoader loader, List<Dependency> dependencies, List<Relocation> relocations) {
        PomXmlScanner scanner = new PomXmlScanner(this.repositories);
        DependencyCollector collector = new DependencyCollector();

        for (Dependency dependency : dependencies) {
            collector = scanner.findAllChildren(collector, dependency);
        }

        collector.scannedDependencies(dependencies);

        List<Path> paths = new ArrayList<>();

        for (Dependency dependency : collector.scannedDependencies()) {
            Path loaded = this.loaded.get(dependency);

            if (loaded != null) {
                loader.addPath(loaded);
                paths.add(loaded);
                continue;
            }

            Path downloadedDependency = this.downloadDependency.downloadDependency(dependency);

            if (this.relocationHandler == null) {
                this.loaded.put(dependency, downloadedDependency);
                loader.addPath(downloadedDependency);
                paths.add(downloadedDependency);
                continue;
            }

            Path relocatedDependency = this.relocationHandler.relocateDependency(downloadedDependency, dependency, relocations);

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

}