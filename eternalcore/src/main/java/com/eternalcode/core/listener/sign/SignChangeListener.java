package com.eternalcode.core.listener.sign;

import com.eternalcode.core.utils.ChatUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
                event.setLine(i, miniMessage.deserialize(event.getLine(i)));
            }
        }
    }
}
