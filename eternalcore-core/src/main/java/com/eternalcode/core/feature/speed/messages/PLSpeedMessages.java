package com.eternalcode.core.feature.speed.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLSpeedMessages extends OkaeriConfig implements SpeedMessages {

    Notice invalidSpeedRange = Notice.chat("<red>✘ <dark_red>Ustaw prędkość w zakresie 0-10!");
    Notice invalidSpeedType = Notice.chat("<red>✘ <dark_red>Nieprawidłowy typ prędkości");
    Notice walkSpeedSet = Notice.chat("<color:#9d6eef>► <white>Ustawiono prędkość chodzenia na <color:#9d6eef>{SPEED}");
    Notice flySpeedSet = Notice.chat("<color:#9d6eef>► <white>Ustawiono prędkość latania na <color:#9d6eef>{SPEED}");
    Notice walkSpeedSetForTargetPlayer = Notice.chat("<color:#9d6eef>► <white>Ustawiono prędkość chodzenia gracza <color:#9d6eef>{PLAYER} <white>na <color:#9d6eef>{SPEED}");
    Notice flySpeedSetForTargetPlayer = Notice.chat("<color:#9d6eef>► <white>Ustawiono prędkość latania gracza <color:#9d6eef>{PLAYER} <white>na <color:#9d6eef>{SPEED}");
}
