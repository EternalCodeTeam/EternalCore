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
        "# Maximum number of homes per permission group",
        "# Configure how many homes players can set based on their permissions",
        "# ",
        "# Permission format: 'permission' -> max_homes",
        "# Players with higher permissions will get the highest limit they qualify for",
        "# ",
        "# Default permissions:",
        "# - eternalcore.home.default: Basic players (1 home)",
        "# - eternalcore.home.vip: VIP players (2 homes)",
        "# - eternalcore.home.premium: Premium players (3 homes)",
        "# ",
        "# You can add custom permission groups and limits as needed",
        "# Example: 'eternalcore.home.admin' -> 999"
    })
    public Map<String, Integer> maxHomes = new LinkedHashMap<>() {
        {
            put("eternalcore.home.default", 1);
            put("eternalcore.home.vip", 2);
            put("eternalcore.home.premium", 3);
        }
    };
}
