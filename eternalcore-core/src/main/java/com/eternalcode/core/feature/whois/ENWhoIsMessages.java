package com.eternalcode.core.feature.whois;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;

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
            "<color:#9d6eef>► <white>Target name: <color:#9d6eef>{PLAYER}",
            "<color:#9d6eef>► <white>Target UUID: <color:#9d6eef>{UUID}",
            "<color:#9d6eef>► <white>Target address: <color:#9d6eef>{IP}",
            "<color:#9d6eef>► <white>Target walk speed: <color:#9d6eef>{WALK-SPEED}",
            "<color:#9d6eef>► <white>Target fly speed: <color:#9d6eef>{SPEED}",
            "<color:#9d6eef>► <white>Target ping: <color:#9d6eef>{PING}<white>ms",
            "<color:#9d6eef>► <white>Target level: <color:#9d6eef>{LEVEL}",
            "<color:#9d6eef>► <white>Target health: <color:#9d6eef>{HEALTH}",
            "<color:#9d6eef>► <white>Target food level: <color:#9d6eef>{FOOD}",
            "<color:#9d6eef>► <white>Last seen: <green>{LAST-SEEN}",
            "<color:#9d6eef>► <white>Account created: <green>{ACCOUNT-CREATED}");
}
