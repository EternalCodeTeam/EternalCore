package com.eternalcode.core.loader.relocation;

import com.eternalcode.core.loader.repository.LocalRepository;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RelocationCacheResolver {

    private static final String RELOCATIONS_FILE = "relocations.txt";

    private final File relocationsFile;

    public RelocationCacheResolver(LocalRepository localRepository) {
        this.relocationsFile = localRepository.getRepositoryFolder().resolve(RELOCATIONS_FILE).toFile();
    }

    public boolean shouldForceRelocate(List<Relocation> relocations) {
        return this.getSavedRelocations()
            .map(savedRelocations -> !savedRelocations.equals(toRawRelocations(relocations)))
            .orElse(true);
    }

    public void markAsRelocated(List<Relocation> relocations) {
        try {
            Files.writeString(relocationsFile.toPath(), toRawRelocations(relocations), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to save relocations", exception);
        }
    }

    private static String toRawRelocations(List<Relocation> relocations) {
        return relocations.stream()
            .map(relocation -> relocation.pattern() + " -> " + relocation.relocatedPattern())
            .collect(Collectors.joining("\n"));
    }

    private Optional<String> getSavedRelocations() {
        try {
            if (!relocationsFile.exists()) {
                return Optional.empty();
            }
            return Optional.of(Files.readString(relocationsFile.toPath(), StandardCharsets.UTF_8));
        } catch (Exception exception) {
            return Optional.empty();
        }
    }
}
