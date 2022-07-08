package com.eternalcode.core.user.client;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import panda.std.Option;

import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.UUID;

public class ClientBukkitSettings implements ClientSettings {

    private final Server server;
    private final UUID uuid;
    private WeakReference<Player> player;

    public ClientBukkitSettings(Server server, UUID uuid) {
        this.server = server;
        this.uuid = uuid;
        this.player = new WeakReference<>(server.getPlayer(uuid));
    }

    private Option<Player> getPlayer() {
        Player player = this.player.get();

        if (player == null) {
            Player playerFromServer = this.server.getPlayer(uuid);

            if (playerFromServer == null) {
                return Option.none();
            }

            this.player = new WeakReference<>(playerFromServer);
            return Option.of(playerFromServer);
        }

        return Option.of(player);
    }

    private Player getPlayerOrThrow() {
        return this.getPlayer().orThrow(() -> new IllegalStateException("Player is offline"));
    }

    @Override
    public Locale getLocate() {
        return new Locale(this.getPlayerOrThrow().getLocale());
    }

    @Override
    public boolean isOnline() {
        return this.getPlayerOrThrow() != null;
    }
}
