package com.eternalcode.core.feature.warp.repository;

import com.eternalcode.core.feature.warp.Warp;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface WarpRepository {

    CompletableFuture<Void> addWarp(Warp warp);

    CompletableFuture<Void> removeWarp(String warp);

    CompletableFuture<Void> addPermissions(String warp, String... permissions);

    CompletableFuture<Void> removePermission(String warp, String permission);

    CompletableFuture<Optional<Warp>> getWarp(String name);

    CompletableFuture<List<Warp>> getWarps();
}
