package com.eternalcode.core.feature.jail;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.UUID;

public interface JailService {
    void setupJailArea(Location jailLocation);

    void detainPlayer(Player player, @Nullable String reason, @Nullable Player detainedBy);

    void releasePlayer(Player player);

    boolean isLocationSet();
}
