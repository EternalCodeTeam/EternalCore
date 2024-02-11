package com.eternalcode.core.feature.warp;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@FeatureDocs(
    name = "Warp System",
    description = "Allows you to create warps, optionally you can enable warp inventory"
)
@Service
class WarpManager implements WarpService {

    private final Map<String, Warp> warpMap = new HashMap<>();
    private final WarpRepository warpRepository;

    @Inject
    private WarpManager(WarpRepository warpRepository) {
        this.warpRepository = warpRepository;

        warpRepository.getWarps().thenAcceptAsync(warps -> { //TODO: Use only completable
            for (Warp warp : warps) {
                this.warpMap.put(warp.name(), warp);
            }
        });
    }

    public void createWarp(@NotNull String name, @NotNull Location location) {

        Warp warp = new Warp(name, location);

        this.warpMap.put(name, warp);

        this.warpRepository.addWarp(warp);
    }

    public void removeWarp(@NotNull String warp) {
        Warp remove = this.warpMap.remove(warp);

        if (remove == null) {
            return;
        }

        this.warpRepository.removeWarp(remove.name());
    }

    public boolean warpExists(@NotNull String name) {
        return this.warpMap.containsKey(name);
    }

    public Optional<Warp> findWarp(@NotNull String name) {
        return Optional.ofNullable(this.warpMap.get(name));
    }

    public Collection<String> getNamesOfWarps() {
        return Collections.unmodifiableCollection(this.warpMap.keySet());
    }

}
