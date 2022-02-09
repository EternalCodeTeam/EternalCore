/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.helpers.builders;

import net.kyori.adventure.text.Component;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private final ItemStack itemStack;

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder(Material material) {
        itemStack = new ItemStack(material, 1);
    }

    public ItemBuilder displayName(String displayName) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(displayName));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder lore(List<Component> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.lore(lore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder lore(Component... lore) {
        return lore(Arrays.asList(lore));
    }

    public ItemBuilder itemData(short data) {
        itemStack.setDurability(data);
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder skullOwner(String owner) {
        ItemMeta meta = itemStack.getItemMeta();

        Validate.isTrue(meta instanceof SkullMeta, "Item must be skull.");

        if (itemStack.getDurability() != 3) {
            itemStack.setDurability((short) 3);
        }

        SkullMeta skullMeta = (SkullMeta) meta;
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(owner));
        itemStack.setItemMeta(skullMeta);
        return this;
    }

    public ItemStack build() {
        return new ItemStack(itemStack);
    }

}
