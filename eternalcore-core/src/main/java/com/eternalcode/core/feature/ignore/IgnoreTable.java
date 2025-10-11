package com.eternalcode.core.feature.ignore;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.UUID;

@DatabaseTable(tableName = "eternal_core_ignores")
class IgnoreTable {

    public static final String PLAYER_ID_COLUMN = "player_id";
    public static final String IGNORED_ID_COLUMN = "ignored_id";

    @DatabaseField(id = true)
    Long id;

    @DatabaseField(columnName = PLAYER_ID_COLUMN, uniqueCombo = true)
    UUID playerUuid;

    @DatabaseField(columnName = IGNORED_ID_COLUMN, uniqueCombo = true)
    UUID ignoredUuid;

    IgnoreTable() {}

    IgnoreTable(UUID playerUuid, UUID ignoredUuid) {
        this.playerUuid = playerUuid;
        this.ignoredUuid = ignoredUuid;
    }
}
