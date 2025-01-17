package com.eternalcode.core.feature.msgtoggle;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.UUID;

@DatabaseTable(tableName = "eternal_core_msg_toggles")
class MsgToggleWrapper {

    @DatabaseField(columnName = "id", id = true)
    private UUID uniqueId;

    @DatabaseField(columnName = "enabled")
    private boolean enabled;

    MsgToggleWrapper(UUID id, boolean enabled) {
        this.uniqueId = id;
        this.enabled = enabled;
    }

    MsgToggleWrapper() {}

    static MsgToggleWrapper from(MsgToggle msgToggle) {
        return new MsgToggleWrapper(msgToggle.uuid, msgToggle.toggle);
    }

    boolean isEnabled() {
        return this.enabled;
    }

    void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
