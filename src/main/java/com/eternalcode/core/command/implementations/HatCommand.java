/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@FunnyComponent
public final class HatCommand {

    private final ConfigurationManager configurationManager;

    public HatCommand(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @FunnyCommand(
        name = "hat",
        permission = "eternalcore.command.hat",
        usage = "&8» &cPoprawne użycie &7/hat",
        acceptsExceeded = true
    )

    public void execute(Player player) {
        MessagesConfiguration config = configurationManager.getMessagesConfiguration();
        PlayerInventory playerInventory = player.getInventory();

        ItemStack itemStack = playerInventory.getHelmet();
        ItemStack handItem = playerInventory.getItem(playerInventory.getHeldItemSlot());

        if (itemStack == null || itemStack.getType() == Material.AIR) {
            playerInventory.setHelmet(handItem);
            playerInventory.remove(handItem);
            return;
        }

        player.sendMessage(ChatUtils.color(config.nullHatMessage));
    }
}
