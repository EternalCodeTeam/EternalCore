package com.eternalcode.core.feature.warp;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.shared.Position;
import panda.std.Option;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
class WarpConfigRepository implements WarpRepository {

    private final LocationsConfiguration locationsConfiguration;
    private final ConfigurationManager configurationManager;

    @Inject
    WarpConfigRepository(ConfigurationManager configurationManager, LocationsConfiguration locationsConfiguration) {
        this.locationsConfiguration = locationsConfiguration;
        this.configurationManager = configurationManager;
    }

    @Override
    public void addWarp(Warp warp) {
        this.edit(warps -> warps.put(warp.getName(), warp.getPosition()));
    }

    @Override
    public void removeWarp(String warp) {
        this.edit(warps -> warps.remove(warp));
    }

    private void edit(Consumer<Map<String, Position>> editor) {
        HashMap<String, Position> warps = new HashMap<>(this.locationsConfiguration.warps);

        editor.accept(warps);

        this.locationsConfiguration.warps = warps;

        this.configurationManager.save(this.locationsConfiguration);
    }

    @Override
    public CompletableFuture<Option<Warp>> getWarp(String name) {
        return CompletableFuture.completedFuture(Option.of(this.locationsConfiguration.warps.get(name)).map(location -> new Warp(name, location)));
    }

    @Override
    public CompletableFuture<List<Warp>> getWarps() {
        return CompletableFuture.completedFuture(this.locationsConfiguration.warps.entrySet().stream()
            .map(entry -> new Warp(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList()));
    }

}
