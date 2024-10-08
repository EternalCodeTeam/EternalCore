package com.eternalcode.core.feature.warp;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

interface WarpRepository {

    void addWarp(Warp warp);

    void removeWarp(String warp);

    void addPermissions(String warp, String... permissions);

    void removePermission(String warp, String permission);

    CompletableFuture<Optional<Warp>> getWarp(String name);

    CompletableFuture<List<Warp>> getWarps();
}
