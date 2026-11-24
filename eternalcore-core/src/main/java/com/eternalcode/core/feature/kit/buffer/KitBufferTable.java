package com.eternalcode.core.feature.kit.buffer;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.UUID;

@DatabaseTable(tableName = "eternal_core_kit_buffers")
class KitBufferTable {

    @DatabaseField(columnName = "player_id", id = true)
    private UUID playerUniqueId;

    @DatabaseField(columnName = "items", dataType = DataType.LONG_STRING)
    private String items;

    KitBufferTable() {
    }

    KitBufferTable(UUID playerUniqueId, String items) {
        this.playerUniqueId = playerUniqueId;
        this.items = items;
    }

    String getItems() {
        return this.items;
    }
}
