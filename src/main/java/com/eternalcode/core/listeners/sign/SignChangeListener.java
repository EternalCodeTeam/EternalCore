package com.eternalcode.core.listeners.sign;

import com.eternalcode.core.chat.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener {

    @EventHandler
    public void onSign(SignChangeEvent event){
        Player player = event.getPlayer();
        String[] lines = event.getLines();

        if (player.hasPermission("eternalCore.sign")){
            for (int i = 0; i < lines.length; i++) {
                event.setLine(i, ChatUtils.color(event.getLine(i)));
            }
        }
    }
}
