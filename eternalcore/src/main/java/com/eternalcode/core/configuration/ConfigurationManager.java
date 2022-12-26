package com.eternalcode.core.configuration;

import com.eternalcode.core.chat.notification.Notification;
import com.eternalcode.core.configuration.composer.DurationComposer;
import com.eternalcode.core.configuration.composer.NotificationComposer;
import com.eternalcode.core.language.LanguageComposer;
import com.eternalcode.core.configuration.composer.PositionComposer;
import com.eternalcode.core.language.Language;
import com.eternalcode.core.shared.Position;
import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;

import java.io.File;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class ConfigurationManager {

    private final Cdn cdn = CdnFactory
        .createYamlLike()
        .getSettings()
        .withComposer(Duration.class, new DurationComposer())
        .withComposer(Language.class, new LanguageComposer())
        .withComposer(Position.class, new PositionComposer())
        .withComposer(Notification.class, new NotificationComposer())
        .build();

    private final Set<ReloadableConfig> configs = new HashSet<>();
    private final File dataFolder;

    public ConfigurationManager(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public <T extends ReloadableConfig> T load(T config) {
        cdn.load(config.resource(this.dataFolder), config)
            .orThrow(RuntimeException::new);

        cdn.render(config, config.resource(this.dataFolder))
            .orThrow(RuntimeException::new);

        this.configs.add(config);

        return config;
    }

    public <T extends ReloadableConfig> void save(T config) {
        cdn.render(config, config.resource(this.dataFolder))
            .orThrow(RuntimeException::new);
    }

    public void reload() {
        for (ReloadableConfig config : this.configs) {
            load(config);
        }
    }

}
