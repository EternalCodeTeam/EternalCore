package com.eternalcode.core.configuration;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.core.configuration.composer.DurationComposer;
import com.eternalcode.core.configuration.composer.LanguageComposer;
import com.eternalcode.core.configuration.composer.PositionComposer;
import com.eternalcode.core.configuration.composer.SetComposer;
import com.eternalcode.core.translation.Language;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.publish.Publisher;
import com.eternalcode.multification.cdn.MultificationNoticeCdnComposer;
import com.eternalcode.multification.notice.Notice;
import com.eternalcode.multification.notice.resolver.NoticeResolverRegistry;
import java.io.File;
import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;
import net.dzikoysk.cdn.CdnSettings;
import net.dzikoysk.cdn.reflect.Visibility;

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
        Publisher publisher,
        File dataFolder
    ) {
        this.configurationBackupService = configurationBackupService;
        this.dataFolder = dataFolder;
        this.cdn = createCdn(publisher, resolverRegistry);
    }

    private static Cdn createCdn(Publisher publisher, NoticeResolverRegistry resolverRegistry) {
        CdnSettings cdnSettings = CdnFactory
            .createYamlLike()
            .getSettings()
            .withComposer(Duration.class, new DurationComposer())
            .withComposer(Set.class, new SetComposer())
            .withComposer(Language.class, new LanguageComposer())
            .withComposer(Position.class, new PositionComposer())
            .withComposer(Notice.class, new MultificationNoticeCdnComposer(resolverRegistry))
            .withMemberResolver(Visibility.PACKAGE_PRIVATE);

        ConfigurationSettingsSetupEvent event = publisher.publish(new ConfigurationSettingsSetupEvent(cdnSettings));

        return event.getSettings()
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
