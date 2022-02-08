package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.IgnoreMethod;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Section(route = "inventoryopen", aliases = {"io", "oi", "open"})
@Permission("eternalcore.command.inventoryopen")
@UsageMessage("&8» &cPoprawne użycie &7/inventoryopen <ar/ec/inv> <player>")
public class InventoryOpenCommand {
    private final MessagesConfiguration message;

    public InventoryOpenCommand(MessagesConfiguration message) {
        this.message = message;
    }

    @Execute(route = "enderchest", aliases = {"ec"})
    public void enderchest(Player sender, @Arg(0) Player player) {
        sender.openInventory(player.getEnderChest());
    }

    @Execute(route = "armor", aliases = {"ar"})
    public void armor(Player sender, @Arg(0) Player player) {
        sender.openInventory(createInventory(player));
    }

    @Execute(route = "iventory", aliases = {"inv"})
    public void inventory(Player sender, @Arg(0) Player player) {
        sender.openInventory(player.getInventory());
    }


    @IgnoreMethod
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
