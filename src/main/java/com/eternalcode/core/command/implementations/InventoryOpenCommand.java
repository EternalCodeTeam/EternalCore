package com.eternalcode.core.command.implementations;

import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.IgnoreMethod;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.PermissionExclude;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import panda.std.Option;

@Section(route = "inventoryopen", aliases = { "io", "oi", "open" })
@PermissionExclude("eternalcore.command.inventoryopen")
@UsageMessage("&8» &cPoprawne użycie &7/inventoryopen <ar/ec/inv> <player>")
public class InventoryOpenCommand {

    private final Server server;

    public InventoryOpenCommand(Server server) {
        this.server = server;
    }

    @Execute(route = "enderchest")
    @Permission("eternalcore.command.inventoryopen.enderchest")
    public void enderchest(Player sender, @Arg(0) Player player) {
        sender.openInventory(player.getEnderChest());
    }

    @Execute(route = "armor")
    @Permission("eternalcore.command.inventoryopen.armor")
    public void armor(Player sender, @Arg(0) Player player) {
        createInventory(player).open(sender);
    }

    @Execute(route = "iventory")
    @Permission("eternalcore.command.inventoryopen.inventory")
    public void inventory(Player sender, @Arg(0) Player player) {
        sender.openInventory(player.getInventory());
    }

    @IgnoreMethod
    private Gui createInventory(Player player) {
        Gui gui = Gui.gui().rows(1).title(ChatUtils.component("Armor player: " + player.getName())).create();

        if (player.getInventory().getHelmet() != null) {
            gui.setItem(0, new GuiItem(player.getInventory().getHelmet()));
        }

        if (player.getInventory().getChestplate() != null) {
            gui.setItem(1, new GuiItem(player.getInventory().getChestplate()));
        }

        if (player.getInventory().getLeggings() != null) {
            gui.setItem(2, new GuiItem(player.getInventory().getLeggings()));
        }

        if (player.getInventory().getBoots() != null) {
            gui.setItem(3, new GuiItem(player.getInventory().getBoots()));
        }

        GuiItem empty = new GuiItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE));

        for (int i = 4; i < 9; i++) {
            gui.setItem(i, empty);
        }

        gui.setDefaultClickAction(event -> {
            Option<ItemStack> itemStackOption = Option.of(event.getCurrentItem());

            itemStackOption.filter(itemStack -> itemStack.getType() == Material.GRAY_STAINED_GLASS_PANE).peek(itemStack -> event.setCancelled(true));
        });

        gui.setCloseGuiAction(event -> {
            Inventory inventory = event.getInventory();
            String title = event.getView().getTitle();
            String playerString = title.replace("Armor player: ", "");
            Option<Player> playerOption = Option.of(this.server.getPlayer(playerString));

            playerOption.peek(playerPeek -> {
                playerPeek.getInventory().setHelmet(inventory.getItem(0));
                playerPeek.getInventory().setChestplate(inventory.getItem(1));
                playerPeek.getInventory().setLeggings(inventory.getItem(2));
                playerPeek.getInventory().setBoots(inventory.getItem(3));
                playerPeek.updateInventory();
            });
        });


        return gui;
    }
}
