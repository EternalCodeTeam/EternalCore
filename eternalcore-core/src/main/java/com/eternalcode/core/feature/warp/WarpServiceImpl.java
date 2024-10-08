package com.eternalcode.core.feature.warp;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@FeatureDocs(
    name = "Warp System",
    description = "Allows you to create warps, optionally you can enable warp inventory"
)
@Service
class WarpServiceImpl implements WarpService {

    private final Map<String, Warp> warpMap = new HashMap<>();
    private final WarpRepository warpRepository;

    @Inject
    private WarpServiceImpl(WarpRepository warpRepository) {
        this.warpRepository = warpRepository;

        warpRepository.getWarps().thenAcceptAsync(warps -> {
            for (Warp warp : warps) {
                this.warpMap.put(warp.getName(), warp);
            }
        });
    }

    @Override
    public Warp createWarp(String name, Location location) {
        Warp warp = new WarpImpl(name, PositionAdapter.convert(location), new ArrayList<>());

        this.warpMap.put(name, warp);
        this.warpRepository.addWarp(warp);

        return warp;
    }

    @Override
    public void removeWarp(String warp) {
        Warp remove = this.warpMap.remove(warp);

        if (remove == null) {
            return;
        }

        this.warpRepository.removeWarp(remove.getName());
    }

    @Override
    public void addPermissions(String warpName, String... permissions) {
        Warp warp = this.warpMap.get(warpName);

        if (warp == null) {
            return;
        }

        List<String> updatedPermissions = new ArrayList<>(warp.getPermissions());
        updatedPermissions.addAll(List.of(permissions));

        warp.setPermissions(updatedPermissions);
        this.warpRepository.addPermissions(warpName, permissions);
    }

    @Override
    public void removePermission(String warpName, String permission) {
        Warp warp = this.warpMap.get(warpName);

        if (warp == null) {
            return;
        }

        List<String> updatedPermissions = new ArrayList<>(warp.getPermissions());
        updatedPermissions.remove(permission);

        warp.setPermissions(updatedPermissions);
        this.warpRepository.removePermission(warpName, permission);
    }

    @Override
    public boolean warpExists(String name) {
        return this.warpMap.containsKey(name);
    }

    @Override
    public boolean doestWarpPermissionExist(String warpName, String permission) {
        Warp warp = this.warpMap.get(warpName);

        if (warp == null) {
            return false;
        }

        return warp.getPermissions().contains(permission);
    }

    @Override
    public Optional<Warp> findWarp(String name) {
        return Optional.ofNullable(this.warpMap.get(name));
    }

    @Override
    public Collection<String> getNamesOfWarps() {
        return Collections.unmodifiableCollection(this.warpMap.keySet());
    }

    @Override
    public boolean hasWarps() {
        return !this.warpMap.isEmpty();
    }
}
