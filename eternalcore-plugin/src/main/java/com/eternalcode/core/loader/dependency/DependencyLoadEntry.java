package com.eternalcode.core.loader.dependency;

import java.nio.file.Path;

public record DependencyLoadEntry(Dependency dependency, Path path) {
}
