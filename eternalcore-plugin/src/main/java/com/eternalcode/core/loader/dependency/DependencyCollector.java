package com.eternalcode.core.loader.dependency;

import java.util.Collection;
import java.util.LinkedHashMap;

public class DependencyCollector {

    private final LinkedHashMap<String, Dependency> fullScannedDependencies = new LinkedHashMap<>();

    public boolean hasScannedDependency(Dependency dependency) {
        Dependency scanned = this.fullScannedDependencies.get(dependency.getGroupArtifactId());
        if (scanned == null) {
            return false;
        }

        if (scanned.isBom() && !dependency.isBom()) {
            return false;
        }

        return scanned.getVersion().equals(dependency.getVersion()) || scanned.isNewerThan(dependency);
    }

    public Dependency addScannedDependency(Dependency dependency) {
        Dependency current = this.fullScannedDependencies.get(dependency.getGroupArtifactId());

        if (current == null) {
            this.fullScannedDependencies.put(dependency.getGroupArtifactId(), dependency);
            return dependency;
        }

        if (!current.isBom() || !dependency.isBom()) {
            current = current.asNotBom();
            dependency = dependency.asNotBom();
        }

        Dependency resolved = dependency.isNewerThan(current) ? dependency : current;
        this.fullScannedDependencies.put(dependency.getGroupArtifactId(), resolved);
        return resolved;
    }

    public void addScannedDependencies(Collection<Dependency> dependencies) {
        for (Dependency dependency : dependencies) {
            this.addScannedDependency(dependency);
        }
    }

    public Collection<Dependency> getScannedDependencies() {
        return this.fullScannedDependencies.values().stream()
            .filter(dependency -> !dependency.isBom())
            .toList();
    }

}
