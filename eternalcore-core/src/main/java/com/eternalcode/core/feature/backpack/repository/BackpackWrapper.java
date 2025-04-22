package com.eternalcode.core.feature.backpack.repository;


import com.eternalcode.core.feature.backpack.Backpack;
import com.eternalcode.core.feature.backpack.BackpackImpl;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.UUID;
import org.json.simple.JSONObject;

@DatabaseTable(tableName = "eternal_core_backpacks")
public class BackpackWrapper {

    @DatabaseField(columnName = "key", id = true)
    private UUID uuid;

    @DatabaseField(columnName = "owner")
    private UUID ownerUuid;

    @DatabaseField(columnName = "slot")
    private Integer slot;

    @DatabaseField(columnName = "backpack")
    private JSONObject jsonBackpack;

    public BackpackWrapper(UUID uuid, UUID ownerUuid, Integer slot, JSONObject jsonBackpack) {
        this.uuid = uuid;
        this.ownerUuid = ownerUuid;
        this.slot = slot;
        this.jsonBackpack = jsonBackpack;
    }

    public BackpackWrapper() {}

    Backpack toBackpack() {
        return new BackpackImpl(this.uuid, this.ownerUuid, this.slot, this.jsonBackpack);
    }

    static BackpackWrapper from(Backpack backpack) {
        return new BackpackWrapper(backpack.getUuid(), backpack.getOwner(), backpack.getSlot(), backpack.getJson());
    }

}
