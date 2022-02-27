/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.IgnoreMethod;
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
        this.messages = messages;
    }

    @Execute
    @Required(1)
    public void execute(CommandSender sender, @Arg(0) Player player) {
        for (String string : this.messages.otherMessages.whoisCommand) {
            sender.sendMessage(ChatUtils.color(replacement(player, string)));
        }
    }

    @IgnoreMethod
    public String replacement(Player player, String replacement) {
        replacement = replacement.replace("{PLAYER}", player.getName());
        replacement = replacement.replace("{UUID}", player.getUniqueId().toString());
        replacement = replacement.replace("{IP}", player.getAddress().getHostString());
        replacement = replacement.replace("{WALK-SPEED}", String.valueOf(player.getWalkSpeed()));
        replacement = replacement.replace("{SPEED}", String.valueOf(player.getFlySpeed()));
        replacement = replacement.replace("{PING}", String.valueOf(player.getPing()));
        replacement = replacement.replace("{LEVEL}", String.valueOf(player.getLevel()));
        replacement = replacement.replace("{HEALTH}", String.valueOf(Math.round(player.getHealthScale())));
        replacement = replacement.replace("{FOOD}", String.valueOf(player.getFoodLevel()));

        return replacement;
    }

}
