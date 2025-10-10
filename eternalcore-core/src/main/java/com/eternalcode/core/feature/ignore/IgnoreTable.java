package com.eternalcode.core.feature.ignore;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.UUID;

@DatabaseTable(tableName = "eternal_core_ignores")
class IgnoreTable {

    @DatabaseField(id = true)
    Long id;

    @DatabaseField(columnName = "player_id", uniqueCombo = true)
    UUID playerUuid;

    @DatabaseField(columnName = "ignored_id", uniqueCombo = true)
    UUID ignoredUuid;

    IgnoreTable() {}

    IgnoreTable(UUID playerUuid, UUID ignoredUuid) {
        this.playerUuid = playerUuid;
        this.ignoredUuid = ignoredUuid;
    }
}
