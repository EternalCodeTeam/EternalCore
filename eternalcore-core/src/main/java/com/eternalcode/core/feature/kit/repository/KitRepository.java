package com.eternalcode.core.feature.kit.repository;

import com.eternalcode.core.feature.kit.Kit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface KitRepository {

    CompletableFuture<Void> saveKit(Kit kit);

    CompletableFuture<Void> changeKitName(String oldName, String newName);

    CompletableFuture<Optional<Kit>> getKit(String kitName);

    CompletableFuture<List<Kit>> getKits();

    CompletableFuture<Void> removeKit(String kitName);

}
