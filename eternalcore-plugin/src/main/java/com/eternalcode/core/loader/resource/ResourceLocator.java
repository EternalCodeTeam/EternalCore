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

    public static ResourceLocator from(String uri) {
        try {
            return new ResourceLocator(URI.create(uri).toURL());
        }
        catch (MalformedURLException exception) {
            throw new RuntimeException(exception);
        }
    }

}
