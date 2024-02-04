package com.eternalcode.example.feature.catboy;

import com.eternalcode.core.feature.catboy.CatboyService;
import com.eternalcode.core.feature.catboy.event.CatboySwitchEvent;
import org.bukkit.Tag;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CatBoyListener implements Listener {

    private final CatboyService catboyService;

    public CatBoyListener(CatboyService catboyService) {
        this.catboyService = catboyService;
    }

    @EventHandler
    public void onCatboyEvent(CatboySwitchEvent event) {
        boolean catboy = event.isCatboy();

        String message = "You are %s a catboy, executed via eternalcore developer api bridge!";
        event.getPlayer().sendMessage(String.format(message, catboy ? "now" : "no longer"));
    }

    /**
     * This method marks the player as a catboy if they right-click a cat with a fish.
     **/
    @EventHandler
    public void onCatboyEvent(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Cat cat)) {
            return;
        }

        Player player = event.getPlayer();

        PlayerInventory inventory = player.getInventory();
        ItemStack itemInMainHand = inventory.getItemInMainHand();

        boolean tagged = Tag.ITEMS_FISHES.isTagged(itemInMainHand.getType());
        if (tagged) {
            this.catboyService.markAsCatboy(player, cat.getCatType());
        }

        cat.remove();
    }

}
