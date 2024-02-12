package com.eternalcode.core.feature.warp;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.shared.PositionAdapter;
import org.bukkit.Location;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.Collection;
import java.util.Collections;

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
                this.warpMap.put(warp.getName(), warp);
            }
        });
    }

    @Override
    public void createWarp( String name, Location location) {

        Warp warp = new WarpImpl(name, PositionAdapter.convert(location));

        this.warpMap.put(name, warp);

        this.warpRepository.addWarp(warp);
    }

    @Override
    public void removeWarp( String warp) {
        Warp remove = this.warpMap.remove(warp);

        if (remove == null) {
            return;
        }

        this.warpRepository.removeWarp(remove.getName());
    }

    @Override
    public boolean warpExists( String name) {
        return this.warpMap.containsKey(name);
    }

    @Override
    public Optional<Warp> findWarp( String name) {
        return Optional.ofNullable(this.warpMap.get(name));
    }

    @Override
    public Collection<String> getNamesOfWarps() {
        return Collections.unmodifiableCollection(this.warpMap.keySet());
    }

}
