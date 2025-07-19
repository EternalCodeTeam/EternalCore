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
public class VanishMetaDataServiceImpl implements VanishMetaDataService {

    private static final String METADATA_VANISHED_KEY = "vanished";
    private final Plugin plugin;
    private final Server server;

    @Inject
    public VanishMetaDataServiceImpl(Plugin plugin) {
        this.server = plugin.getServer();
        this.plugin = plugin;
    }

    @Override
    public void addMetadata(Player player) {
        player.setMetadata(METADATA_VANISHED_KEY, new FixedMetadataValue(this.plugin, true));
    }

    @Override
    public void removeMetadata(Player player) {
        player.removeMetadata(METADATA_VANISHED_KEY, this.plugin);
    }

    @Override
    public boolean hasMetadata(UUID playerUniqueId) {
        Player player = this.server.getPlayer(playerUniqueId);
        if (player == null) {
            return false;
        }
        return this.hasMetadata(player);
    }

    @Override
    public boolean hasMetadata(Player player) {
        for (MetadataValue isVanished : player.getMetadata(METADATA_VANISHED_KEY)) {
            if (isVanished.asBoolean()) {
                return true;
            }
        }
        return false;
    }

}
