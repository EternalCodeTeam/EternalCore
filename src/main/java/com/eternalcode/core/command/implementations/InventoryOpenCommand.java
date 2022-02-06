package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.MaxArgs;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import panda.std.Option;

@Section(route = "inventoryopen", aliases = {"io", "oi", "open"})
@Permission("eternalcore.command.inventoryopen")
@UsageMessage("&8» &cPoprawne użycie &7/inventoryopen <player> [ec/armor]")
public class InventoryOpenCommand {
    private final MessagesConfiguration message;

    public InventoryOpenCommand(MessagesConfiguration message) {
        this.message = message;
    }

    @Execute
    @MinArgs(1)
    @MaxArgs(2)
    public void execute(Player player, String[] args) {
        Option<Player> playerOption = Option.of(Bukkit.getPlayer(args[0]));

        playerOption.peek(arg -> {
            if (args.length == 2) {
                switch (args[1]) {
                    case "ec" -> player.openInventory(arg.getEnderChest());
                    case "armor" -> player.openInventory(createInventory(arg));
                    default -> player.openInventory(arg.getInventory());
                }
                return;
            }

            player.openInventory(arg.getInventory());

        }).onEmpty(() -> player.sendMessage(ChatUtils.component(message.messagesSection.offlinePlayer)));
    }

    private Inventory createInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, ChatUtils.component("Armor player: " + player.getName()));

        inventory.setContents(player.getInventory().getArmorContents());

        ItemStack empty = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);

        for (int i = 4; i < 9; i++) {
            inventory.setItem(i, empty);
        }

        return inventory;
    }
}
