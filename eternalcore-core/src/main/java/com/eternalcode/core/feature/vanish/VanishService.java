package com.eternalcode.core.feature.vanish;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.Server;
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
        return Optional.ofNullable(this.server.getPlayer(playerUniqueId))
            .map(this::isVanished)
            .orElse(false);
    }

    private boolean isVanished(Player player) {
        boolean vanished = false;
        for (MetadataValue value : player.getMetadata(METADATA_VANISHED_KEY)) {
            vanished |= value.asBoolean();
        }
        return vanished;
    }
}
