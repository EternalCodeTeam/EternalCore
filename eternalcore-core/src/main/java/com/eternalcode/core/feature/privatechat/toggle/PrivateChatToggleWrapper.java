package com.eternalcode.core.feature.privatechat.toggle;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.UUID;

@DatabaseTable(tableName = "eternal_core_private_chat_toggle")
class PrivateChatToggleWrapper {

    @DatabaseField(columnName = "id", id = true)
    private UUID uniqueId;

    @DatabaseField(columnName = "enabled")
    private PrivateChatState state;

    PrivateChatToggleWrapper(UUID id, PrivateChatState enabled) {
        this.uniqueId = id;
        this.state = enabled;
    }

    PrivateChatToggleWrapper() {}

    static PrivateChatToggleWrapper from(PrivateChatToggle msgToggle) {
        return new PrivateChatToggleWrapper(msgToggle.getUuid(), msgToggle.getState());
    }

    PrivateChatState isEnabled() {
        return this.state;
    }

    void setState(PrivateChatState state) {
        this.state = state;
    }
}
