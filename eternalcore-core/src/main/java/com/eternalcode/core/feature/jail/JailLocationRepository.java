package com.eternalcode.core.feature.jail;

import org.bukkit.Location;
import panda.std.reactive.Completable;

import java.util.Optional;

public interface JailLocationRepository {
    Completable<Optional<Location>> getJailLocation();

    void setJailLocation(Location location);

    void deleteJailLocation();
}
