package com.eternalcode.core.feature.clear.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLClearMessages extends OkaeriConfig implements ClearMessages {

    Notice inventoryCleared = Notice.chat("<color:#9d6eef>► <white>Twój ekwipunek został wyczyszczony!");
    Notice targetInventoryCleared = Notice.chat("<color:#9d6eef>► <white>Ekwipunek gracza <color:#9d6eef>{PLAYER} <white>został wyczyszczony");

}
