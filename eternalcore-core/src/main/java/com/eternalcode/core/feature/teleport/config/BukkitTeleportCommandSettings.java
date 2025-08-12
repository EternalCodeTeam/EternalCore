package com.eternalcode.core.feature.teleport.config;

import com.eternalcode.core.feature.teleport.apiteleport.TeleportCommandSettings;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.time.Duration;
import java.util.Map;

@Getter
@Accessors(fluent = true)
public class BukkitTeleportCommandSettings extends OkaeriConfig implements TeleportCommandSettings {

    public boolean useDelay;
    public Duration defaultDelay;

    public String bypassPermission;

    public Map<String, Duration> delayTiers;


    public BukkitTeleportCommandSettings(boolean useDelay, Map<String, Duration> delayTiers, Duration defaultDelay, String bypassPermission) {
        this.useDelay = useDelay;
        this.defaultDelay = defaultDelay;
        this.bypassPermission = bypassPermission;
        this.delayTiers = delayTiers;
    }
}
