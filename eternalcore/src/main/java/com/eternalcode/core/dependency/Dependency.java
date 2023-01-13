package com.eternalcode.core.dependency;

import net.byteflux.libby.Library;

public class Dependency {

    private final String artifactId;
    private final String groupId;
    private final String version;

    public Dependency(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public Library createLibrary() {
        return Library.builder()
            .groupId(groupId)
            .artifactId(artifactId)
            .version(version)
            .build();
    }
}
