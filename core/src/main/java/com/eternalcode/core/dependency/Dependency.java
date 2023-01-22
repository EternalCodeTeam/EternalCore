package com.eternalcode.core.dependency;

import net.byteflux.libby.Library;
import net.byteflux.libby.relocation.Relocation;

import java.util.List;

public class Dependency {

    private final String artifactId;
    private final String groupId;
    private final String version;
    private final List<Relocation> relocation;

    public Dependency(String groupId, String artifactId, String version, List<Relocation> relocation) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.relocation = relocation;
    }

    public Library createLibrary() {
        Library.Builder builder = Library.builder();

        for (Relocation relocation : this.relocation) {
            builder.relocate(relocation);
        }

        return builder
            .groupId(groupId)
            .artifactId(artifactId)
            .version(version)
            .build();
    }
}

