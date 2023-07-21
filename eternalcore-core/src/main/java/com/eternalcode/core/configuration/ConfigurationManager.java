package com.eternalcode.core.configuration;

import com.eternalcode.core.configuration.composer.DurationComposer;
import com.eternalcode.core.configuration.composer.LanguageComposer;
import com.eternalcode.core.configuration.composer.MaterialComposer;
import com.eternalcode.core.configuration.composer.PositionComposer;
import com.eternalcode.core.language.Language;
import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.notice.NoticeComposer;
import com.eternalcode.core.shared.Position;
import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;
import net.dzikoysk.cdn.reflect.Visibility;
import org.bukkit.Material;

import java.io.File;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class ConfigurationManager {

    private final ConfigurationBackupService configurationBackupService;

    private final Cdn cdn = CdnFactory
        .createYamlLike()
        .getSettings()
        .withComposer(Duration.class, new DurationComposer())
        .withComposer(Language.class, new LanguageComposer())
        .withComposer(Position.class, new PositionComposer())
        .withComposer(Notice.class, new NoticeComposer())
        .withComposer(Material.class, new MaterialComposer())
        .withMemberResolver(Visibility.PACKAGE_PRIVATE)
        .build();

    private final Set<ReloadableConfig> configs = new HashSet<>();
    private final File dataFolder;

    public ConfigurationManager(ConfigurationBackupService configurationBackupService, File dataFolder) {
        this.configurationBackupService = configurationBackupService;
        this.dataFolder = dataFolder;
    }

    public <T extends ReloadableConfig> T load(T config) {
        this.cdn.load(config.resource(this.dataFolder), config)
            .orThrow(RuntimeException::new);

        this.cdn.render(config, config.resource(this.dataFolder))
            .orThrow(RuntimeException::new);

        this.configs.add(config);

        return config;
    }

    public <T extends ReloadableConfig> void save(T config) {
        this.cdn.render(config, config.resource(this.dataFolder))
            .orThrow(RuntimeException::new);
    }

    public void saveAll() {
        for (ReloadableConfig config : this.configs) {
            this.save(config);
        }
    }

    public void reload() {
        this.configurationBackupService.createBackup();

        for (ReloadableConfig config : this.configs) {
            this.load(config);
        }
    }
}
