package com.eternalcode.core.feature.jail;

import org.bukkit.Location;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface JailLocationRepository {
    CompletableFuture<Optional<Location>> getJailLocation();

    void setJailLocation(Location location);

    void deleteJailLocation();
}
