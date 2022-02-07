/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import dev.rollczi.litecommands.platform.LiteSender;
import org.bukkit.entity.Player;

@Section(route = "whois")
@Permission("eternalcore.command.whois")
@UsageMessage("&8» &cPoprawne użycie &7/whois <player>")
public class WhoIsCommand {

    @Execute
    public void execute(LiteSender sender, @Arg(0) Player player) {
        sender.sendMessage("&8» &7Target name: &f" + player.getName());
        sender.sendMessage("&8» &7Target UUID: &f" + player.getUniqueId());
        sender.sendMessage("&8» &7Target address: &f" + player.getAddress());
        sender.sendMessage("&8» &7Target walk speed: &f" + player.getWalkSpeed());
        sender.sendMessage("&8» &7Target fly speed: &f" + player.getFlySpeed());
        sender.sendMessage("&8» &7Target ping: &f" + player.getPing());
        sender.sendMessage("&8» &7Target exp: &f" + player.getExp());
        sender.sendMessage("&8» &7Target health: &f" + player.getHealth());
        sender.sendMessage("&8» &7Target food level: &f" + player.getFoodLevel());
    }

}

