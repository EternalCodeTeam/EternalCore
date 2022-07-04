package com.eternalcode.core.command.implementation.inventory;

import com.eternalcode.core.util.legacy.Legacy;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import panda.std.Option;

@Section(route = "invsee", aliases = { "oi", "inventoryopen", "open", "invsee" })
@Permission("eternalcore.invsee")
public class InventoryOpenCommand {

    private final Server server;

    public InventoryOpenCommand(Server server) {
        this.server = server;
    }

    @Execute(route = "enderchest")
    @Permission("eternalcore.invsee.enderchest")
    void enderchest(Player sender, @Arg Player player) {
        sender.openInventory(player.getEnderChest());
    }

    @Execute(route = "armor")
    @Permission("eternalcore.invsee.armor")
    void armor(Player sender, @Arg Player player) {
        createInventory(player).open(sender);
    }

    @Execute(route = "inventory")
    @Permission("eternalcore.invsee.inventory")
    void inventory(Player sender, @Arg Player player) {
        sender.openInventory(player.getInventory());
    }


    private Gui createInventory(Player player) {
        Gui gui = Gui.gui().rows(1).title(Legacy.component("Armor player: " + player.getName())).create();

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
                PlayerInventory inv = playerPeek.getInventory();

                inv.setHelmet(inventory.getItem(0));
                inv.setChestplate(inventory.getItem(1));
                inv.setLeggings(inventory.getItem(2));
                inv.setBoots(inventory.getItem(3));
                playerPeek.updateInventory();
            });
        });


        return gui;
    }
}
