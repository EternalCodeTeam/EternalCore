package com.eternalcode.core.warps;

import panda.std.Option;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface WarpRepository {

    void addWarp(Warp warp);

    void removeWarp(String warp);

    CompletableFuture<Option<Warp>> getWarp(String name);

    CompletableFuture<List<Warp>> getWarps();

}
