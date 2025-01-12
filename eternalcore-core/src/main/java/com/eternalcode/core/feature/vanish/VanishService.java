package com.eternalcode.core.feature.vanish;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.UUID;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

@Service
public class VanishService {

    private static final String METADATA_VANISHED_KEY = "vanished";
    private final Server server;

    @Inject
    public VanishService(Server server) {
        this.server = server;
    }

    public boolean isVanished(UUID playerUniqueId) {
        Player player = this.server.getPlayer(playerUniqueId);
        if (player == null) {
            return false;
        }
        return this.isVanished(player);
    }

    public boolean isVanished(Player player) {
        for (MetadataValue isVanished : player.getMetadata(METADATA_VANISHED_KEY)) {
            if (isVanished.asBoolean()) {
                return true;
            }
        }
        return false;
    }
}
