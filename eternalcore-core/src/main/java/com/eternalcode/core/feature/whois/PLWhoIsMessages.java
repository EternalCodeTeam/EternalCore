package com.eternalcode.core.feature.whois;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;
import java.util.List;

@Getter
@Accessors(fluent = true)
public class PLWhoIsMessages extends OkaeriConfig implements WhoIsMessages {

    @Comment({
            " ",
            "# {PLAYER} - nazwa gracza",
            "# {UUID} - UUID gracza",
            "# {IP} - IP gracza",
            "# {WALK-SPEED} - prędkość chodzenia gracza",
            "# {SPEED} - prędkość latania gracza",
            "# {PING} - ping gracza",
            "# {LEVEL} - poziom gracza",
            "# {HEALTH} - zdrowie gracza",
            "# {FOOD} - poziom najedzenia gracza",
            "# {LAST-SEEN} - Ostatnio widziany",
            "# {ACCOUNT-CREATED} - Data utworzenia konta"
    })
    public List<String> info = List.of(
            "<green>► <white>Gracz: <green>{PLAYER}",
            "<green>► <white>UUID: <green>{UUID}",
            "<green>► <white>IP: <green>{IP}",
            "<green>► <white>Szybkość chodzenia: <green>{WALK-SPEED}",
            "<green>► <white>Szybkość latania: <green>{SPEED}",
            "<green>► <white>Opóźnienie: <green>{PING}<white>ms",
            "<green>► <white>Poziom: <green>{LEVEL}",
            "<green>► <white>Zdrowie: <green>{HEALTH}",
            "<green>► <white>Poziom najedzenia: <green>{FOOD}",
            "<green>► <white>Ostatnio widziany: <green>{LAST-SEEN}",
            "<green>► <white>Data utworzenia konta: <green>{ACCOUNT-CREATED}");
}
