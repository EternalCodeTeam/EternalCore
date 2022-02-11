/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.builders.ItemBuilder;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import panda.utilities.StringUtils;

@Section(route = "skull", aliases = "glowa")
@Permission("eternalcore.command.skull")
@UsageMessage("&8» &cPoprawne użycie &7/skull <player>")
public class SkullCommand {

    private final MessagesConfiguration messages;
    private final EternalCore eternalCore;
    private final Server server;

    public SkullCommand(EternalCore eternalCore) {
        this.eternalCore = eternalCore;
        this.messages = eternalCore.getConfigurationManager().getMessagesConfiguration();
        this.server = eternalCore.getServer();
    }

    @Execute
    @MinArgs(1)
    public void execute(Player player, String[] args) {
        this.server.getScheduler().runTaskAsynchronously(this.eternalCore, () -> {
            ItemStack item = new ItemBuilder(Material.PLAYER_HEAD).displayName(args[0]).skullOwner(args[0]).build();

            player.getInventory().addItem(item);
            player.sendMessage(ChatUtils.color(StringUtils.replace(this.messages.otherMessages.skullMessage, "{NICK}", args[0])));
        });
    }
}
