package com.eternalcode.core.feature.teleport.config;

import com.eternalcode.core.feature.teleport.apiteleport.TeleportCommandSettings;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Duration;
import java.util.Map;

@Getter
@Accessors(fluent = true)
public class BukkitTeleportCommandSettings implements TeleportCommandSettings {

    public boolean useDelay;

    public Map<String, Duration> delayTiers;

    public Duration defaultDelay;

    public String bypassPermission;

    public BukkitTeleportCommandSettings(boolean useDelay, Map<String, Duration> delayTiers, Duration defaultDelay, String bypassPermission) {
        this.useDelay = useDelay;
        this.delayTiers = delayTiers;
        this.defaultDelay = defaultDelay;
        this.bypassPermission = bypassPermission;
    }
}
