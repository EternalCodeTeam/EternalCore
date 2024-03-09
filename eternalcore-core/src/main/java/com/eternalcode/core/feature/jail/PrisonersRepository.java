package com.eternalcode.core.feature.jail;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PrisonersRepository {
    CompletableFuture<Optional<Prisoner>> getPrisoner(UUID uuid);

    CompletableFuture<Set<Prisoner>> getPrisoners();

    void deletePrisoner(UUID uuid);

    void editPrisoner(Prisoner prisoner);

    void deleteAllPrisoners();

    CompletableFuture<List<Prisoner>> getAllPrisoners();

    void savePrisoner(Prisoner prisoner);
}
