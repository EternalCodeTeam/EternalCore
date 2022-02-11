/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
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

    private final MessagesConfiguration message;

    public HatCommand(MessagesConfiguration message) {
        this.message = message;
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

        player.sendMessage(ChatUtils.color(this.message.otherMessages.nullHatMessage));
    }
}
