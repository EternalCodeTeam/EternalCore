package com.eternalcode.core.feature.jail;

import com.eternalcode.commons.bukkit.position.Position;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface JailLocationRepository {
    CompletableFuture<Optional<Position>> getJailLocation();

    void setJailLocation(Position location);

    void deleteJailLocation();
}
