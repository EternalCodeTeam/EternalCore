package com.eternalcode.core.listener.sign;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener {

    private final MiniMessage miniMessage;

    public SignChangeListener(MiniMessage miniMessage) {
        this.miniMessage = miniMessage;
    }

    @EventHandler
    public void onSign(SignChangeEvent event) {
        Player player = event.getPlayer();
        String[] lines = event.getLines();

        if (player.hasPermission("eternalcore.sign")) {
            for (int i = 0; i < lines.length; i++) {
                event.line(i, miniMessage.deserialize(event.getLine(i)));
            }
        }
    }
}
