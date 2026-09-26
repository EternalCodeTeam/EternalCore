package com.eternalcode.core.litecommand.argument;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.Optional;

@Service
public class KnownPlayerResolver {

    private final Server server;

    @Inject
    public KnownPlayerResolver(Server server) {
        this.server = server;
    }

    public Optional<OfflinePlayer> resolve(String name) {
        if (name.isBlank()) {
            return Optional.empty();
        }

        Player online = this.server.getPlayerExact(name);

        if (online != null) {
            return Optional.of(online);
        }

        OfflinePlayer cached = this.server.getOfflinePlayerIfCached(name);

        if (cached == null || cached.getName() == null) {
            return Optional.empty();
        }

        return Optional.of(cached);
    }
}
