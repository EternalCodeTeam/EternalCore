package com.eternalcode.core.feature.jail;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

interface PrisonerRepository {

    CompletableFuture<Optional<JailedPlayer>> getPrisoner(UUID uuid);

    CompletableFuture<Set<JailedPlayer>> getPrisoners();

    void deletePrisoner(UUID uuid);

    void editPrisoner(JailedPlayer jailedPlayer);

    void deleteAllPrisoners();

    CompletableFuture<List<JailedPlayer>> getAllPrisoners();

    void savePrisoner(JailedPlayer jailedPlayer);

}
