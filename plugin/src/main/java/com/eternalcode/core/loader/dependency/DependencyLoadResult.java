package com.eternalcode.core.loader.dependency;

import com.eternalcode.core.loader.classloader.IsolatedClassLoader;

import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.List;

public record DependencyLoadResult(IsolatedClassLoader loader, LinkedHashSet<Dependency> dependencies, List<Path> paths) {
}
