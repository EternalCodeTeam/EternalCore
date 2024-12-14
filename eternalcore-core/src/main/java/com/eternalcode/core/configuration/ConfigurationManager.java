package com.eternalcode.core.configuration;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.core.configuration.composer.DurationComposer;
import com.eternalcode.core.configuration.composer.LanguageComposer;
import com.eternalcode.core.configuration.composer.MaterialComposer;
import com.eternalcode.core.configuration.composer.PositionComposer;
import com.eternalcode.core.configuration.composer.SetComposer;
import com.eternalcode.core.configuration.composer.SoundComposer;
import com.eternalcode.core.feature.language.Language;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.multification.cdn.MultificationNoticeCdnComposer;
import com.eternalcode.multification.notice.Notice;
import com.eternalcode.multification.notice.resolver.NoticeResolverRegistry;
import io.papermc.lib.PaperLib;
import java.io.File;
import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;
import net.dzikoysk.cdn.CdnSettings;
import net.dzikoysk.cdn.reflect.Visibility;
import org.bukkit.Material;
import org.bukkit.Sound;

@Service
public class ConfigurationManager {

    private final ConfigurationBackupService configurationBackupService;

    private final Cdn cdn;

    private final Set<ReloadableConfig> configs = new HashSet<>();
    private final File dataFolder;

    @Inject
    public ConfigurationManager(
        ConfigurationBackupService configurationBackupService,
        NoticeResolverRegistry resolverRegistry,
        File dataFolder
    ) {
        this.configurationBackupService = configurationBackupService;
        this.dataFolder = dataFolder;

        CdnSettings cdnSettings = CdnFactory
            .createYamlLike()
            .getSettings()
            .withComposer(Duration.class, new DurationComposer())
            .withComposer(Set.class, new SetComposer())
            .withComposer(Language.class, new LanguageComposer())
            .withComposer(Position.class, new PositionComposer())
            .withComposer(Notice.class, new MultificationNoticeCdnComposer(resolverRegistry))
            .withMemberResolver(Visibility.PACKAGE_PRIVATE);

        if (PaperLib.isVersion(21, 2)) { // move to xserise
            cdnSettings = cdnSettings
                .withComposer(Sound.class, new SoundComposer())
                .withComposer(Material.class, new MaterialComposer());
        }

        this.cdn = cdnSettings
            .build();
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

    public void reload() {
        this.configurationBackupService.createBackup();

        for (ReloadableConfig config : this.configs) {
            this.load(config);
        }
    }

    public Set<ReloadableConfig> getConfigs() {
        return Collections.unmodifiableSet(this.configs);
    }

}
