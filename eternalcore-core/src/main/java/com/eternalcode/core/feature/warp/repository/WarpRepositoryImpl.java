package com.eternalcode.core.feature.warp.repository;

import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.commons.scheduler.Scheduler;
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
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
class WarpRepositoryImpl implements WarpRepository {

    private static final Object READ_WRITE_LOCK = new Object();

    private final LocationsConfiguration locationsConfiguration;
    private final WarpConfig warpConfig;
    private final ConfigurationManager configurationManager;
    private final Scheduler scheduler;

    @Inject
    WarpRepositoryImpl(
        ConfigurationManager configurationManager,
        LocationsConfiguration locationsConfiguration,
        WarpConfig warpConfig,
        Scheduler scheduler
    ) {
        this.locationsConfiguration = locationsConfiguration;
        this.configurationManager = configurationManager;
        this.warpConfig = warpConfig;
        this.scheduler = scheduler;

        this.migrateWarps();
    }

    @Override
    public CompletableFuture<Void> saveWarp(Warp warp) {
        WarpConfig.WarpConfigEntry warpConfigEntry = new WarpConfig.WarpConfigEntry(
            PositionAdapter.convert(warp.getLocation()),
            warp.getPermissions()
        );

        return this.transactionalRun(warps -> warps.put(warp.getName(), warpConfigEntry));
    }

    @Override
    public CompletableFuture<Void> removeWarp(String warp) {
        return this.transactionalRun(warps -> warps.remove(warp));
    }

    @Override
    public CompletableFuture<Optional<Warp>> getWarp(String name) {
        return transactionalSupply(warps -> Optional.ofNullable(this.warpConfig.warps.get(name))
            .map(warpConfigEntry -> new WarpImpl(
                name,
                warpConfigEntry.position,
                warpConfigEntry.permissions)
            ));
    }

    @Override
    public CompletableFuture<List<Warp>> getWarps() {
        return transactionalSupply(warps -> warps.entrySet().stream()
            .map(warpConfigEntry -> {
                WarpConfig.WarpConfigEntry warpContextual = warpConfigEntry.getValue();
                return new WarpImpl(warpConfigEntry.getKey(), warpContextual.position, warpContextual.permissions);
            })
            .collect(Collectors.toList()));
    }

    private void migrateWarps() {
        synchronized (READ_WRITE_LOCK) {
            if (this.locationsConfiguration.warps.isEmpty()) {
                return;
            }

            this.transactionalRun(warps -> warps.putAll(this.locationsConfiguration.warps
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                    entry -> entry.getKey(),
                    entry -> new WarpConfig.WarpConfigEntry(entry.getValue(), new ArrayList<>()))
                )
            ));

            this.locationsConfiguration.warps.clear();
            this.configurationManager.save(this.locationsConfiguration);
        }
    }

    private CompletableFuture<Void> transactionalRun(Consumer<Map<String, WarpConfig.WarpConfigEntry>> editor) {
        return transactionalSupply(warps -> {
            editor.accept(warps);
            return null;
        });
    }

    private <T> CompletableFuture<T> transactionalSupply(Function<Map<String, WarpConfig.WarpConfigEntry>, T> editor) {
        return scheduler.completeAsync(() -> {
            synchronized (READ_WRITE_LOCK) {
                Map<String, WarpConfig.WarpConfigEntry> warps = new HashMap<>(this.warpConfig.warps);
                T result = editor.apply(warps);
                this.warpConfig.warps = warps;
                this.configurationManager.save(this.warpConfig);
                return result;
            }
        });
    }

}
