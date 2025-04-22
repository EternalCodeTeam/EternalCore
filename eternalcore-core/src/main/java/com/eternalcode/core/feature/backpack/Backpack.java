package com.eternalcode.core.feature.backpack;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

public interface Backpack {

    UUID getOwner();

    UUID getUuid();

    Integer getSlot();

    JSONObject getJson();

    HashMap<Integer, ItemStack> getBackpack();
}
