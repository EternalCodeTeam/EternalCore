/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.audience.AudiencesService;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Required;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Section(route = "whois")
@Permission("eternalcore.command.whois")
@UsageMessage("&8» &cPoprawne użycie &7/whois <player>")
public class WhoIsCommand {

    private final AudiencesService audiencesService;

    public WhoIsCommand(AudiencesService audiencesService) {
        this.audiencesService = audiencesService;
    }

    @Execute
    @Required(1)
    public void execute(CommandSender sender, @Arg(0) Player player) {
        this.audiencesService.notice()
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{UUID}", String.valueOf(player.getUniqueId()))
            .placeholder("{IP}", player.getAddress().getHostString())
            .placeholder("{WALK-SPEED}", String.valueOf(player.getWalkSpeed()))
            .placeholder("{SPEED}", String.valueOf(player.getFlySpeed()))
            .placeholder("{PING}", String.valueOf(player.getPing()))
            .placeholder("{LEVEL}", String.valueOf(player.getLevel()))
            .placeholder("{HEALTH}", String.valueOf(Math.round(player.getHealthScale())))
            .placeholder("{FOOD}", String.valueOf(player.getFoodLevel()))
            .messages(messages -> messages.other().whoisCommand())
            .sender(sender)
            .send();
    }
}
