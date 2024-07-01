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

package com.eternalcode.core.loader.classloader;

import io.sentry.Sentry;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.Collection;

abstract class URLClassLoaderAccessor {

    public static URLClassLoaderAccessor create(URLClassLoader classLoader) {
        if (ReflectionURLClassLoaderAccessor.isSupported()) {
            return new ReflectionURLClassLoaderAccessor(classLoader);
        }

        if (UnsafeURLClassLoaderAccessor.isSupported()) {
            return new UnsafeURLClassLoaderAccessor(classLoader);
        }

        return Noop.INSTANCE;
    }

    private final URLClassLoader classLoader;

    protected URLClassLoaderAccessor(URLClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public abstract void addURL(URL url);

    public void addJarToClasspath(Path file) {
        try {
            this.addURL(file.toUri().toURL());
        }
        catch (MalformedURLException exception) {
            Sentry.captureException(exception);
            throw new RuntimeException(exception);
        }
    }

    private static void throwError(Throwable cause) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("EternalCore is unable to inject into the plugin URLClassLoader.\n" +
                "You may be able to fix this problem by adding the following command-line argument " +
                "directly after the 'java' command in your start script: \n'--add-opens java.base/java.lang=ALL-UNNAMED'", cause);
    }

    private static class ReflectionURLClassLoaderAccessor extends URLClassLoaderAccessor {

        private static final Method ADD_URL_METHOD;

        static {
            Method addUrlMethod;
            try {
                addUrlMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                addUrlMethod.setAccessible(true);
            }
            catch (Exception e) {
                Sentry.captureException(e);
                addUrlMethod = null;
            }
            ADD_URL_METHOD = addUrlMethod;
        }

        private static boolean isSupported() {
            return ADD_URL_METHOD != null;
        }

        ReflectionURLClassLoaderAccessor(URLClassLoader classLoader) {
            super(classLoader);
        }

        @Override
        public void addURL(URL url) {
            try {
                ADD_URL_METHOD.invoke(super.classLoader, url);
            }
            catch (ReflectiveOperationException e) {
                Sentry.captureException(e);
                URLClassLoaderAccessor.throwError(e);
            }
        }

    }

    /**
     * Accesses using sun.misc.Unsafe, supported on Java 9+.
     *
     * @author Vaishnav Anil (https://github.com/slimjar/slimjar)
     */
    private static class UnsafeURLClassLoaderAccessor extends URLClassLoaderAccessor {

        private static final sun.misc.Unsafe UNSAFE;

        static {
            sun.misc.Unsafe unsafe;
            try {
                Field unsafeField = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
                unsafeField.setAccessible(true);
                unsafe = (sun.misc.Unsafe) unsafeField.get(null);
            }
            catch (Throwable throwable) {
                Sentry.captureException(throwable);
                unsafe = null;
            }
            UNSAFE = unsafe;
        }

        private static boolean isSupported() {
            return UNSAFE != null;
        }

        private final Collection<URL> unopenedURLs;
        private final Collection<URL> pathURLs;

        @SuppressWarnings("unchecked")
        UnsafeURLClassLoaderAccessor(URLClassLoader classLoader) {
            super(classLoader);

            Collection<URL> unopenedURLs;
            Collection<URL> pathURLs;
            try {
                Object ucp = fetchField(URLClassLoader.class, classLoader, "ucp");
                unopenedURLs = (Collection<URL>) fetchField(ucp.getClass(), ucp, "unopenedUrls");
                pathURLs = (Collection<URL>) fetchField(ucp.getClass(), ucp, "path");
            }
            catch (Throwable throwable) {
                Sentry.captureException(throwable);
                unopenedURLs = null;
                pathURLs = null;
            }

            this.unopenedURLs = unopenedURLs;
            this.pathURLs = pathURLs;
        }

        private static Object fetchField(final Class<?> clazz, final Object object, final String name) throws NoSuchFieldException {
            Field field = clazz.getDeclaredField(name);
            long offset = UNSAFE.objectFieldOffset(field);
            return UNSAFE.getObject(object, offset);
        }

        @Override
        public void addURL(URL url) {
            if (this.unopenedURLs == null || this.pathURLs == null) {
                URLClassLoaderAccessor.throwError(new NullPointerException("unopenedURLs or pathURLs"));
            }

            synchronized (this.unopenedURLs) {
                this.unopenedURLs.add(url);
                this.pathURLs.add(url);
            }
        }

    }

    private static class Noop extends URLClassLoaderAccessor {

        private static final Noop INSTANCE = new Noop();

        private Noop() {
            super(null);
        }

        @Override
        public void addURL(URL url) {
            URLClassLoaderAccessor.throwError(null);
        }

    }

}
