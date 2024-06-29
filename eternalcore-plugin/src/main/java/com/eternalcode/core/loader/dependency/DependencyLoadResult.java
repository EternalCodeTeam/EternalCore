package com.eternalcode.core.loader.dependency;

import com.eternalcode.core.loader.classloader.IsolatedClassLoader;

import java.util.List;

public record DependencyLoadResult(IsolatedClassLoader loader, List<DependencyLoadEntry> paths) {
}
