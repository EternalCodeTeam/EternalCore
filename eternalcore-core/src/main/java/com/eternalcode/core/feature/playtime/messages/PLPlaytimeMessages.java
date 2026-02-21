package com.eternalcode.core.feature.playtime.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLPlaytimeMessages extends OkaeriConfig implements PlaytimeMessages {

    Notice self = Notice.chat("<color:#9d6eef>► <white>Twój czas gry wynosi <color:#9d6eef>{PLAYTIME}<white>!");

    Notice other = Notice.chat(
            "<color:#9d6eef>► <white>Czas gry gracza <color:#9d6eef>{PLAYER} <white>wynosi <color:#9d6eef>{PLAYTIME}<white>!");
}
