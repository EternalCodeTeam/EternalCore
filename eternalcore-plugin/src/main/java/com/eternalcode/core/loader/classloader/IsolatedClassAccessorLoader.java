package com.eternalcode.core.loader.classloader;

import java.io.IOException;
import java.net.URLClassLoader;
import java.nio.file.Path;

public class IsolatedClassAccessorLoader implements IsolatedClassLoader {

    private final URLClassLoaderAccessor accessor;
    private final URLClassLoader classLoader;

    public IsolatedClassAccessorLoader(URLClassLoader classLoader) {
        this.accessor = URLClassLoaderAccessor.create(classLoader);
        this.classLoader = classLoader;
    }

    @Override
    public void addPath(Path path) {
        this.accessor.addJarToClasspath(path);
    }

    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        return this.classLoader.loadClass(className);
    }

    @Override
    public void close() throws IOException {
        this.classLoader.close();
    }

}
