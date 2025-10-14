package com.eternalcode.core.loader.relocation;

import com.eternalcode.core.loader.dependency.Dependency;
import com.eternalcode.core.loader.repository.LocalRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RelocationCacheResolver {

    private static final String RELOCATIONS_FILE = "relocations.txt";

    private final LocalRepository localRepository;

    public RelocationCacheResolver(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public boolean shouldForceRelocate(Dependency dependency, List<Relocation> relocations) {
        return this.getRawSavedRelocations(dependency)
            .map(savedRelocations -> !savedRelocations.equals(toRawRelocations(relocations)))
            .orElse(true);
    }

    public void markAsRelocated(Dependency dependency, List<Relocation> relocations) {
        try {
            File relocationsCache = dependency.toResource(localRepository, RELOCATIONS_FILE).toFile();
            Files.writeString(relocationsCache.toPath(), toRawRelocations(relocations), StandardCharsets.UTF_8);
        } catch (Exception exception) {
            throw new RuntimeException("Failed to save relocations", exception);
        }
    }

    private static String toRawRelocations(List<Relocation> relocations) {
        return relocations.stream()
            .map(relocation -> relocation.pattern() + " -> " + relocation.relocatedPattern())
            .collect(Collectors.joining("\n"));
    }

    private Optional<String> getRawSavedRelocations(Dependency dependency) {
        try {
            File relocationsCache = dependency.toResource(localRepository, RELOCATIONS_FILE).toFile();
            if (!relocationsCache.exists()) {
                return Optional.empty();
            }
            return Optional.of(Files.readString(relocationsCache.toPath()));
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

}
