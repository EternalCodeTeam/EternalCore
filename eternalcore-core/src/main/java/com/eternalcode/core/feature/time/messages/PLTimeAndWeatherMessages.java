package com.eternalcode.core.feature.time.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLTimeAndWeatherMessages extends OkaeriConfig implements TimeAndWeatherMessages {
    @Comment("# {WORLD} - Nazwa świata w którym zmieniono czas")
    public Notice timeSetDay = Notice.chat("<green>► <white>Ustawiono dzień w świecie <green>{WORLD}<white>!");
    public Notice timeSetNight = Notice.chat("<green>► <white>Ustawiono noc w świecie <green>{WORLD}<white>!");

    @Comment({" ", "# {TIME} - Czas"})
    public Notice timeSet = Notice.chat("<green>► <white>Ustawiono czas na <green>{TIME}<white>!");
    public Notice timeAdd = Notice.chat("<green>► <white>Zmieniono czas o <green>{TIME}<white>!");

    @Comment({" ", "# {WORLD} - Świat w którym ustawiono pogode"})
    public Notice weatherSetRain = Notice.chat("<green>► <white>Ustawiono deszcz w świecie <green>{WORLD}<white>!");
    public Notice weatherSetSun =
        Notice.chat("<green>► <white>Ustawiono słoneczną pogodę w świecie <green>{WORLD}<white>!");
    public Notice weatherSetThunder = Notice.chat("<green>► <white>Ustawiono burze w świecie <green>{WORLD}<white>!");
}
