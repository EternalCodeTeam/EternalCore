package com.eternalcode.core.warps;

import panda.std.Option;

import java.util.HashMap;
import java.util.Map;

public class WarpManager {

    private final Map<String, Warp> warpMap = new HashMap<>();

    public void createWarp(String name) {
        Warp warp = new Warp(name);

        this.warpMap.put(name, warp);
    }

    public void removeWarp(String warp) {
        this.warpMap.remove(warp);
    }

    public Option<Warp> findWarp(String name) {
        return Option.of(this.warpMap.get(name));
    }

    public Map<String, Warp> getWarpMap() {
        return this.warpMap;
    }
}