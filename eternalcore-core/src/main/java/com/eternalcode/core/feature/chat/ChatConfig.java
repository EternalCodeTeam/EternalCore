package com.eternalcode.core.feature.chat;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true, chain = true)
public class ChatConfig extends OkaeriConfig implements ChatSettings {

    @Comment("# Custom message for unknown command")
    public boolean replaceStandardHelpMessage = false;

    @Comment("# Chat delay to send next message in chat")
    public Duration chatDelay = Duration.ofSeconds(5);

    @Comment({
            "# Chat cooldowns per permission group",
            "# Configure how long players must wait between messages based on their permissions",
            "# ",
            "# Permission format: 'permission' -> cooldown",
            "# Players with higher permissions will get the lowest cooldown they qualify for",
            "# ",
            "# Default permissions:",
            "# - eternalcore.chat.cooldown.default: Basic players (3s)",
            "# - eternalcore.chat.cooldown.vip: VIP players (2s)",
            "# - eternalcore.chat.cooldown.premium: Premium players (1s)",
            "# - eternalcore.chat.cooldown.bypass: Administrators (0s)",
            "# ",
            "# You can add custom permission groups and limits as needed",
            "# Example: 'eternalcore.chat.cooldown.admin' -> 0s"
    })
    public Map<String, Duration> chatCooldowns = new LinkedHashMap<>() {
        {
            put("eternalcore.chat.cooldown.default", Duration.ofSeconds(3));
            put("eternalcore.chat.cooldown.vip", Duration.ofSeconds(2));
            put("eternalcore.chat.cooldown.premium", Duration.ofSeconds(1));
            put("eternalcore.chat.cooldown.bypass", Duration.ZERO);
        }
    };

    @Comment("# Number of lines that will be cleared when using the /chat clear command")
    public int linesToClear = 256;

    @Comment("# Chat should be enabled?")
    public boolean chatEnabled = true;
}
