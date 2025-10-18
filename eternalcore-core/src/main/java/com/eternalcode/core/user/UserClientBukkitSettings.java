package com.eternalcode.core.user;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.UUID;

class UserClientBukkitSettings implements UserClientSettings {

    private final Server server;
    private final UUID uuid;
    private WeakReference<Player> playerReference;

    UserClientBukkitSettings(Server server, UUID uuid) {
        this.server = server;
        this.uuid = uuid;
        this.playerReference = new WeakReference<>(server.getPlayer(uuid));
    }

    @Override
    public boolean isOnline() {
        return this.getPlayer().isPresent();
    }

    private Optional<Player> getPlayer() {
        Player player = this.playerReference.get();

        if (player == null) {
            Player playerFromServer = this.server.getPlayer(this.uuid);

            if (playerFromServer == null) {
                return Optional.empty();
            }

            this.playerReference = new WeakReference<>(playerFromServer);
            return Optional.of(playerFromServer);
        }

        return Optional.of(player);
    }

}
