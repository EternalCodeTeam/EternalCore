package com.eternalcode.core.loader;

import com.eternalcode.core.loader.dependency.Dependency;
import com.eternalcode.core.loader.repository.Repository;
import com.eternalcode.core.loader.relocation.Relocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class EternalCoreLoaderConstants {

    private static final String DEPENDENCIES_HOLDER = "{eternalcore-dependencies}";
    private static final String REPOSITORIES_HOLDER = "{eternalcore-repositories}";
    private static final String RELOCATIONS_HOLDER = "{eternalcore-relocations}";
    private static final List<Relocation> RELOCATIONS = createRelocations();

    private static final String RELOCATION_PREFIX = "com{}eternalcode{}core{}libs{}";

    private EternalCoreLoaderConstants() {
        throw new UnsupportedOperationException("This class cannot be instantiated!");
    }

    public static List<Dependency> dependencies() {
        List<Dependency> dependencies = new ArrayList<>();

        for (String rawDependency : DEPENDENCIES_HOLDER.split("\\|")) {
            String[] dependencyData = rawDependency.split(":");

            if (dependencyData.length != 3) {
                throw new IllegalStateException("Dependency " + rawDependency + " is not valid!");
            }

            String groupId = dependencyData[0];
            String artifactId = dependencyData[1];
            String version = dependencyData[2];

            Dependency dependency = Dependency.of(groupId, artifactId, version);

            dependencies.add(dependency);
        }

        return dependencies;
    }

    public static List<Repository> repositories() {
        return Arrays.stream(REPOSITORIES_HOLDER.split("\\|"))
            .map(Repository::of)
            .toList();
    }

    public static List<Relocation> relocations() {
        return RELOCATIONS;
    }

    private static List<Relocation> createRelocations() {
        return Arrays.stream(RELOCATIONS_HOLDER.split("\\|"))
            .map(rawRelocation -> Relocation.builder()
                    .pattern(rawRelocation)
                    .relocatedPattern(RELOCATION_PREFIX + rawRelocation)
                    .build()
            )
            .toList();
    }


}