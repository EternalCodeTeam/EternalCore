package com.eternalcode.core.feature.teleport.config;

import com.eternalcode.core.configuration.AbstractConfigurationFile;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import eu.okaeri.configs.annotation.Comment;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@ConfigurationFile
public class TeleportConfig extends AbstractConfigurationFile {

    @Comment({
        "Konfiguracja czasów teleportacji dla różnych komend",
        "Każda komenda może mieć własne czasy dla różnych poziomów uprawnień"
    })
    public Map<String, BukkitTeleportCommandSettings> commands = this.createDefaultCommands();

    private Map<String, BukkitTeleportCommandSettings> createDefaultCommands() {
        Map<String, BukkitTeleportCommandSettings> defaultCommands = new HashMap<>();

        defaultCommands.put("tpa", new BukkitTeleportCommandSettings(
            true,
            Map.of(
                "eternalcore.tpa.5s", Duration.ofSeconds(5),
                "eternalcore.tpa.3s", Duration.ofSeconds(3)
            ),
            Duration.ofSeconds(10),
            "eternalcore.tpa.bypass")
        );

        defaultCommands.put("tpahere", new BukkitTeleportCommandSettings(
            true,
            Map.of(
                "eternalcore.tpahere.5s", Duration.ofSeconds(5),
                "eternalcore.tpahere.3s", Duration.ofSeconds(3)
            ),
            Duration.ofSeconds(10),
            "eternalcore.tpahere.bypass")
        );

        defaultCommands.put("spawn", new BukkitTeleportCommandSettings(
            true,
            Map.of("eternalcore.spawn.3s", Duration.ofSeconds(3)),
            Duration.ofSeconds(5),
            "eternalcore.tpaccept.bypass")
        );

        return defaultCommands;
    }

    @Override
    public File getConfigFile(File dataFolder) {
        return new File(dataFolder, "teleport-times.yml");
    }
}
