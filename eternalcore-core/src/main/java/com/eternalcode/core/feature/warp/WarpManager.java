package com.eternalcode.core.feature.warp;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.shared.Position;
import org.bukkit.entity.Player;
import panda.std.Option;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@FeatureDocs(
    name = "Warp System",
    description = "Allows you to create warps, optionally you can enable warp inventory"
)
public class WarpManager {

    private final Map<String, Warp> warpMap = new HashMap<>();
    private final WarpRepository warpRepository;
    private final WarpSettings warpSettings;

    private WarpManager(WarpRepository warpRepository, WarpSettings warpSettings) {
        this.warpRepository = warpRepository;
        this.warpSettings = warpSettings;
    }

    public void createWarp(String name, Position position) {
        Warp warp = new Warp(name, position);

        this.warpMap.put(name, warp);

        this.warpRepository.addWarp(warp);
    }

    public void removeWarp(String warp) {
        Warp remove = this.warpMap.remove(warp);

        if (remove == null) {
            return;
        }

        this.warpRepository.removeWarp(remove.getName());
    }

    public boolean warpExists(String name) {
        return this.warpMap.containsKey(name);
    }

    public Option<Warp> findWarp(String name) {
        return Option.of(this.warpMap.get(name));
    }

    public Collection<String> getNamesOfWarps() {
        return Collections.unmodifiableCollection(this.warpMap.keySet());
    }

    public Duration findTeleportationTime(Player player) {
        if (this.warpSettings.teleportationTimesByPermission().isEmpty()) {
            return this.warpSettings.teleportationTimeToWarp();
        }

        for (String permission : this.warpSettings.teleportationTimesByPermission().keySet()) {
            if (player.hasPermission(permission)) {
                return this.warpSettings.teleportationTimesByPermission().get(permission);
            }
        }

        return this.warpSettings.teleportationTimeToWarp();
    }

    public static WarpManager create(WarpRepository warpRepository, WarpSettings warpSettings) {
        WarpManager warpManager = new WarpManager(warpRepository, warpSettings);

        warpRepository.getWarps().thenAcceptAsync(warps -> {
            for (Warp warp : warps) {
                warpManager.warpMap.put(warp.getName(), warp);
            }
        });

        return warpManager;
    }

}
