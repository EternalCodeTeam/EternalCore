package com.eternalcode.core.feature.warp;

import panda.std.Option;

import java.util.List;
import java.util.concurrent.CompletableFuture;

interface WarpRepository {

    void addWarp(Warp warp);

    void removeWarp(String warp);

    CompletableFuture<Option<Warp>> getWarp(String name);

    CompletableFuture<List<Warp>> getWarps();

}
