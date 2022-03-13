package com.eternalcode.core.builder;

import net.kyori.adventure.text.Component;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

//TODO: recode
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

    public ItemBuilder amount(int amount) {
        itemStack.setAmount(amount);

        return this;
    }

    public ItemBuilder itemData(short data) {
        this.itemStack.editMeta(meta -> {
            Damageable damageable = (Damageable) meta;

            damageable.setDamage(data);
        });

        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder skullOwner(String owner) {
        ItemMeta meta = itemStack.getItemMeta();

        Validate.isTrue(meta instanceof SkullMeta, "Item must be skull.");

        Damageable damageable = (Damageable) meta;
        damageable.setDamage(3);

        SkullMeta skullMeta = (SkullMeta) meta;
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(owner));
        itemStack.setItemMeta(skullMeta);
        return this;
    }

    public ItemStack build() {
        return new ItemStack(itemStack);
    }

}
