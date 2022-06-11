package com.eternalcode.core.warp;

import org.bukkit.Location;
import panda.std.Option;

import java.util.HashMap;
import java.util.Map;

public class WarpManager {

    private final Map<String, Warp> warpMap = new HashMap<>();
    private final WarpRepository warpRepository;

    private WarpManager(WarpRepository warpRepository) {
        this.warpRepository = warpRepository;
    }

    public void createWarp(String name, Location location) {
        Warp warp = new Warp(name, location.clone());

        this.warpMap.put(name, warp);
        this.warpRepository.addWarp(warp);
    }

    public boolean removeWarp(String warp) {
        Warp remove = this.warpMap.remove(warp);

        if (remove == null) {
            return false;
        }

        this.warpRepository.removeWarp(remove.getName());
        return true;
    }

    public Option<Warp> findWarp(String name) {
        return Option.of(this.warpMap.get(name));
    }

    public Map<String, Warp> getWarpMap() {
        return this.warpMap;
    }

    public static WarpManager create(WarpRepository warpRepository) {
        WarpManager warpManager = new WarpManager(warpRepository);

        warpRepository.getWarps().thenAcceptAsync(warps -> {
            for (Warp warp : warps) {
                warpManager.warpMap.put(warp.getName(), warp);
            }
        });

        return warpManager;
    }
}
