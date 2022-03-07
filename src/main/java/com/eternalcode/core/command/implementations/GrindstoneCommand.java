/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.command.argument.PlayerArg;
import com.eternalcode.core.command.argument.PlayerArgOrSender;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "grindstone")
@UsageMessage("&8» &cPoprawne użycie &7/grindstone <player>")
@Permission("eternalcore.command.grindstone")
public class GrindstoneCommand {

    private final NoticeService noticeService;

    public GrindstoneCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    public void execute(CommandSender sender, @Arg(0) @Handler(PlayerArgOrSender.class) Player player) {
        player.openGrindstone(null, true);
    }
}

