package com.eternalcode.core.feature.jail;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.time.Duration;
import java.util.Set;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class JailConfig extends OkaeriConfig implements JailSettings {

    @Comment("# Default jail duration, set if no duration is specified")
    public Duration defaultJailDuration = Duration.ofMinutes(30);

    @Comment("# Command restriction type, either WHITELIST or BLACKLIST")
    public JailCommandRestrictionType restrictionType = JailCommandRestrictionType.WHITELIST;

    @Comment("# Restricted commands for jailed players")
    public Set<String> restrictedCommands = Set.of("help", "msg", "r", "tell", "me", "helpop");
}
