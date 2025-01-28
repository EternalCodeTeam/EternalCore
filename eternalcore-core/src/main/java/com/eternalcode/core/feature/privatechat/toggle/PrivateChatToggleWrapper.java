package com.eternalcode.core.feature.privatechat.toggle;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.UUID;

@DatabaseTable(tableName = "eternal_core_private_chat_toggle")
class PrivateChatToggleWrapper {

    @DatabaseField(columnName = "id", id = true)
    private UUID uniqueId;

    @DatabaseField(columnName = "enabled")
    private PrivateChatToggleState state;

    PrivateChatToggleWrapper(UUID id, PrivateChatToggleState enabled) {
        this.uniqueId = id;
        this.state = enabled;
    }

    PrivateChatToggleWrapper() {}

    static PrivateChatToggleWrapper from(PrivateChatToggle msgToggle) {
        return new PrivateChatToggleWrapper(msgToggle.uuid, msgToggle.state);
    }

    PrivateChatToggleState isEnabled() {
        return this.state;
    }

    void setState(PrivateChatToggleState state) {
        this.state = state;
    }
}
