package com.eternalcode.core.feature.time.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
@Contextual
public class ENTimeAndWeatherMessages implements TimeAndWeatherMessages {
    @Description("# {WORLD} - World name")
    public Notice timeSetDay = Notice.chat("<green>► <white>Time set to day in the <green>{WORLD}<white>!");
    public Notice timeSetNight = Notice.chat("<green>► <white>Time set to night in the <green>{WORLD}<white>!");

    @Description("# {TIME} - Changed time in ticks")
    public Notice timeSet = Notice.chat("<green>► <white>Time set to <green>{TIME}");
    public Notice timeAdd = Notice.chat("<green>► <white>Time added <green>{TIME}");

    @Description("# {WORLD} - World name")
    public Notice weatherSetRain = Notice.chat("<green>► <white>Weather set to rain in the <green>{WORLD}<white>!");
    public Notice weatherSetSun = Notice.chat("<green>► <white>Weather set to sun in the <green>{WORLD}<white>!");
    public Notice weatherSetThunder =
        Notice.chat("<green>► <white>Weather set to thunder in the <green>{WORLD}<white>!");
}
