package com.eternalcode.core.feature.jail;


import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;

public interface PrisonerService {

    void detainPlayer(Player player, Player detainedBy, @Nullable Duration duration);

    void releasePlayer(Player player, @Nullable Player releasedBy);

    void releaseAllPlayers(Player player);

    void listJailedPlayers(Player player);

    boolean isAllowedCommand(String command);

    boolean isPlayerJailed(UUID player);

    Map<UUID, Prisoner> getJailedPlayers();
}
