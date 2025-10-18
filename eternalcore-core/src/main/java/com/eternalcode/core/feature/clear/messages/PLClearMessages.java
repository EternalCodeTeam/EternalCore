package com.eternalcode.core.feature.clear.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLClearMessages extends OkaeriConfig implements ClearMessages {

    Notice inventoryCleared = Notice.chat("<green>► <white>Twój ekwipunek został wyczyszczony!");
    Notice targetInventoryCleared = Notice.chat("<green>► <white>Ekwipunek gracza <green>{PLAYER} <white>został wyczyszczony");

}
