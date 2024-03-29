package com.eternalcode.core.loader.dependency;

import com.eternalcode.core.loader.classloader.IsolatedClassLoader;
import com.eternalcode.core.loader.relocation.Relocation;

import java.net.URL;
import java.util.List;

public interface DependencyLoader extends AutoCloseable {

    DependencyLoadResult load(List<Dependency> dependencies, List<Relocation> relocations);

    DependencyLoadResult load(ClassLoader pattern, List<Dependency> dependencies, List<Relocation> relocations, URL... urls);

    DependencyLoadResult load(IsolatedClassLoader loader, List<Dependency> dependencies, List<Relocation> relocations);

    @Override
    void close();

}
