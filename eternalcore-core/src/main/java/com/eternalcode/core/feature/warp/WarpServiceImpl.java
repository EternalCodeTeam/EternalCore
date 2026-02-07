package com.eternalcode.core.feature.warp;

import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.feature.warp.repository.WarpRepository;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Service
class WarpServiceImpl implements WarpService {

    private final Map<String, Warp> warps = new ConcurrentHashMap<>();
    private final WarpRepository warpRepository;

    @Inject
    WarpServiceImpl(WarpRepository warpRepository) {
        this.warpRepository = warpRepository;
        this.loadWarps();
    }

    private void loadWarps() {
        this.warpRepository.getWarps()
                .thenAcceptAsync(warps -> warps.forEach(warp -> this.warps.put(warp.getName(), warp)));
    }

    @Override
    public Warp createWarp(String name, Location location) {
        Warp warp = new WarpImpl(name, PositionAdapter.convert(location), new ArrayList<>());

        this.warps.put(name, warp);
        this.warpRepository.saveWarp(warp);

        return warp;
    }

    @Override
    public void removeWarp(String warpName) {
        Warp removed = this.warps.remove(warpName);

        if (removed != null) {
            this.warpRepository.removeWarp(removed.getName());
        }
    }

    @Override
    public Warp addPermissions(String warpName, String... permissions) {
        Warp warp = this.modifyPermissions(warpName, perms -> perms.addAll(List.of(permissions)));
        this.warpRepository.saveWarp(warp);

        return warp;
    }

    @Override
    public Warp removePermissions(String warpName, String... permissions) {
        Warp warp = this.modifyPermissions(warpName, perms -> perms.removeAll(List.of(permissions)));
        this.warpRepository.saveWarp(warp);

        return warp;
    }

    private Warp modifyPermissions(String warpName, Consumer<List<String>> modifier) {
        Warp warp = this.warps.get(warpName);

        if (warp == null) {
            throw new IllegalArgumentException("Warp " + warpName + " does not exist");
        }

        List<String> updatedPermissions = new ArrayList<>(warp.getPermissions());
        modifier.accept(updatedPermissions);

        Warp updatedWarp = new WarpImpl(
                warp.getName(),
                PositionAdapter.convert(warp.getLocation()),
                updatedPermissions);

        this.warps.put(warpName, updatedWarp);
        return updatedWarp;
    }

    @Override
    public boolean exists(String warp) {
        return this.warps.containsKey(warp);
    }

    @Override
    public Optional<Warp> findWarp(String warp) {
        return Optional.ofNullable(this.warps.get(warp));
    }

    @Override
    public Collection<Warp> getWarps() {
        return Collections.unmodifiableCollection(this.warps.values());
    }
}
