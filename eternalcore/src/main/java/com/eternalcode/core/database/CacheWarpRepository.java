package com.eternalcode.core.database;

import com.eternalcode.core.warps.Warp;
import com.eternalcode.core.warps.WarpRepository;
import panda.std.Option;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CacheWarpRepository implements WarpRepository {

    @Override
    public void addWarp(Warp warp) {

    }

    @Override
    public void removeWarp(Warp warp) {

    }

    @Override
    public CompletableFuture<Option<Warp>> getWarp(String name) {
        return CompletableFuture.completedFuture(Option.none());
    }

    @Override
    public CompletableFuture<List<Warp>> getWarps() {
        return CompletableFuture.completedFuture(Collections.emptyList());
    }
}
