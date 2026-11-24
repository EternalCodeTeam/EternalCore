package com.eternalcode.core.feature.kit.repository;

import com.eternalcode.core.feature.kit.Kit;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface KitRepository {

    CompletableFuture<List<Kit>> findAll();

    CompletableFuture<Void> save(Kit kit);

    CompletableFuture<Void> delete(String kitName);

}
