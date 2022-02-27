/*
 * Copyright (c) 2022. EternalCode.pl
 */


package com.eternalcode.core.command.implementations;

import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.IgnoreMethod;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Section(route = "kill")
@Permission("eternalcore.command.kill")
@UsageMessage("&8» &cPoprawne użycie &7/kill <player>")
public class KillCommand {

    private final AudiencesService audiencesService;

    public KillCommand(AudiencesService audiencesService) {
        this.messages = messages;
    }

    @Execute
    public void execute(CommandSender sender, @Arg(0) @Handler(PlayerArgument.class) Option<Player> playerOption) {
        if (playerOption.isEmpty()) {
            if (sender instanceof Player player) {
                killPlayer(player);
                return;
            }
            sender.sendMessage(ChatUtils.color(this.messages.argumentSection.onlyPlayer));
            return;
        }
        Player player = playerOption.get();

        killPlayer(player);

        sender.sendMessage(ChatUtils.color(StringUtils.replace(this.messages.otherMessages.killedMessage, "{PLAYER}", player.getName())));
    }

    @IgnoreMethod
    private void killPlayer(Player player) {
        player.setHealth(0);

        player.sendMessage(ChatUtils.color(this.messages.otherMessages.killSelf));
    }

}
