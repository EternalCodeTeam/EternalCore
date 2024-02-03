package com.eternalcode.core.feature.warp;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.shared.Position;
import panda.std.Option;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@FeatureDocs(
    name = "Warp System",
    description = "Allows you to create warps, optionally you can enable warp inventory"
)
@Service
class WarpManager {

    private final Map<String, Warp> warpMap = new HashMap<>();
    private final WarpRepository warpRepository;

    @Inject
    private WarpManager(WarpRepository warpRepository) {
        this.warpRepository = warpRepository;

        warpRepository.getWarps().thenAcceptAsync(warps -> { //TODO: Use only completable
            for (Warp warp : warps) {
                this.warpMap.put(warp.getName(), warp);
            }
        });
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

}
