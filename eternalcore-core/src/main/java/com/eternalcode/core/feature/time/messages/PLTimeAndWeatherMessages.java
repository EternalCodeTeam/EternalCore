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
    Notice timeSetDay = Notice.chat("<color:#9d6eef>► <white>Ustawiono dzień w świecie <color:#9d6eef>{WORLD}<white>!");
    Notice timeSetNight = Notice.chat("<color:#9d6eef>► <white>Ustawiono noc w świecie <color:#9d6eef>{WORLD}<white>!");

    @Comment({" ", "# {TIME} - Czas"})
    Notice timeSet = Notice.chat("<color:#9d6eef>► <white>Ustawiono czas na <color:#9d6eef>{TIME}<white>!");
    Notice timeAdd = Notice.chat("<color:#9d6eef>► <white>Zmieniono czas o <color:#9d6eef>{TIME}<white>!");

    @Comment({" ", "# {WORLD} - Świat w którym ustawiono pogode"})
    Notice weatherSetRain = Notice.chat("<color:#9d6eef>► <white>Ustawiono deszcz w świecie <color:#9d6eef>{WORLD}<white>!");
    Notice weatherSetSun =
        Notice.chat("<color:#9d6eef>► <white>Ustawiono słoneczną pogodę w świecie <color:#9d6eef>{WORLD}<white>!");
    Notice weatherSetThunder = Notice.chat("<color:#9d6eef>► <white>Ustawiono burze w świecie <color:#9d6eef>{WORLD}<white>!");
}
