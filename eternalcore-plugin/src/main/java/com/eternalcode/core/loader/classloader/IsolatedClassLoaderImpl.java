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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

public class IsolatedClassLoaderImpl extends URLClassLoader implements IsolatedClassLoader {

    static {
        ClassLoader.registerAsParallelCapable();
    }

    public IsolatedClassLoaderImpl(URL... urls) {
        super(requireNonNull(urls, "urls"), ClassLoader.getSystemClassLoader().getParent());
    }

    public IsolatedClassLoaderImpl(ClassLoader parent, URL... urls) {
        super(requireNonNull(urls, "urls"), parent);
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

    @Override
    public void addPath(Path path) {
        try {
            this.addURL(requireNonNull(path, "path").toUri().toURL());
        }
        catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void close() {
        try {
            super.close();
        }
        catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

}