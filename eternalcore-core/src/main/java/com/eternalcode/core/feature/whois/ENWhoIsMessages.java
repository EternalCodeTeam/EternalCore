package com.eternalcode.core.feature.whois;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;
import java.util.List;

@Getter
@Accessors(fluent = true)
public class ENWhoIsMessages extends OkaeriConfig implements WhoIsMessages {

    @Comment({
            " ",
            "# {PLAYER} - Player name",
            "# {UUID} - Player UUID",
            "# {IP} - Player IP",
            "# {WALK-SPEED} - Player walk speed",
            "# {SPEED} - Player fly speed",
            "# {PING} - Player ping",
            "# {LEVEL} - Player level",
            "# {HEALTH} - Player health",
            "# {FOOD} - Player food level",
            "# {LAST-SEEN} - Player last seen",
            "# {ACCOUNT-CREATED} - Player account created"
    })
    public List<String> info = List.of(
            "<green>► <white>Target name: <green>{PLAYER}",
            "<green>► <white>Target UUID: <green>{UUID}",
            "<green>► <white>Target address: <green>{IP}",
            "<green>► <white>Target walk speed: <green>{WALK-SPEED}",
            "<green>► <white>Target fly speed: <green>{SPEED}",
            "<green>► <white>Target ping: <green>{PING}<white>ms",
            "<green>► <white>Target level: <green>{LEVEL}",
            "<green>► <white>Target health: <green>{HEALTH}",
            "<green>► <white>Target food level: <green>{FOOD}",
            "<green>► <white>Last seen: <green>{LAST-SEEN}",
            "<green>► <white>Account created: <green>{ACCOUNT-CREATED}");
}
