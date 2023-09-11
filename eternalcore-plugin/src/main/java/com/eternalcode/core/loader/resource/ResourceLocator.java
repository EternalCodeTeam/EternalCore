package com.eternalcode.core.loader.resource;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

public class ResourceLocator {

    private final URL url;

    private ResourceLocator(URL url) {
        this.url = url;
    }

    public URL toURL() {
        return this.url;
    }

    public URI toURI() {
        return URI.create(this.url.toString());
    }

    public Path toPath() {
        File file = this.toFile();
        return file.toPath();
    }

    public File toFile() {
        try {
            return new File(this.url.toURI());
        }
        catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static ResourceLocator fromURL(URL url) {
        return new ResourceLocator(url);
    }

    public static ResourceLocator fromURI(URI uri) {
        try {
            return new ResourceLocator(uri.toURL());
        }
        catch (MalformedURLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static ResourceLocator fromPath(Path path) {
        return fromFile(path.toFile());
    }

    public static ResourceLocator fromFile(File file) {
        try {
            return new ResourceLocator(file.toURI().toURL());
        }
        catch (MalformedURLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static ResourceLocator fromString(String string) {
        try {
            return new ResourceLocator(new URL(string));
        }
        catch (MalformedURLException exception) {
            throw new RuntimeException(exception);
        }
    }

}
