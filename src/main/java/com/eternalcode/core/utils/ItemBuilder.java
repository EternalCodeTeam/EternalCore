/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public final class ItemBuilder {

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

    public ItemBuilder lore(List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        // długie, ale przynajmniej non-deprecated :)
        itemMeta.lore((List<Component>) Component.text((Consumer<? super TextComponent.Builder>) lore));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder lore(String... lore) {
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

    public ItemBuilder skullOwner(Player owner) {
        ItemMeta meta = itemStack.getItemMeta();

        Validate.isTrue(meta instanceof SkullMeta, "Item must be skull.");

        // TODO use-non deprecated methods (see ItemMeta)
        if (itemStack.getDurability() != 3) {
            itemStack.setDurability((short) 3);
        }

        SkullMeta skullMeta = (SkullMeta) meta;
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(owner.getUniqueId()));
        itemStack.setItemMeta(skullMeta);
        return this;
    }

    public ItemStack build() {
        return new ItemStack(itemStack);
    }

}
