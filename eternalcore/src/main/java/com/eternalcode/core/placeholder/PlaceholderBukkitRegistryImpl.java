package com.eternalcode.core.placeholder;

import com.eternalcode.core.viewer.Viewer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class PlaceholderBukkitRegistryImpl implements PlaceholderRegistry {

    private final Server server;
    private final Set<PlaceholderReplacer> replacers = new HashSet<>();
    private final Set<PlayerPlaceholderReplacer> replacerPlayers = new HashSet<>();

    public PlaceholderBukkitRegistryImpl(Server server) {
        this.server = server;
    }

    public void registerPlaceholderReplacer(PlaceholderReplacer stack) {
        this.replacers.add(stack);
    }

    public void registerPlayerPlaceholderReplacer(PlayerPlaceholderReplacer stack) {
        this.replacerPlayers.add(stack);
    }

    public String format(String text) {
        for (PlaceholderReplacer replacer : replacers) {
            text = replacer.apply(text);
        }

        return text;
    }

    public String format(String text, Viewer target) {
        if (!target.isConsole()) {
            Player playerTarget = server.getPlayer(target.getUniqueId());

            if (playerTarget != null) {
                for (PlayerPlaceholderReplacer replacer : replacerPlayers) {
                    text = replacer.apply(text, playerTarget);
                }
            }
        }

        return this.format(text);
    }

}
