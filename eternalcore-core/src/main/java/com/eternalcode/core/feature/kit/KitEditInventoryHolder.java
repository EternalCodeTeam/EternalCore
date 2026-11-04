package com.eternalcode.core.feature.kit;

import com.cryptomorin.xseries.XMaterial;
import com.eternalcode.core.feature.kit.repository.InventoryAdapter;
import com.eternalcode.core.util.MaterialUtil;
import java.util.Arrays;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class KitEditInventoryHolder implements InventoryHolder {

    public static final int PLAYER_INVENTORY_SIZE = 41;

    private static final int SIZE = 54;
    private static final int STORAGE_SIZE = 36;
    static final int NO_PLAYER_SLOT = -1;

    private static final int HELMET_SLOT = 39;
    private static final int CHESTPLATE_SLOT = 38;
    private static final int LEGGINGS_SLOT = 37;
    private static final int BOOTS_SLOT = 36;
    private static final int OFFHAND_SLOT = 40;

    private static final int ARMOR_ROW_START = 45;
    private static final int OFFHAND_GUI_SLOT = 51;

    private static final int[] PLAYER_SLOT_BY_GUI_SLOT = createSlotMapping();
    private static final int[] GUI_SLOT_BY_PLAYER_SLOT = createReverseSlotMapping();

    private static final Material FILLER_MATERIAL = MaterialUtil.parseRequired(XMaterial.GRAY_STAINED_GLASS_PANE);
    private static final ItemStack FILLER = createFiller();

    private final Kit kit;
    private final Inventory inventory;

    public KitEditInventoryHolder(Kit kit, Component title) {
        this.kit = kit;
        this.inventory = Bukkit.createInventory(this, SIZE, title);

        this.setupBackground();
        this.loadKitItems();
    }

    private void setupBackground() {
        for (int slot = 0; slot < SIZE; slot++) {
            if (PLAYER_SLOT_BY_GUI_SLOT[slot] == NO_PLAYER_SLOT) {
                this.inventory.setItem(slot, FILLER.clone());
            }
        }
    }

    private void loadKitItems() {
        Inventory kitInventory = InventoryAdapter.convert(this.kit.inventory());

        for (int playerSlot = 0; playerSlot < PLAYER_INVENTORY_SIZE; playerSlot++) {
            int guiSlot = GUI_SLOT_BY_PLAYER_SLOT[playerSlot];
            if (guiSlot != NO_PLAYER_SLOT) {
                ItemStack item = kitInventory.getItem(playerSlot);
                if (item != null && item.getType() != Material.AIR) {
                    this.inventory.setItem(guiSlot, item.clone());
                }
            }
        }
    }

    public Kit kit() {
        return this.kit;
    }

    public int playerSlot(int guiSlot) {
        if (guiSlot < 0 || guiSlot >= SIZE) return NO_PLAYER_SLOT;
        return PLAYER_SLOT_BY_GUI_SLOT[guiSlot];
    }

    public boolean isLockedSlot(int rawSlot) {
        return rawSlot >= 0 && rawSlot < SIZE && PLAYER_SLOT_BY_GUI_SLOT[rawSlot] == NO_PLAYER_SLOT;
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
        if (meta != null) {
            meta.displayName(Component.empty());
            filler.setItemMeta(meta);
        }
        return filler;
    }
}