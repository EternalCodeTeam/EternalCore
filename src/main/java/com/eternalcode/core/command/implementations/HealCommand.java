/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.MaxArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section (route = "heal")
@Permission ("eternalcore.command.heal")
@UsageMessage ("&8» &cPoprawne użycie &7/heal <player>")
public class HealCommand {

    private final MessagesConfiguration message;

    public HealCommand (MessagesConfiguration message) {
        this.message = message;
    }

    @Execute
    @MaxArgs (1)
    public void execute (CommandSender sender, String[] args) {
        Option.when(args.length == 1, () -> Bukkit.getPlayer(args[0])).orElse(Option.of(sender).is(Player.class)).peek(player -> {
            player.setFoodLevel(20);
            player.setHealth(20);
            player.setFireTicks(0);
            player.sendMessage(ChatUtils.color(message.messagesSection.healMessage));
        }).onEmpty(() -> sender.sendMessage(ChatUtils.color(message.messagesSection.offlinePlayer)));
    }
}
