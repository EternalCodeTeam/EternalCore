package com.eternalcode.core.feature.privatechat.toggle;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.UUID;

@DatabaseTable(tableName = "eternal_core_private_chat_state")
class PrivateChatStateWrapper {

    @DatabaseField(columnName = "id", id = true)
    private UUID uniqueId;

    @DatabaseField(columnName = "enabled")
    private PrivateChatState state;

    PrivateChatStateWrapper(UUID id, PrivateChatState enabled) {
        this.uniqueId = id;
        this.state = enabled;
    }

    PrivateChatStateWrapper() {}

    static PrivateChatStateWrapper from(PrivateChatToggle msgToggle) {
        return new PrivateChatStateWrapper(msgToggle.getUuid(), msgToggle.getState());
    }

    PrivateChatState isEnabled() {
        return this.state;
    }

    void setState(PrivateChatState state) {
        this.state = state;
    }
}
