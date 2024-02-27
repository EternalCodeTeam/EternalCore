package com.eternalcode.core.feature.jail;

import panda.std.reactive.Completable;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PrisonersRepository {
    Completable<Optional<Prisoner>> getPrisoner(UUID uuid);

    Completable<Set<Prisoner>> getPrisoners();

    void deletePrisoner(UUID uuid);

    void editPrisoner(Prisoner prisoner);

    void deleteAllPrisoners();

    Completable<java.util.List<Prisoner>> getAllPrisoners();

    void savePrisoner(Prisoner prisoner);
}
