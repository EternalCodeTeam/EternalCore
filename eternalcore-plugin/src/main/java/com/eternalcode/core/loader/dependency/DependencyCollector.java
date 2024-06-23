package com.eternalcode.core.loader.dependency;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;

public class DependencyCollector {

    private final LinkedHashMap<String, Dependency> fullScannedDependencies = new LinkedHashMap<>();

    public boolean hasScannedDependency(Dependency dependency) {
        return this.fullScannedDependencies.containsKey(dependency.getGroupArtifactId());
    }

    public void scannedDependency(Dependency dependency) {
        Dependency current = this.fullScannedDependencies.get(dependency.getGroupArtifactId());

        if (current == null) {
            this.fullScannedDependencies.put(dependency.getGroupArtifactId(), dependency);
            return;
        }

        if (dependency.isNewerThan(current)) {
            this.fullScannedDependencies.put(dependency.getGroupArtifactId(), dependency);
        }
    }

    public void scannedDependencies(Collection<Dependency> dependencies) {
        for (Dependency dependency : dependencies) {
            this.scannedDependency(dependency);
        }
    }

    public Collection<Dependency> scannedDependencies() {
        return Collections.unmodifiableCollection(this.fullScannedDependencies.values());
    }

}
