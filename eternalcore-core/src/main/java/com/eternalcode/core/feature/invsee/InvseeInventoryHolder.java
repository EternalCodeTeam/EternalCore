package com.eternalcode.core.feature.invsee;

import com.cryptomorin.xseries.XMaterial;
import com.eternalcode.core.util.MaterialUtil;
import java.util.Arrays;
import java.util.List;
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
 * Layout:
 * <pre>
 * rows 1-4 | player's inventory (player slots 0-35)
 * row 5    | slot indicators above the armor and offhand slots, filler
 * row 6    | helmet, chestplate, leggings, boots, filler, filler, offhand, filler, filler
 * </pre>
 */
public class InvseeInventoryHolder implements InventoryHolder {

    public static final int PLAYER_INVENTORY_SIZE = 41;

    private static final int SIZE = 54;
    private static final int STORAGE_SIZE = 36;
    private static final int NO_PLAYER_SLOT = -1;

    private static final int HELMET_SLOT = 39;
    private static final int CHESTPLATE_SLOT = 38;
    private static final int LEGGINGS_SLOT = 37;
    private static final int BOOTS_SLOT = 36;
    private static final int OFFHAND_SLOT = 40;

    private static final int[] INDICATOR_SLOTS = { 36, 37, 38, 39, 42 };
    private static final int ARMOR_ROW_START = 45;
    private static final int OFFHAND_GUI_SLOT = 51;

    private static final int[] PLAYER_SLOT_BY_GUI_SLOT = createSlotMapping();
    private static final int[] GUI_SLOT_BY_PLAYER_SLOT = createReverseSlotMapping();

    private static final Material FILLER_MATERIAL = MaterialUtil.parseRequired(XMaterial.GRAY_STAINED_GLASS_PANE);
    private static final ItemStack FILLER = createFiller();

    private final UUID target;
    private final boolean editable;
    private final List<ItemStack> slotIndicators;
    private final Inventory inventory;

    InvseeInventoryHolder(Server server, UUID target, boolean editable, Component title, List<ItemStack> slotIndicators) {
        this.target = target;
        this.editable = editable;
        this.slotIndicators = List.copyOf(slotIndicators);
        this.inventory = server.createInventory(this, SIZE, title);

        for (int slot = 0; slot < SIZE; slot++) {
            if (PLAYER_SLOT_BY_GUI_SLOT[slot] == NO_PLAYER_SLOT) {
                this.inventory.setItem(slot, FILLER.clone());
            }
        }

        for (int index = 0; index < this.slotIndicators.size(); index++) {
            this.inventory.setItem(INDICATOR_SLOTS[index], this.slotIndicators.get(index).clone());
        }
    }

    public UUID target() {
        return this.target;
    }

    public boolean isEditable() {
        return this.editable;
    }

    public int guiSlot(int playerSlot) {
        return GUI_SLOT_BY_PLAYER_SLOT[playerSlot];
    }

    public boolean isLockedSlot(int rawSlot) {
        return rawSlot >= 0 && rawSlot < SIZE && PLAYER_SLOT_BY_GUI_SLOT[rawSlot] == NO_PLAYER_SLOT;
    }

    public boolean isDecoration(ItemStack item) {
        if (item == null) {
            return false;
        }

        return item.isSimilar(FILLER) || this.slotIndicators.stream().anyMatch(item::isSimilar);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    private static int[] createSlotMapping() {
        int[] mapping = new int[SIZE];
        Arrays.fill(mapping, NO_PLAYER_SLOT);

        for (int slot = 0; slot < STORAGE_SIZE; slot++) {
            mapping[slot] = slot;
        }

        mapping[ARMOR_ROW_START] = HELMET_SLOT;
        mapping[ARMOR_ROW_START + 1] = CHESTPLATE_SLOT;
        mapping[ARMOR_ROW_START + 2] = LEGGINGS_SLOT;
        mapping[ARMOR_ROW_START + 3] = BOOTS_SLOT;
        mapping[OFFHAND_GUI_SLOT] = OFFHAND_SLOT;

        return mapping;
    }

    private static int[] createReverseSlotMapping() {
        int[] mapping = new int[PLAYER_INVENTORY_SIZE];

        for (int guiSlot = 0; guiSlot < SIZE; guiSlot++) {
            int playerSlot = PLAYER_SLOT_BY_GUI_SLOT[guiSlot];

            if (playerSlot != NO_PLAYER_SLOT) {
                mapping[playerSlot] = guiSlot;
            }
        }

        return mapping;
    }

    private static ItemStack createFiller() {
        ItemStack filler = new ItemStack(FILLER_MATERIAL);
        ItemMeta meta = filler.getItemMeta();
        meta.displayName(Component.empty());
        filler.setItemMeta(meta);
        return filler;
    }
}
