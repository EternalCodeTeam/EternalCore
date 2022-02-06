package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.ChatUtils;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import net.dzikoysk.funnycommands.commands.CommandInfo;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import panda.std.Option;

import static com.eternalcode.core.command.Valid.when;


@FunnyComponent

public final class InventoryOpenCommand {
    private final ConfigurationManager configurationManager;

    public InventoryOpenCommand(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @FunnyCommand(
        name = "inventoryopen",
        aliases = { "io", "oi", "open" },
        permission = "eternalcore.command.inventoryopen",
        usage = "&8» &cPoprawne użycie &7/inventoryopen <player> [ec/armor]",
        acceptsExceeded = true
    )

    public void execute(Player player, String[] args, CommandInfo commandInfo) {
        when(args.length < 1, commandInfo.getUsageMessage());

        MessagesConfiguration messages = this.configurationManager.getMessagesConfiguration();
        Option<Player> playerOption = Option.of(Bukkit.getPlayer(args[0]));

        playerOption.peek(arg -> {
            if (args.length == 2) {
                switch (args[1]) {
                    case "ec" -> player.openInventory(arg.getEnderChest());
                    case "armor" -> player.openInventory(createInventory(arg));
                    default -> player.openInventory(arg.getInventory());
                }
            }else {
                player.openInventory(arg.getInventory());
            }
        }).onEmpty(() -> player.sendMessage(ChatUtils.component(messages.offlinePlayer)));
    }

    private Inventory createInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, ChatUtils.component("Armor player: "+player.getName()));

        inventory.setContents(player.getInventory().getArmorContents());

        ItemStack empty = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);

        for (int i = 4; i < 9; i++) {
            inventory.setItem(i, empty);
        }

        return inventory;
    }
}
