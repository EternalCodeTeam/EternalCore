package com.eternalcode.core.configuration;

import com.eternalcode.core.chat.notification.Notification;
import com.eternalcode.core.configuration.composer.DurationComposer;
import com.eternalcode.core.configuration.composer.NotificationComposer;
import com.eternalcode.core.configuration.implementation.PlaceholdersConfiguration;
import com.eternalcode.core.configuration.language.LanguageComposer;
import com.eternalcode.core.configuration.composer.PositionComposer;
import com.eternalcode.core.configuration.implementation.CommandsConfiguration;
import com.eternalcode.core.configuration.language.LanguageConfiguration;
import com.eternalcode.core.configuration.implementation.LocationsConfiguration;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.language.Language;
import com.eternalcode.core.shared.Position;
import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;

import java.io.File;
import java.time.Duration;

public class ConfigurationManager {

    private final Cdn cdn = CdnFactory
        .createYamlLike()
        .getSettings()
        .withComposer(Duration.class, new DurationComposer())
        .withComposer(Language.class, new LanguageComposer())
        .withComposer(Position.class, new PositionComposer())
        .withComposer(Notification.class, new NotificationComposer())
        .build();

    private final PluginConfiguration pluginConfiguration;
    private final CommandsConfiguration commandsConfiguration;
    private final LocationsConfiguration locationsConfiguration;
    private final LanguageConfiguration languageConfiguration;
    private final PlaceholdersConfiguration placeholdersConfiguration;

    public ConfigurationManager(File dataFolder) {
        this.pluginConfiguration = new PluginConfiguration(dataFolder, "config.yml");
        this.commandsConfiguration = new CommandsConfiguration(dataFolder, "commands.yml");
        this.locationsConfiguration = new LocationsConfiguration(dataFolder, "locations.yml");
        this.languageConfiguration = new LanguageConfiguration(dataFolder, "language.yml");
        this.placeholdersConfiguration = new PlaceholdersConfiguration(dataFolder, "placeholders.yml");
    }

    public void loadAndRenderConfigs() {
        this.loadAndRender(this.pluginConfiguration);
        this.loadAndRender(this.commandsConfiguration);
        this.loadAndRender(this.locationsConfiguration);
        this.loadAndRender(this.languageConfiguration);
    }

    public <T extends ConfigWithResource> void loadAndRender(T config) {
        this.cdn.load(config.getResource(), config)
            .orThrow(RuntimeException::new);

        this.cdn.render(config, config.getResource())
            .orThrow(RuntimeException::new);
    }

    public <T extends ConfigWithResource> void render(T config) {
        this.cdn.render(config, config.getResource())
            .orThrow(RuntimeException::new);
    }

    public PluginConfiguration getPluginConfiguration() {
        return this.pluginConfiguration;
    }

    public CommandsConfiguration getCommandsConfiguration() {
        return this.commandsConfiguration;
    }

    public LocationsConfiguration getLocationsConfiguration() {
        return this.locationsConfiguration;
    }

    public LanguageConfiguration getLanguageConfiguration() {
        return languageConfiguration;
    }

    public PlaceholdersConfiguration getPlaceholdersConfiguration() {
        return placeholdersConfiguration;
    }

}
