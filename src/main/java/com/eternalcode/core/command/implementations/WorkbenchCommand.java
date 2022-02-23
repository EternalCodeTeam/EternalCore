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
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "workbench")
@Permission("eternalcore.command.workbench")
public class WorkbenchCommand {

    private final MessagesConfiguration messages;

    public WorkbenchCommand(MessagesConfiguration messages) {
        this.messages = messages;
    }

    @Execute
    public void execute(CommandSender sender, @Arg(0) @Handler(PlayerArgument.class) Option<Player> playerOption) {
        if (playerOption.isEmpty()) {
            if (sender instanceof Player player) {
                player.openWorkbench(null, true);
                return;
            }

            sender.sendMessage(ChatUtils.color(this.messages.argumentSection.onlyPlayer));
            return;
        }

        Player player = playerOption.get();
        player.openWorkbench(null, true);
    }
}

