/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.audience.AudiencesService;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@Section(route = "hat")
@Permission("eternalcore.command.hat")
@UsageMessage("&8» &cPoprawne użycie &7/hat")
public class HatCommand {

    private final AudiencesService audiencesService;

    public HatCommand(AudiencesService audiencesService) {
        this.audiencesService = audiencesService;
    }

    @Execute
    public void execute(Player player) {
        PlayerInventory playerInventory = player.getInventory();

        ItemStack itemStack = playerInventory.getHelmet();
        ItemStack handItem = playerInventory.getItem(playerInventory.getHeldItemSlot());

        if (itemStack == null || itemStack.getType() == Material.AIR) {
            playerInventory.setHelmet(handItem);
            playerInventory.remove(handItem);
            return;
        }

        this.audiencesService
            .notice()
            .player(player.getUniqueId())
            .message(messages -> messages.other().nullHatMessage())
            .send();
    }
}
