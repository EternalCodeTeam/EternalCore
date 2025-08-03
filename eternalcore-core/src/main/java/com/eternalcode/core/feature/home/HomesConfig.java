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
public class HomesConfig extends OkaeriConfig {
    @Comment("# Default home name")
    public String defaultHomeName = "home";

    @Comment("# Time of teleportation to homes")
    public Duration teleportTimeToHomes = Duration.ofSeconds(5);

    @Comment("# Max homes per permission")
    public Map<String, Integer> maxHomes = new LinkedHashMap<>() {
        {
            put("eternalcore.home.default", 1);
            put("eternalcore.home.vip", 2);
            put("eternalcore.home.premium", 3);
        }
    };
}
