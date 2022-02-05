package com.eternalcode.core.listeners.player;

import com.eternalcode.core.chat.ChatUtils;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;
import panda.std.Option;

public class PlayerCommandPreprocessListener implements Listener {

    private final ConfigurationManager configurationManager;

    public PlayerCommandPreprocessListener(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event){
        Player player = event.getPlayer();
        String command = event.getMessage().split(" ")[0];
        Option<HelpTopic> helpTopic = Option.of(Bukkit.getHelpMap().getHelpTopic(command));
        MessagesConfiguration messages = this.configurationManager.getMessagesConfiguration();


        helpTopic.onEmpty(() -> {
            event.setCancelled(true);
            player.sendMessage(ChatUtils.color(StringUtils.replace(messages.chatNoCommand, "{COMMAND}", command)));
        });
    }
}
