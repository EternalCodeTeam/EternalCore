package com.eternalcode.core.kit;

import org.bukkit.inventory.ItemStack;

import java.time.LocalDateTime;
import java.util.List;

public class Kit {

    private final LocalDateTime     blocker;
    private final String            permission;
    private final String            name;
    private final int               deley;
    private final List<ItemStack>   items;
    private Item                    itemKit;

    public Kit(LocalDateTime blocker, String permission, String name, int deley, List<ItemStack> items) {
        this.blocker = blocker;
        this.permission = permission;
        this.name = name;
        this.deley = deley;
        this.items = items;
    }

    public LocalDateTime getBlocker() {
        return blocker;
    }

    public String getPermission() {
        return permission;
    }

    public String getName() {
        return name;
    }

    public int getDeley() {
        return deley;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public Item getItemKit() {
        return itemKit;
    }

    private static class Item {

        private final List<String>  lore;
        private final ItemStack     icon;
        private final String        name;
        private final int           slot;
        private final Kit           kit;

        public Item(List<String> lore, ItemStack icon, String name, int slot, Kit kit) {
            this.lore = lore;
            this.icon = icon;
            this.name = name;
            this.slot = slot;
            this.kit = kit;
        }

        public List<String> getLore() {
            return lore;
        }

        public ItemStack getIcon() {
            return icon;
        }

        public String getName() {
            return name;
        }

        public int getSlot() {
            return slot;
        }

        public Kit getKit() {
            return kit;
        }
    }
}
