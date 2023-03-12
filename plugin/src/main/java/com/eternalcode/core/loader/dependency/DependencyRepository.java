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

package com.eternalcode.core.loader.dependency;

import com.google.common.io.ByteStreams;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

public class DependencyRepository {

    private final String url;

    private DependencyRepository(String url) {
        this.url = url;
    }

    protected URLConnection openConnectionForJar(Dependency dependency) throws IOException {
        URL dependencyUrl = new URL(this.url + dependency.getMavenJarPath());
        return dependencyUrl.openConnection();
    }

    public byte[] downloadRaw(ThrowingSupplier<URLConnection> supplier) throws DependencyDownloadException {
        try {
            URLConnection connection = supplier.get();

            try (InputStream in = connection.getInputStream()) {
                byte[] bytes = ByteStreams.toByteArray(in);
                if (bytes.length == 0) {
                    throw new DependencyDownloadException("Empty stream");
                }
                return bytes;
            }
        }
        catch (Exception e) {
            throw new DependencyDownloadException(e);
        }
    }

    public byte[] downloadJar(Dependency dependency) throws DependencyDownloadException {
        return this.downloadRaw(() -> this.openConnectionForJar(dependency));
    }

    public void downloadJar(Dependency dependency, Path file) throws DependencyDownloadException {
        try {
            Files.write(file, this.downloadJar(dependency));
        }
        catch (IOException e) {
            throw new DependencyDownloadException(e);
        }
    }

    public static DependencyRepository of(String url) {
        return new DependencyRepository(url);
    }

    public String getUrl() {
        return url;
    }

    interface ThrowingSupplier<T> {
        T get() throws Exception;
    }

}