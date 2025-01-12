package com.eternalcode.core.feature.warp.repository;

import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.feature.warp.Warp;
import com.eternalcode.core.feature.warp.WarpImpl;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Repository
class WarpRepositoryImpl implements WarpRepository {

    private final LocationsConfiguration locationsConfiguration;
    private final WarpDataConfig warpDataConfig;
    private final ConfigurationManager configurationManager;

    @Inject
    WarpRepositoryImpl(
        ConfigurationManager configurationManager,
        LocationsConfiguration locationsConfiguration,
        WarpDataConfig warpDataConfig
    ) {
        this.locationsConfiguration = locationsConfiguration;
        this.configurationManager = configurationManager;
        this.warpDataConfig = warpDataConfig;

        this.migrateWarps();
    }

    @Override
    public CompletableFuture<Void> addWarp(Warp warp) {
        return CompletableFuture.runAsync(() -> {
            WarpConfigRepresenter warpConfigRepresenter = new WarpConfigRepresenter(
                PositionAdapter.convert(warp.getLocation()),
                warp.getPermissions()
            );

            this.edit(warps -> warps.put(warp.getName(), warpConfigRepresenter));
        });
    }

    @Override
    public CompletableFuture<Void> removeWarp(String warp) {
        return CompletableFuture.runAsync(() -> this.edit(warps -> warps.remove(warp)));
    }

    @Override
    public CompletableFuture<Void> addPermissions(String warp, String... permissions) {
        return CompletableFuture.runAsync(() -> this.edit(warps -> {
            WarpConfigRepresenter warpConfigRepresenter = warps.get(warp);
            if (warpConfigRepresenter == null) {
                return;
            }

            warpConfigRepresenter.permissions.addAll(List.of(permissions));
        }));
    }

    @Override
    public CompletableFuture<Void> removePermission(String warp, String permission) {
        return CompletableFuture.runAsync(() -> this.edit(warps -> {
            WarpConfigRepresenter warpConfigRepresenter = warps.get(warp);

            if (warpConfigRepresenter == null) {
                return;
            }

            warpConfigRepresenter.permissions.remove(permission);
        }));
    }

    private void edit(Consumer<Map<String, WarpConfigRepresenter>> editor) {
        synchronized (warpDataConfig.warps) {
            Map<String, WarpConfigRepresenter> warps = new HashMap<>(this.warpDataConfig.warps);
            editor.accept(warps);
            this.warpDataConfig.warps.putAll(warps);
            this.configurationManager.save(this.warpDataConfig);
        }
    }

    @Override
    public CompletableFuture<Optional<Warp>> getWarp(String name) {
        return CompletableFuture.completedFuture(Optional.ofNullable(this.warpDataConfig.warps.get(name))
            .map(warpConfigRepresenter -> new WarpImpl(
                name,
                warpConfigRepresenter.position,
                warpConfigRepresenter.permissions)));
    }

    @Override
    public CompletableFuture<List<Warp>> getWarps() {
        return CompletableFuture.completedFuture(this.warpDataConfig.warps.entrySet().stream()
            .map(warpConfigEntry -> {
                WarpConfigRepresenter warpContextual = warpConfigEntry.getValue();
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
            .collect(Collectors.toMap(
                Map.Entry::getKey, entry ->
                    new WarpConfigRepresenter(entry.getValue(), new ArrayList<>()))
            )
        ));

        this.locationsConfiguration.warps.clear();
        this.configurationManager.save(this.locationsConfiguration);
        this.configurationManager.save(this.warpDataConfig);
    }
}
