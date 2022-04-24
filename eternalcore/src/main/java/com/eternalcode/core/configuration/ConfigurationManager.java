package com.eternalcode.core.configuration;

import com.eternalcode.core.configuration.language.LanguageComposer;
import com.eternalcode.core.configuration.composers.LocationComposer;
import com.eternalcode.core.configuration.implementations.CommandsConfiguration;
import com.eternalcode.core.configuration.language.LanguageConfiguration;
import com.eternalcode.core.configuration.implementations.LocationsConfiguration;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.language.Language;
import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;
import org.bukkit.Location;

import java.io.File;

public class ConfigurationManager {

    private final Cdn cdn = CdnFactory
        .createYamlLike()
        .getSettings()
        .withComposer(Language.class, new LanguageComposer())
        .withComposer(Location.class, new LocationComposer())
        .build();

    private final PluginConfiguration pluginConfiguration;
    private final CommandsConfiguration commandsConfiguration;
    private final LocationsConfiguration locationsConfiguration;
    private final LanguageConfiguration languageConfiguration;

    public ConfigurationManager(File dataFolder) {
        this.pluginConfiguration = new PluginConfiguration(dataFolder, "config.yml");
        this.commandsConfiguration = new CommandsConfiguration(dataFolder, "commands.yml");
        this.locationsConfiguration = new LocationsConfiguration(dataFolder, "locations.yml");
        this.languageConfiguration = new LanguageConfiguration(dataFolder, "language.yml");
    }

    public void loadAndRenderConfigs() {
        this.loadAndRender(this.pluginConfiguration);
        this.loadAndRender(this.commandsConfiguration);
        this.loadAndRender(this.locationsConfiguration);
        this.loadAndRender(this.languageConfiguration);
    }

    public <T extends ConfigWithResource> void loadAndRender(T config) {
        this.cdn.load(config.getResource(), config)
            .orElseThrow(RuntimeException::new);

        this.cdn.render(config, config.getResource())
            .orElseThrow(RuntimeException::new);
    }

    public <T extends ConfigWithResource> void render(T config) {
        this.cdn.render(config, config.getResource())
            .orElseThrow(RuntimeException::new);
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

    public LanguageConfiguration getInventoryConfiguration() {
        return languageConfiguration;
    }
}
