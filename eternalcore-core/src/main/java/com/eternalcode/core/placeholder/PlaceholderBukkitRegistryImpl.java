package com.eternalcode.core.placeholder;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.viewer.Viewer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

@Service
public class PlaceholderBukkitRegistryImpl implements PlaceholderRegistry {

    private final Server server;
    private final Set<PlaceholderReplacer> replacers = new HashSet<>();
    private final Set<PlayerPlaceholderReplacer> replacerPlayers = new HashSet<>();

    @Inject
    public PlaceholderBukkitRegistryImpl(Server server) {
        this.server = server;
    }

    @Override
    public void registerPlaceholderReplacer(PlaceholderReplacer stack) {
        this.replacers.add(stack);
    }

    @Override
    public void registerPlayerPlaceholderReplacer(PlayerPlaceholderReplacer stack) {
        this.replacerPlayers.add(stack);
    }

    @Override
    public String format(String text) {
        for (PlaceholderReplacer replacer : this.replacers) {
            text = replacer.apply(text);
        }

        return text;
    }

    @Override
    public String format(String text, Viewer target) {
        if (!target.isConsole()) {
            Player playerTarget = this.server.getPlayer(target.getUniqueId());

            if (playerTarget != null) {
                for (PlayerPlaceholderReplacer replacer : this.replacerPlayers) {
                    text = replacer.apply(text, playerTarget);
                }
            }
        }

        return this.format(text);
    }

}
