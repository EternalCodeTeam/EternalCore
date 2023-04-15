package com.eternalcode.core.loader.classloader;

import java.io.IOException;
import java.nio.file.Path;

public interface IsolatedClassLoader {

    void addPath(Path path);

    Class<?> loadClass(String className) throws ClassNotFoundException;

    void close() throws IOException;

}
