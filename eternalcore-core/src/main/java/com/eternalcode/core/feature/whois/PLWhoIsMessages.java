package com.eternalcode.core.feature.whois;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;

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
        "# {FOOD} - poziom najedzenia gracza"
    })
    public List<String> info = List.of(
        "<color:#9d6eef>► <white>Gracz: <color:#9d6eef>{PLAYER}",
        "<color:#9d6eef>► <white>UUID: <color:#9d6eef>{UUID}",
        "<color:#9d6eef>► <white>IP: <color:#9d6eef>{IP}",
        "<color:#9d6eef>► <white>Szybkość chodzenia: <color:#9d6eef>{WALK-SPEED}",
        "<color:#9d6eef>► <white>Szybkość latania: <color:#9d6eef>{SPEED}",
        "<color:#9d6eef>► <white>Opóźnienie: <color:#9d6eef>{PING}<white>ms",
        "<color:#9d6eef>► <white>Poziom: <color:#9d6eef>{LEVEL}",
        "<color:#9d6eef>► <white>Zdrowie: <color:#9d6eef>{HEALTH}",
        "<color:#9d6eef>► <white>Poziom najedzenia: <color:#9d6eef>{FOOD}"
    );
}
