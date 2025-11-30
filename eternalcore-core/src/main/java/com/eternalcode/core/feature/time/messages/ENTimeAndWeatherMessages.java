package com.eternalcode.core.feature.time.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENTimeAndWeatherMessages extends OkaeriConfig implements TimeAndWeatherMessages {
    @Comment("# {WORLD} - World name")
    Notice timeSetDay = Notice.chat("<color:#9d6eef>► <white>Time set to day in the <color:#9d6eef>{WORLD}<white>!");
    Notice timeSetNight = Notice.chat("<color:#9d6eef>► <white>Time set to night in the <color:#9d6eef>{WORLD}<white>!");

    @Comment("# {TIME} - Changed time in ticks")
    Notice timeSet = Notice.chat("<color:#9d6eef>► <white>Time set to <color:#9d6eef>{TIME}");
    Notice timeAdd = Notice.chat("<color:#9d6eef>► <white>Time added <color:#9d6eef>{TIME}");

    @Comment("# {WORLD} - World name")
    Notice weatherSetRain = Notice.chat("<color:#9d6eef>► <white>Weather set to rain in the <color:#9d6eef>{WORLD}<white>!");
    Notice weatherSetSun = Notice.chat("<color:#9d6eef>► <white>Weather set to sun in the <color:#9d6eef>{WORLD}<white>!");
    Notice weatherSetThunder =
        Notice.chat("<color:#9d6eef>► <white>Weather set to thunder in the <color:#9d6eef>{WORLD}<white>!");
}
