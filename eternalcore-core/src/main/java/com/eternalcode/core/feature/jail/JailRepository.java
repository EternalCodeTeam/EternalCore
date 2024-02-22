package com.eternalcode.core.feature.jail;

import panda.std.reactive.Completable;

import java.util.Optional;
import java.util.UUID;

public interface JailRepository {
    Completable<Optional<Prisoner>> getPrisoner(UUID uuid);

    void deletePrisoner(UUID uuid);

    void editPrisoner(Prisoner prisoner);

    void deleteAllPrisoners();

    Completable<java.util.List<Prisoner>> getAllPrisoners();

    void savePrisoner(Prisoner prisoner);
}
