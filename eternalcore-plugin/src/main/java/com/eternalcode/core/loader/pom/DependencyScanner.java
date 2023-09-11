package com.eternalcode.core.loader.pom;

import com.eternalcode.core.loader.dependency.Dependency;
import com.eternalcode.core.loader.dependency.DependencyCollector;

public interface DependencyScanner {
    DependencyCollector findAllChildren(DependencyCollector collector, Dependency dependency);
}
