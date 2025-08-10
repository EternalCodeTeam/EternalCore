package com.eternalcode.core.feature.msg.toggle;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.UUID;

@DatabaseTable(tableName = "eternal_core_private_chat_state") // TODO: maybe change to "eternal_core_msg_state"?
class MsgStateTable {

    @DatabaseField(columnName = "id", id = true)
    private UUID uniqueId;

    @DatabaseField(columnName = "enabled")
    private MsgState state;

    MsgStateTable(UUID id, MsgState enabled) {
        this.uniqueId = id;
        this.state = enabled;
    }

    MsgStateTable() {}

    MsgState isEnabled() {
        return this.state;
    }
}
