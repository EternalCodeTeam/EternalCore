package com.eternalcode.core.feature.invsee;

import com.cryptomorin.xseries.XMaterial;
import com.eternalcode.core.util.MaterialUtil;
import java.util.UUID;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

/**
 * Inventory showing a copy of the inspected player's inventory.
 * Slots {@code 0 - contentSize} mirror the player's inventory slots, the remaining ones are blocked by a filler.
 */
public class InvseeInventoryHolder implements InventoryHolder {

    private static final int SIZE = 45;
    private static final Material FILLER_MATERIAL = MaterialUtil.parseRequired(XMaterial.GRAY_STAINED_GLASS_PANE);
    private static final ItemStack FILLER = createFiller();

    private final UUID target;
    private final boolean editable;
    private final int contentSize;
    private final Inventory inventory;

    InvseeInventoryHolder(Server server, UUID target, boolean editable, int contentSize, Component title) {
        this.target = target;
        this.editable = editable;
        this.contentSize = contentSize;
        this.inventory = server.createInventory(this, SIZE, title);

        for (int slot = contentSize; slot < SIZE; slot++) {
            this.inventory.setItem(slot, FILLER.clone());
        }
    }

    public UUID target() {
        return this.target;
    }

    public boolean isEditable() {
        return this.editable;
    }

    public int contentSize() {
        return this.contentSize;
    }

    public boolean isFillerSlot(int rawSlot) {
        return rawSlot >= this.contentSize && rawSlot < SIZE;
    }

    public static boolean isFiller(ItemStack item) {
        return item != null && item.isSimilar(FILLER);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    private static ItemStack createFiller() {
        ItemStack filler = new ItemStack(FILLER_MATERIAL);
        ItemMeta meta = filler.getItemMeta();
        meta.displayName(Component.empty());
        filler.setItemMeta(meta);
        return filler;
    }
}
