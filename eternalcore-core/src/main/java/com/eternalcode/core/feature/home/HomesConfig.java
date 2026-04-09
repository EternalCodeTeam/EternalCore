package com.eternalcode.core.feature.home;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class HomesConfig extends OkaeriConfig implements HomesSettings {

    @Comment({
        "# Default home name when no specific name is provided",
        "# This name will be used when player uses /sethome without specifying a name",
    })
    public String defaultName = "home";

    @Comment({
        "# Delay before teleportation",
        "# Time to wait before teleporting player to their home",
        "# During this time, movement or damage may cancel the teleportation",
        "# Format: Duration (e.g., 5s for 5 seconds, 1m30s for 1 minute 30 seconds)"
    })
    public Duration delay = Duration.ofSeconds(5);

    @Comment({
        "# Configure how many homes a player can set depending on their permissions.",
        "#",
        "# Permission format: 'permission.node : max_homes'",
        "# If user has multiple permissions from the list the highest number will be taken",
        "#",
        "# Fallback behavior:",
        "# - If a player does not have any of the listed permissions,",
        "# the value from 'defaultLimit' will be used.",
        "#",
        "# Example permissions:",
        "# - eternalcore.home.vip: VIP players (2 homes)",
        "# - eternalcore.home.premium: Premium players (3 homes)",
        "# How it works:",
        "# If a player has both 'eternalcore.home.vip' and 'eternalcore.home.premium',",
        "# they will be able to set 3 homes (highest value wins).",
        "# If the player does not have any permission from the list he will be able to set `defaultLimit` of homes.",
        "#",
        "# You can define additional permissions as needed.",
        "# Example: 'eternalcore.home.admin: 999'"
    })
    public Map<String, Integer> maxHomes = new LinkedHashMap<>() {
        {
            put("eternalcore.home.vip", 2);
            put("eternalcore.home.premium", 3);
        }
    };

    @Comment({
        "# Default limit of homes used when the player does not have any permission from the list above."
    })
    public Integer defaultLimit = 1;
}
