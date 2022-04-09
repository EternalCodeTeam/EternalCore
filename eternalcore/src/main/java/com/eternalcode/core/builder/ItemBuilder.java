package com.eternalcode.core.builder;

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
import java.util.function.Consumer;

public class ItemBuilder {

    private final ItemStack itemStack;

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material, 1);
    }

    public ItemBuilder displayName(String name) {
        return this.editMeta(itemMeta -> itemMeta.setDisplayName(name));
    }

    public ItemBuilder lore(List<String> lore) {
        return this.editMeta(itemMeta -> itemMeta.setLore(lore));
    }

    public ItemBuilder lore(String... lore) {
        return lore(Arrays.asList(lore));
    }

    public ItemBuilder amount(int amount) {
        this.itemStack.setAmount(amount);

        return this;
    }

    public ItemBuilder itemData(short data) {
        return this.editMeta(meta -> {
            Damageable damageable = (Damageable)this.itemStack.getItemMeta();
            damageable.setDamage(data);
        });
    }

    public ItemBuilder editMeta(Consumer<ItemMeta> consumer) {
        ItemMeta meta = this.itemStack.getItemMeta();

        consumer.accept(meta);

        this.itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder skullOwner(String owner) {
        return this.editMeta(itemMeta -> {
            Validate.isTrue(itemMeta instanceof SkullMeta, "Item must be skull.");

            Damageable damageable = (Damageable) itemMeta;
            damageable.setDamage(3);

            SkullMeta skullMeta = (SkullMeta) itemMeta;
            skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(owner));
        });
    }

    public ItemStack build() {
        return this.itemStack;
    }
}

