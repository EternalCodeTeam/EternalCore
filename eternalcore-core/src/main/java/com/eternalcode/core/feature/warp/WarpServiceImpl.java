package com.eternalcode.core.feature.warp;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.feature.warp.data.WarpDataRepository;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import org.bukkit.Location;

@FeatureDocs(
    name = "Warp System",
    description = "Allows you to create warps, optionally you can enable warp inventory"
)
@Service
class WarpServiceImpl implements WarpService {

    private final Map<String, Warp> warps = new ConcurrentHashMap<>();
    private final WarpDataRepository warpRepository;

    @Inject
    private WarpServiceImpl(WarpDataRepository warpRepository) {
        this.warpRepository = warpRepository;

        warpRepository.getWarps().thenAcceptAsync(warps -> {
            for (Warp warp : warps) {
                this.warps.put(warp.getName(), warp);
            }
        });
    }

    @Override
    public Warp create(String name, Location location) {
        Warp warp = new WarpImpl(name, PositionAdapter.convert(location), new ArrayList<>());

        this.warps.put(name, warp);
        this.warpRepository.addWarp(warp);

        return warp;
    }

    @Override
    public void delete(String warp) {
        Warp remove = this.warps.remove(warp);

        if (remove == null) {
            return;
        }

        this.warpRepository.removeWarp(remove.getName());
    }

    @Override
    public void addPermissions(String warpName, String... permissions) {
        Warp warp = this.warps.get(warpName);

        if (warp == null) {
            return;
        }

        if (permissions == null) {
            return;
        }

        List<String> updatedPermissions = new ArrayList<>(warp.getPermissions());
        updatedPermissions.addAll(List.of(permissions));

        this.modifyPermissions(warpName, perms -> perms.addAll(List.of(permissions)));
        this.warpRepository.addPermissions(warpName, permissions);
    }

    @Override
    public void removePermission(String warpName, String permission) {
        Warp warp = this.warps.get(warpName);

        if (warp == null) {
            return;
        }

        this.modifyPermissions(warpName, perms -> perms.remove(permission));
        this.warpRepository.removePermission(warpName, permission);
    }

    private void modifyPermissions(String warpName, Consumer<List<String>> modifier) {
        Warp warp = this.warps.get(warpName);

        if (warp == null) {
            return;
        }

        List<String> updatedPermissions = new ArrayList<>(warp.getPermissions());
        modifier.accept(updatedPermissions);

        Warp updatedWarp = new WarpImpl(
            warp.getName(),
            PositionAdapter.convert(warp.getLocation()),
            updatedPermissions
        );

        this.warps.put(warpName, updatedWarp);
    }

    @Override
    public boolean exists(String name) {
        return this.warps.containsKey(name);
    }

    @Override
    public boolean hasPermission(String warpName, String permission) {
        Warp warp = this.warps.get(warpName);

        if (warp == null) {
            return false;
        }

        return warp.getPermissions().contains(permission);
    }

    @Override
    public Optional<Warp> findWarp(String name) {
        return Optional.ofNullable(this.warps.get(name));
    }

    @Override
    public Collection<String> getAllNames() {
        return Collections.unmodifiableCollection(this.warps.keySet());
    }

    @Override
    public boolean isEmpty() {
        return this.warps.isEmpty();
    }
}
