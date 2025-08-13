package com.eternalcode.core.feature.clear.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENClearMessages extends OkaeriConfig implements ClearMessages {

    public Notice inventoryCleared = Notice.chat("<green>► <white>Your inventory has been cleared");
    public Notice targetInvenoryCleared = Notice.chat("<green>► <white>Player inventory: <green>{PLAYER} <white>has been cleared");

}
