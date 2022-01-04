/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import panda.std.Option;

@FunnyComponent
public final class EnderchestCommand {
    private final ConfigurationManager configurationManager;

    public EnderchestCommand(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @FunnyCommand(
        name = "enderchest",
        aliases = {"ec"},
        permission = "eternalcore.command.enderchest",
        usage = "&8» &cPoprawne użycie &7/enderchest <player>",
        acceptsExceeded = true
    )

    public void execute(Player player, String[] args) {
        MessagesConfiguration config = configurationManager.getMessagesConfiguration();
        Option.when(args.length == 1, () -> Bukkit.getPlayer(args[0]))
            .peek((other) -> {
                Inventory otherInventory = other.getEnderChest();
                player.openInventory(otherInventory);
                player.sendMessage(ChatUtils.color(config.enderchestGuiOpenPlayerMessage + other.getName()));
            }).onEmpty(() -> player.openInventory(player.getEnderChest()));
    }
}
