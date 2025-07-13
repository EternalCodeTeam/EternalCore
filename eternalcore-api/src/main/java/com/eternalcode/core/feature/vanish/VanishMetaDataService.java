package com.eternalcode.core.feature.vanish;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface VanishMetaDataService {

    void addMetadata(Player player);

    void removeMetadata(Player player);

    boolean hasMetadata(UUID playerUniqueId);

    boolean hasMetadata(Player player);
}
