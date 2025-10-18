package com.eternalcode.core.feature.speed.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLSpeedMessages extends OkaeriConfig implements SpeedMessages {

    public Notice invalidSpeedRange = Notice.chat("<red>✘ <dark_red>Ustaw prędkość w zakresie 0-10!");
    public Notice invalidSpeedType = Notice.chat("<red>✘ <dark_red>Nieprawidłowy typ prędkości");

    @Comment("# {SPEED} - Ustawiona prędkość chodzenia lub latania")
    public Notice walkSpeedSetForYourself = Notice.chat("<green>► <white>Ustawiono prędkość chodzenia na <green>{SPEED}");
    public Notice flySpeedSetForYourself = Notice.chat("<green>► <white>Ustawiono prędkość latania na <green>{SPEED}");

    @Comment("# {SPEED} - Ustawiona prędkość")
    public Notice walkSpeedSetForTargetPlayer = Notice.chat("<green>► <white>Ustawiono prędkość chodzenia gracza <green>{PLAYER} <white>na <green>{SPEED}");
    public Notice flySpeedSetForTargetPlayer = Notice.chat("<green>► <white>Ustawiono prędkość latania gracza <green>{PLAYER} <white>na <green>{SPEED}");
}
