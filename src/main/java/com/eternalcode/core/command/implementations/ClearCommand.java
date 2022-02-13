/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.command.argument.PlayerArgument;
import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.IgnoreMethod;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import panda.std.Option;

@Section(route = "clear")
@Permission("eternalcore.command.clear")
public class ClearCommand {

    private final MessagesConfiguration messages;

    public ClearCommand(MessagesConfiguration message) {
        this.messages = message;
    }

    @Execute
    public void execute(CommandSender sender, @Arg(0) @Handler(PlayerArgument.class) Option<Player> playerOption) {
        if (playerOption.isEmpty()){
            if (sender instanceof Player player){
                this.clear(player);
                return;
            }
            sender.sendMessage(ChatUtils.color(this.messages.argumentSection.onlyPlayer));
            return;
        }
        Player player = playerOption.get();

        this.clear(player);

        sender.sendMessage(ChatUtils.color(StringUtils.replace(this.messages.otherMessages.clearByMessage, "{PLAYER}", player.getName())));
    }

    @IgnoreMethod
    private void clear(Player player) {
        player.getInventory().setArmorContents(new ItemStack[0]);
        player.getInventory().clear();
        player.sendMessage(ChatUtils.color(this.messages.otherMessages.clearMessage));
    }

}
