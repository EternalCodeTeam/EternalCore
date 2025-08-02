package com.eternalcode.core.feature.afk;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.time.Duration;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class AfkConfig extends OkaeriConfig implements AfkSettings {
    @Comment({
        "# Number of interactions a player must make to have AFK status removed",
        "# This is for so that stupid miss-click doesn't disable AFK status"
    })
    public int interactionsCountDisableAfk = 20;

    @Comment("# Time before using the /afk command again")
    public Duration afkCommandDelay = Duration.ofSeconds(60);

    @Comment({
        "# Should a player be marked as AFK automatically?",
        "# If set to true, the player will be marked as AFK after a certain amount of time of inactivity",
        "# If set to false, the player will have to use the /afk command to be marked as AFK"
    })
    public boolean autoAfk = true;

    @Comment("# The amount of time a player must be inactive to be marked as AFK")
    public Duration afkInactivityTime = Duration.ofMinutes(10);

    @Comment("# Should a player be kicked from the game when marked as AFK?")
    public boolean kickOnAfk = false;

    @Override
    public Duration getAfkDelay() {
        return this.afkCommandDelay;
    }

    @Override
    public Duration getAfkInactivityTime() {
        return this.afkInactivityTime;
    }
}
