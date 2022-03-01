/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.audience.AudiencesService;
import com.eternalcode.core.command.argument.PlayerArgument;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.IgnoreMethod;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import panda.std.Option;

@Section(route = "clear")
@Permission("eternalcore.command.clear")
public class ClearCommand {

    private final AudiencesService audiencesService;

    public ClearCommand(AudiencesService audiencesService) {
        this.audiencesService = audiencesService;
    }

    @Execute
    public void execute(CommandSender sender, @Arg(0) @Handler(PlayerArgument.class) Option<Player> playerOption) {
        if (playerOption.isEmpty()){
            if (sender instanceof Player player){
                this.clear(player);
                return;
            }

            audiencesService.sender(sender, messages -> messages.argument().onlyPlayer());
            return;
        }

        Player player = playerOption.get();

        this.clear(player);

        audiencesService.notice()
            .message(messages -> messages.other().clearByMessage())
            .placeholder("{PLAYER}", player.getName())
            .sender(sender)
            .send();
    }

    @IgnoreMethod
    private void clear(Player player) {
        player.getInventory().setArmorContents(new ItemStack[0]);
        player.getInventory().clear();

        audiencesService.notice()
            .message(messages -> messages.other().clearMessage())
            .player(player.getUniqueId())
            .send();
    }

}
