/*
 * This file is part of LuckPerms, licensed under the MIT License.
 *
 *  Copyright (c) lucko (Luck) <luck@lucko.me>
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.eternalcode.core.loader.relocation;

import com.eternalcode.core.loader.classloader.IsolatedClassLoader;
import com.eternalcode.core.loader.dependency.Dependency;
import com.eternalcode.core.loader.dependency.DependencyException;
import com.eternalcode.core.loader.dependency.DependencyLoadResult;
import com.eternalcode.core.loader.dependency.DependencyLoaderImpl;
import com.eternalcode.core.loader.repository.Repository;
import io.sentry.Sentry;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles class runtime relocation of packages in downloaded dependencies
 */
public class RelocationHandler implements AutoCloseable {

    private static final List<Dependency> DEPENDENCIES = List.of(
            Dependency.of(
                    "org.ow2.asm",
                    "asm",
                    "9.2"
            ),
            Dependency.of(
                    "org.ow2.asm",
                    "asm-commons",
                    "9.2"
            ),
            Dependency.of(
                    "me.lucko",
                    "jar-relocator",
                    "1.7"
            )
    );

    private static final String JAR_RELOCATOR_CLASS = "me.lucko.jarrelocator.JarRelocator";
    private static final String JAR_RELOCATOR_RUN_METHOD = "run";

    private final IsolatedClassLoader classLoader;
    private final Constructor<?> jarRelocatorConstructor;
    private final Method jarRelocatorRunMethod;

    private RelocationHandler(IsolatedClassLoader classLoader, Constructor<?> jarRelocatorConstructor, Method jarRelocatorRunMethod) {
        this.classLoader = classLoader;
        this.jarRelocatorConstructor = jarRelocatorConstructor;
        this.jarRelocatorRunMethod = jarRelocatorRunMethod;
    }

    public Path relocateDependency(Repository localRepository, Path dependencyPath, Dependency dependency, List<Relocation> relocations) {
        if (relocations.isEmpty()) {
            return dependencyPath;
        }

        Path relocatedJar = dependency.toMavenJar(localRepository, "relocated").toPath();

        if (Files.exists(relocatedJar)) {
            return relocatedJar;
        }

        return this.relocate(dependencyPath, relocatedJar, relocations);
    }

    private Path relocate(Path input, Path output, List<Relocation> relocations) {
        Map<String, String> mappings = new HashMap<>();

        for (Relocation relocation : relocations) {
            mappings.put(relocation.getPattern(), relocation.getRelocatedPattern());
        }

        try {
            Files.createDirectories(output.getParent());
            Files.createFile(output);
            Object relocator = this.jarRelocatorConstructor.newInstance(input.toFile(), output.toFile(), mappings);

            this.jarRelocatorRunMethod.invoke(relocator);
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException | IOException exception) {
            Sentry.captureException(exception);
            throw new DependencyException("Failed to create JarRelocator instance", exception);
        }

        return output;
    }

    public static RelocationHandler create(DependencyLoaderImpl dependencyLoaderImpl) {
        DependencyLoadResult result = dependencyLoaderImpl.load(DEPENDENCIES, List.of());
        IsolatedClassLoader classLoader = result.loader();

        try {
            Class<?> jarRelocatorClass = classLoader.loadClass(JAR_RELOCATOR_CLASS);

            Constructor<?> jarRelocatorConstructor = jarRelocatorClass.getDeclaredConstructor(File.class, File.class, Map.class);
            jarRelocatorConstructor.setAccessible(true);

            Method jarRelocatorRunMethod = jarRelocatorClass.getDeclaredMethod(JAR_RELOCATOR_RUN_METHOD);
            jarRelocatorRunMethod.setAccessible(true);

            return new RelocationHandler(classLoader, jarRelocatorConstructor, jarRelocatorRunMethod);
        }
        catch (ClassNotFoundException | NoSuchMethodException exception) {
            Sentry.captureException(exception);
            throw new DependencyException(exception);
        }
    }

    @Override
    public void close() throws IOException {
        this.classLoader.close();
    }

}
