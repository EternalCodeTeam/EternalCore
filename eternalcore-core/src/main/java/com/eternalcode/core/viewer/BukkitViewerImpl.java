package com.eternalcode.core.viewer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

class BukkitViewerImpl implements Viewer {

    private static final BukkitViewerImpl CONSOLE = new BukkitViewerImpl(UUID.nameUUIDFromBytes("CONSOLE".getBytes()), true);

    private final UUID uuid;
    private final boolean console;

    private BukkitViewerImpl(UUID uuid, boolean console) {
        this.uuid = uuid;
        this.console = console;
    }

    static BukkitViewerImpl console() {
        return CONSOLE;
    }

    static BukkitViewerImpl player(UUID uuid) {
        return new BukkitViewerImpl(uuid, false);
    }

    @Override
    public UUID getUniqueId() {
        return this.uuid;
    }

    @Override
    public boolean isConsole() {
        return this.console;
    }

    @Override
    public String getName() {
        if (this.console) {
            return "CONSOLE";
        }

        Player player = Bukkit.getServer().getPlayer(this.uuid);

        if (player == null) {
            throw new IllegalStateException("Unknown player name");
        }
        
        return player.getName();
    }

}
