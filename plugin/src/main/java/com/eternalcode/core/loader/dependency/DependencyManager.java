package com.eternalcode.core.loader.dependency;

import com.eternalcode.core.loader.dependency.classloader.IsolatedClassLoader;
import com.eternalcode.core.loader.dependency.classpath.ClassPathAppender;
import com.eternalcode.core.loader.dependency.relocation.Relocation;
import com.eternalcode.core.loader.dependency.relocation.RelocationHandler;
import com.google.common.collect.ImmutableSet;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class DependencyManager {

    private static final List<DependencyRepository> REPOSITORIES = DependencyRegistry.getRepositories();

    private final Path cacheDirectory;
    private final ClassPathAppender classPathAppender;
    private final Map<Dependency, Path> loaded = new HashMap<>();
    private final Map<ImmutableSet<Dependency>, IsolatedClassLoader> loaders = new HashMap<>();

    private @MonotonicNonNull RelocationHandler relocationHandler = null;

    public DependencyManager(File dataFolder, ClassPathAppender classPathAppender) {
        this.cacheDirectory = setupCacheDirectory(dataFolder);
        this.classPathAppender = classPathAppender;
    }

    private synchronized RelocationHandler getRelocationHandler() {
        if (this.relocationHandler == null) {
            this.relocationHandler = new RelocationHandler(this);
        }
        return this.relocationHandler;
    }

    public IsolatedClassLoader obtainClassLoaderWith(List<Dependency> dependencies) {
        ImmutableSet<Dependency> set = ImmutableSet.copyOf(dependencies);

        for (Dependency dependency : dependencies) {
            if (!this.loaded.containsKey(dependency)) {
                throw new IllegalStateException("Dependency " + dependency + " is not loaded.");
            }
        }

        synchronized (this.loaders) {
            IsolatedClassLoader classLoader = this.loaders.get(set);
            if (classLoader != null) {
                return classLoader;
            }

            URL[] urls = set.stream()
                    .map(this.loaded::get)
                    .map(file -> {
                        try {
                            return file.toUri().toURL();
                        }
                        catch (MalformedURLException exception) {
                            throw new RuntimeException(exception);
                        }
                    })
                    .toArray(URL[]::new);

            classLoader = new IsolatedClassLoader(urls);
            this.loaders.put(set, classLoader);
            return classLoader;
        }
    }

    public void loadDependencies(List<Dependency> dependencies, List<Relocation> relocations) {
        List<Dependency> allDependencies = new ArrayList<>();

        for (Dependency dependency : dependencies) {
            for (DependencyRepository repository : REPOSITORIES) {
                XmlScanner scanner = new XmlScanner(relocations);
                Optional<List<Dependency>> dependencyList = scanner.findAll(repository, dependency);

                if (dependencyList.isEmpty()) {
                    continue;
                }

                allDependencies.addAll(dependencyList.get());
                break;
            }
        }

        allDependencies.addAll(dependencies);

        for (Dependency dependency : allDependencies) {
            if (this.loaded.containsKey(dependency)) {
                continue;
            }

            try {
                this.loadDependency(dependency);
            }
            catch (DependencyDownloadException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void loadDependency(Dependency dependency) throws DependencyDownloadException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (this.loaded.containsKey(dependency)) {
            return;
        }

        Path downloadedDependency = this.downloadDependency(dependency);
        Path file = this.remapDependency(dependency, downloadedDependency);

        this.loaded.put(dependency, file);
        System.out.println("Loaded dependency: " + dependency);

        if (this.classPathAppender != null) {
            this.classPathAppender.addJarToClasspath(file);
        }
    }

    private Path downloadDependency(Dependency dependency) throws DependencyDownloadException {
        Path file = this.cacheDirectory.resolve(dependency.getFileName(null));

        // if the file already exists, don't attempt to re-download it.
        if (Files.exists(file)) {
            return file;
        }

        DependencyDownloadException lastError = null;

        // attempt to download the dependency from each repo in order.
        for (DependencyRepository repository : REPOSITORIES) {
            try {
                repository.downloadJar(dependency, file);
                return file;
            }
            catch (DependencyDownloadException exception) {
                lastError = exception;
            }
        }

        throw Objects.requireNonNull(lastError);
    }

    private Path remapDependency(Dependency dependency, Path normalFile) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Relocation> rules = new ArrayList<>(dependency.getRelocations());

        if (rules.isEmpty()) {
            return normalFile;
        }

        Path remappedFile = this.cacheDirectory.resolve(dependency.getFileName("remapped"));

        // if the remapped source exists already, just use that.
        if (Files.exists(remappedFile)) {
            return remappedFile;
        }

        if (!dependency.toRelocate()) {
            return normalFile;
        }

        this.getRelocationHandler().remap(normalFile, remappedFile, rules);
        return remappedFile;
    }

    private static Path setupCacheDirectory(File dataFolder) {
        Path cacheDirectory = dataFolder.toPath().resolve("libs");
        try {
            createDirectoriesIfNotExists(cacheDirectory);
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to create libs/ directory", e);
        }

        return cacheDirectory;
    }

    public static void createDirectoriesIfNotExists(Path path) throws IOException {
        if (Files.exists(path) && (Files.isDirectory(path) || Files.isSymbolicLink(path))) {
            return;
        }

        try {
            Files.createDirectories(path);
        }
        catch (FileAlreadyExistsException ignored) {
        }
    }

    public static void deleteDirectory(Path path) throws IOException {
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            return;
        }

        try (DirectoryStream<Path> contents = Files.newDirectoryStream(path)) {
            for (Path file : contents) {
                if (Files.isDirectory(file)) {
                    deleteDirectory(file);
                }
                else {
                    Files.delete(file);
                }
            }
        }

        Files.delete(path);
    }

}