/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.builder.ItemBuilder;
import com.eternalcode.core.chat.audience.AudiencesService;
import com.eternalcode.core.command.argument.StringPlayerArgument;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Section(route = "skull", aliases = "glowa")
@Permission("eternalcore.command.skull")
@UsageMessage("&8» &cPoprawne użycie &7/skull <player>")
public class SkullCommand {

    private final AudiencesService audiencesService;
    private final EternalCore eternalCore;

    public SkullCommand(AudiencesService audiencesService, EternalCore eternalCore) {
        this.eternalCore = eternalCore;
        this.audiencesService = audiencesService;
    }

    @Execute
    public void execute(Player player, @Arg(0) @Handler(StringPlayerArgument.class) String name) {
        this.eternalCore.getScheduler().runTaskAsynchronously(() -> {
            ItemStack item = new ItemBuilder(Material.PLAYER_HEAD).displayName(name).skullOwner(name).build();

            player.getInventory().addItem(item);

            this.audiencesService
                .notice()
                .message(messages -> messages.other().skullMessage())
                .placeholder("{NICK}", name)
                .player(player.getUniqueId())
                .send();
        });
    }
}
