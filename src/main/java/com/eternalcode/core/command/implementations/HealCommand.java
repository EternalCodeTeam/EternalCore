/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.AudiencesService;
import com.eternalcode.core.command.argument.PlayerArgument;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.IgnoreMethod;
import dev.rollczi.litecommands.annotations.MaxArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "heal")
@Permission("eternalcore.command.heal")
@UsageMessage("&8» &cPoprawne użycie &7/heal <player>")
public class HealCommand {

    private final AudiencesService audiencesService;

    public HealCommand(AudiencesService audiencesService) {
        this.audiencesService = audiencesService;
    }

    @Execute
    @MaxArgs(1)
    public void execute(CommandSender sender, @Arg(0) @Handler(PlayerArgument.class) Option<Player> playerOption) {
        if (playerOption.isEmpty()) {
            if (sender instanceof Player player) {
                healPlayer(player);
                return;
            }

            this.audiencesService.console(messages -> messages.argument().onlyPlayer());
            return;
        }

        Player player = playerOption.get();

        healPlayer(player);

        this.audiencesService
            .notice()
            .message(messages -> messages.other().healedMessage())
            .placeholder("{PLAYER}", player.getName())
            .sender(sender)
            .send();
    }

    @IgnoreMethod
    private void healPlayer(Player player) {
        player.setFoodLevel(20);
        player.setHealth(20);
        player.setFireTicks(0);
        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));

        this.audiencesService
            .notice()
            .message(messages -> messages.other().healMessage())
            .player(player.getUniqueId())
            .send();
    }
}
