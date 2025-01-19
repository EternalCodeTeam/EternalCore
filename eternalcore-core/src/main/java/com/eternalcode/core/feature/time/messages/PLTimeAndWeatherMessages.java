package com.eternalcode.core.feature.time.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
@Contextual
public class PLTimeAndWeatherMessages implements TimeAndWeatherMessages {
    @Description("# {WORLD} - Nazwa świata w którym zmieniono czas")
    public Notice timeSetDay = Notice.chat("<green>► <white>Ustawiono dzień w świecie <green>{WORLD}<white>!");
    public Notice timeSetNight = Notice.chat("<green>► <white>Ustawiono noc w świecie <green>{WORLD}<white>!");

    @Description({" ", "# {TIME} - Czas"})
    public Notice timeSet = Notice.chat("<green>► <white>Ustawiono czas na <green>{TIME}<white>!");
    public Notice timeAdd = Notice.chat("<green>► <white>Zmieniono czas o <green>{TIME}<white>!");

    @Description({" ", "# {WORLD} - Świat w którym ustawiono pogode"})
    public Notice weatherSetRain = Notice.chat("<green>► <white>Ustawiono deszcz w świecie <green>{WORLD}<white>!");
    public Notice weatherSetSun =
        Notice.chat("<green>► <white>Ustawiono słoneczną pogodę w świecie <green>{WORLD}<white>!");
    public Notice weatherSetThunder = Notice.chat("<green>► <white>Ustawiono burze w świecie <green>{WORLD}<white>!");
}
