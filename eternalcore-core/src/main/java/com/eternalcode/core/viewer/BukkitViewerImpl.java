package com.eternalcode.core.viewer;

import com.eternalcode.core.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.function.Supplier;

class BukkitViewerImpl implements Viewer {

    private static final BukkitViewerImpl CONSOLE = new BukkitViewerImpl(UUID.nameUUIDFromBytes("CONSOLE".getBytes()), true, Language.DEFAULT);

    private final UUID uuid;
    private final boolean console;

    private final Supplier<Language> language;

    private BukkitViewerImpl(UUID uuid, boolean console, Language language) {
        this(uuid, console, () -> language);
    }

    private BukkitViewerImpl(UUID uuid, boolean console, Supplier<Language> language) {
        this.uuid = uuid;
        this.console = console;
        this.language = language;
    }

    public static BukkitViewerImpl console() {
        return CONSOLE;
    }

    public static BukkitViewerImpl player(UUID uuid, Language language) {
        return new BukkitViewerImpl(uuid, false, language);
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
        if (console) {
            return "CONSOLE";
        }

        Player player = Bukkit.getServer().getPlayer(uuid);

        if (player == null) {
            throw new IllegalStateException("Unknown player name");
        } else {
            return player.getName();
        }
    }

    @Override
    public Language getLanguage() {
        return this.language.get();
    }

}
