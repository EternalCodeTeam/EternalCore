package com.eternalcode.core.feature.backpack;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

public class BackpackImpl implements Backpack {

    private final UUID uuid;
    private final UUID owner;
    private final Integer slot;
    private final JSONObject backpack;

    public BackpackImpl(UUID uuid, UUID owner, Integer slot, JSONObject backpack) {
        this.uuid = uuid;
        this.owner = owner;
        this.slot = slot;
        this.backpack = backpack;
    }

    @Override
    public UUID getOwner() {
        return this.owner;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public Integer getSlot() {
        return this.slot;
    }

    @Override
    public JSONObject getJson() {
        return this.backpack;
    }

    @Override
    public HashMap<Integer, ItemStack> getBackpack() {
        return toMap(this.backpack);
    }

    private static HashMap<Integer, ItemStack> toMap(JSONObject object) {
        HashMap<Integer, ItemStack> map = new HashMap<>();

        for (Object o : object.keySet()) {
            Integer key = (int) o;
            ItemStack value = (ItemStack) object.get(key);

            map.put(key, value);
        }
        return map;
    }
}
