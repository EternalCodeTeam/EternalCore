package com.eternalcode.core.feature.helpop;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.time.Duration;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class HelpOpConfig extends OkaeriConfig implements HelpOpSettings {

    @Comment("# Delay to send the next message under /helpop")
    public Duration helpOpDelay = Duration.ofSeconds(60);

    @Override
    public Duration getHelpOpDelay() {
        return this.helpOpDelay;
    }
}
