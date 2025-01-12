package com.eternalcode.core.feature.msgtoggle;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.UUID;

@DatabaseTable(tableName = "msgtoggle")
class MsgToggle {

    @DatabaseField(columnName = "id", id = true)
    private UUID id;

    @DatabaseField(columnName = "enabled")
    private boolean enabled;

    MsgToggle() {
    }

    MsgToggle(UUID id, boolean enabled) {
        this.id = id;
        this.enabled = enabled;
    }

}
