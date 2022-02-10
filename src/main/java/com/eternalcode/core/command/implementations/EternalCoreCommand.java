/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Section(route = "eternalcore", aliases = { "eternal", "eternalmc", "core", "eternalplugin", "eternaltools", "eternalessentials" })
@UsageMessage("&8» &cPoprawne użycie &7/eternalcore <reload>")
@Permission("eternalcore.command.eternalcore")
public class EternalCoreCommand {

    @Execute(route = "reload")
    @Permission("eternalcore.command.reload")
    public void reload(Player sender, ConfigurationManager manager, MessagesConfiguration message) {
        manager.loadAndRenderConfigs();
        sender.sendMessage(ChatUtils.color(message.messagesSection.successfullyReloaded));
        Bukkit.getLogger().info(ChatUtils.color("Configs has ben successfuly reloaded!"));
    }
}
