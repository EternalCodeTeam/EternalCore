/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.utils.ChatUtils;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@FunnyComponent
public final class HatCommand {
    @FunnyCommand(
        name = "hat",
        permission = "eternalcore.command.hat",
        usage = "&8» &cPoprawne użycie &7/hat",
        acceptsExceeded = true
    )
    public void execute(Player player) {
        PlayerInventory playerInventory = player.getInventory();
        ItemStack itemStack = playerInventory.getHelmet();
        ItemStack handItem = playerInventory.getItem(playerInventory.getHeldItemSlot());
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            playerInventory.setHelmet(handItem);
            return;
        }
        player.sendMessage(ChatUtils.color("&8» &cNie możesz użyć komendy /hat, najpierw ściągnij to co masz na głowie."));
    }
}
