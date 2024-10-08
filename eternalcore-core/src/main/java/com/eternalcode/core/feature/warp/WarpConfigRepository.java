package com.eternalcode.core.feature.warp;

import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.configuration.implementation.WarpsConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
class WarpConfigRepository implements WarpRepository {

    private final LocationsConfiguration locationsConfiguration;
    private final WarpsConfiguration warpsConfiguration;
    private final ConfigurationManager configurationManager;

    @Inject
    WarpConfigRepository(
        ConfigurationManager configurationManager,
        LocationsConfiguration locationsConfiguration,
        WarpsConfiguration warpsConfiguration
    ) {
        this.locationsConfiguration = locationsConfiguration;
        this.configurationManager = configurationManager;
        this.warpsConfiguration = warpsConfiguration;

        this.migrateWarps();
    }

    @Override
    public void addWarp(Warp warp) {
        WarpConfigEntry warpConfigEntry = new WarpConfigEntry(PositionAdapter.convert(warp.getLocation()), warp.getPermissions());
        this.edit(warps -> warps.put(
            warp.getName(),
            warpConfigEntry
        ));
    }

    @Override
    public void removeWarp(String warp) {
        this.edit(warps -> warps.remove(warp));
    }

    @Override
    public void addPermissions(String warp, String... permissions) {
        this.edit(warps -> {
            WarpConfigEntry warpConfigEntry = warps.get(warp);
            if (warpConfigEntry == null) {
                return;
            }

            List<String> newPermissions = new ArrayList<>(warpConfigEntry.permissions);
            newPermissions.addAll(List.of(permissions));

            warpConfigEntry.permissions = newPermissions;
        });
    }

    @Override
    public void removePermission(String warp, String permission) {
        this.edit(warps -> {
            WarpConfigEntry warpConfigEntry = warps.get(warp);

            if (warpConfigEntry == null) {
                return;
            }

            List<String> newPermissions = new ArrayList<>(warpConfigEntry.permissions);
            newPermissions.remove(permission);

            warpConfigEntry.permissions = newPermissions;
        });
    }

    private void edit(Consumer<Map<String, WarpConfigEntry>> editor) {
        Map<String, WarpConfigEntry> warps = new HashMap<>(this.warpsConfiguration.warps);
        editor.accept(warps);

        warps.forEach((key, value) -> System.out.println(key + ": " + value));
        this.warpsConfiguration.warps.putAll(warps);
        this.configurationManager.save(this.warpsConfiguration);
    }

    @Override
    public CompletableFuture<Optional<Warp>> getWarp(String name) {
        return CompletableFuture.completedFuture(Optional.of(this.warpsConfiguration.warps.get(name))
            .map(warpConfigEntry -> new WarpImpl(name, warpConfigEntry.position, warpConfigEntry.permissions)));
    }

    @Override
    public CompletableFuture<List<Warp>> getWarps() {
        return CompletableFuture.completedFuture(this.warpsConfiguration.warps.entrySet().stream()
            .map(warpConfigEntry -> {
                WarpConfigEntry warpContextual = warpConfigEntry.getValue();
                return new WarpImpl(warpConfigEntry.getKey(), warpContextual.position, warpContextual.permissions);
            })
            .collect(Collectors.toList()));
    }

    private void migrateWarps() {
        if (this.locationsConfiguration.warps.isEmpty()) {
            return;
        }

        this.edit(warps -> warps.putAll(this.locationsConfiguration.warps
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, entry ->
                new WarpConfigEntry(entry.getValue(), new ArrayList<>()))
            )
        ));

        this.locationsConfiguration.warps.clear();
        this.configurationManager.save(this.locationsConfiguration);
        this.configurationManager.save(this.warpsConfiguration);
    }
}
