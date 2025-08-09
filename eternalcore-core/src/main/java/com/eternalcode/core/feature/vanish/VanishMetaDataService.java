package com.eternalcode.core.feature.vanish;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.UUID;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

@Service
class VanishMetaDataService {

    private static final String METADATA_VANISHED_KEY = "vanished";
    private final Plugin plugin;
    private final Server server;

    @Inject
    VanishMetaDataService(Plugin plugin) {
        this.server = plugin.getServer();
        this.plugin = plugin;
    }

    void addMetadata(Player player) {
        player.setMetadata(METADATA_VANISHED_KEY, new FixedMetadataValue(this.plugin, true));
    }

    void removeMetadata(Player player) {
        player.removeMetadata(METADATA_VANISHED_KEY, this.plugin);
    }

    boolean hasMetadata(UUID playerUuid) {
        Player player = this.server.getPlayer(playerUuid);
        if (player == null) {
            return false;
        }
        return this.hasMetadata(player);
    }

    boolean hasMetadata(Player player) {
        for (MetadataValue isVanished : player.getMetadata(METADATA_VANISHED_KEY)) {
            if (isVanished.asBoolean()) {
                return true;
            }
        }
        return false;
    }

}
