package com.eternalcode.core.feature.privatechat.toggle;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.UUID;

@DatabaseTable(tableName = "eternal_core_private_chat_state")
class PrivateChatStateTable {

    @DatabaseField(columnName = "id", id = true)
    private UUID uniqueId;

    @DatabaseField(columnName = "enabled")
    private PrivateChatState state;

    PrivateChatStateTable(UUID id, PrivateChatState enabled) {
        this.uniqueId = id;
        this.state = enabled;
    }

    PrivateChatStateTable() {}

    PrivateChatState isEnabled() {
        return this.state;
    }
}
