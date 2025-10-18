package com.eternalcode.core.feature.jail;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

interface PrisonerRepository {

    CompletableFuture<Optional<JailedPlayer>> getPrisoner(UUID uuid);

    CompletableFuture<Set<JailedPlayer>> getPrisoners();

    void deletePrisoner(UUID uuid);

    void savePrisoner(JailedPlayer jailedPlayer);

    void deleteAllPrisoners();
}
