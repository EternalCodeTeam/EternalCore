package com.eternalcode.core.loader.dependency;

import java.util.Collection;
import java.util.LinkedHashSet;

public class DependencyCollector {

    private final LinkedHashSet<Dependency> fullScannedDependencies = new LinkedHashSet<>();

    public boolean hasScannedDependency(Dependency dependency) {
        return fullScannedDependencies.contains(dependency);
    }

    public void scannedDependency(Dependency dependency) {
        fullScannedDependencies.add(dependency);
    }

    public void scannedDependencies(Collection<Dependency> dependencies) {
        fullScannedDependencies.addAll(dependencies);
    }

    public LinkedHashSet<Dependency> scannedDependencies() {
        return fullScannedDependencies;
    }

}
