package com.eternalcode.core.util;

import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class ItemStackDisplayUtil {

    private ItemStackDisplayUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static ItemStack applyDisplayName(ItemStack itemStack, Component name) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) {
            return itemStack;
        }

        meta.displayName(name);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static ItemStack applyLore(ItemStack itemStack, List<Component> lore) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) {
            return itemStack;
        }

        meta.lore(lore);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static ItemStack applyDisplayNameAndLore(ItemStack itemStack, Component name, List<Component> lore) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) {
            return itemStack;
        }

        meta.displayName(name);
        meta.lore(lore);
        itemStack.setItemMeta(meta);

        return itemStack;
    }
}
