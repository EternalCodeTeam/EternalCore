package com.eternalcode.core.warp;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementations.LocationsConfiguration;
import org.bukkit.Location;
import panda.std.Option;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class WarpConfigRepository implements WarpRepository {

    private final LocationsConfiguration locationsConfiguration;
    private final ConfigurationManager configurationManager;

    public WarpConfigRepository(ConfigurationManager configurationManager, LocationsConfiguration locationsConfiguration) {
        this.locationsConfiguration = locationsConfiguration;
        this.configurationManager = configurationManager;
    }

    @Override
    public void addWarp(Warp warp) {
        this.edit(warps -> warps.put(warp.getName(), warp.getLocation()));
        this.configurationManager.render(locationsConfiguration);
    }

    @Override
    public void removeWarp(String warp) {
        this.edit(warps -> warps.remove(warp));
        this.configurationManager.render(locationsConfiguration);
    }

    private void edit(Consumer<Map<String, Location>> editor) {
        HashMap<String, Location> warps = new HashMap<>(this.locationsConfiguration.warps);

        editor.accept(warps);
        this.locationsConfiguration.warps = warps;
    }

    @Override
    public CompletableFuture<Option<Warp>> getWarp(String name) {
        return CompletableFuture.completedFuture(Option.of(locationsConfiguration.warps.get(name)).map(location -> new Warp(name, location)));
    }

    @Override
    public CompletableFuture<List<Warp>> getWarps() {
        return CompletableFuture.completedFuture(locationsConfiguration.warps.entrySet().stream()
            .map(entry -> new Warp(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList()));
    }

}
