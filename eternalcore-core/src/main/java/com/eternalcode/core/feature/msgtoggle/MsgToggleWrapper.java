package com.eternalcode.core.feature.msgtoggle;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.UUID;

@DatabaseTable(tableName = "msgtoggle")
class MsgToggleWrapper {

    @DatabaseField(columnName = "id", id = true)
    private UUID id;

    @DatabaseField(columnName = "enabled")
    private boolean enabled;

    MsgToggleWrapper() {
    }

    MsgToggleWrapper(UUID id, boolean enabled) {
        this.id = id;
        this.enabled = enabled;
    }

    static MsgToggleWrapper from(MsgToggleWrapper msgToggle) {
        return new MsgToggleWrapper(msgToggle.id, msgToggle.enabled);
    }

    boolean isEnabled() {
        return this.enabled;
    }

    void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
