package com.eternalcode.core.feature.clear.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENClearMessages extends OkaeriConfig implements ClearMessages {

    Notice inventoryCleared = Notice.chat("<color:#9d6eef>► <white>Your inventory has been cleared");
    Notice targetInventoryCleared = Notice.chat("<color:#9d6eef>► <white>Player inventory: <color:#9d6eef>{PLAYER} <white>has been cleared");

}
