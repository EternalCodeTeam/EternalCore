/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.configuration;

import com.eternalcode.core.configuration.composers.LocationComposer;
import com.eternalcode.core.configuration.composers.StringComposer;
import com.eternalcode.core.configuration.implementations.CommandsConfiguration;
import com.eternalcode.core.configuration.implementations.LocationsConfiguration;
import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import lombok.Getter;
import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Location;

import java.io.File;

public class ConfigurationManager {

    private final File dataFolder;
    private final Cdn cdn = CdnFactory
        .createYamlLike()
        .getSettings()
        .withComposer(Location.class, new LocationComposer())
        .withComposer(String.class, new StringComposer())
        .build();

    @Getter private final PluginConfiguration pluginConfiguration;
    @Getter private final MessagesConfiguration messagesConfiguration;
    @Getter private final CommandsConfiguration commandsConfiguration;
    @Getter private final LocationsConfiguration locationsConfiguration;

    public ConfigurationManager(File dataFolder) {
        this.dataFolder = dataFolder;
        this.pluginConfiguration = new PluginConfiguration(dataFolder, "config.yml");
        this.messagesConfiguration = new MessagesConfiguration(dataFolder, "messages.yml");
        this.commandsConfiguration = new CommandsConfiguration(dataFolder, "commands.yml");
        this.locationsConfiguration = new LocationsConfiguration(dataFolder, "locations.yml");
    }

    public void loadAndRenderConfigs() {
        this.loadAndRender(pluginConfiguration);
        this.loadAndRender(messagesConfiguration);
        this.loadAndRender(commandsConfiguration);
        this.loadAndRender(locationsConfiguration);
    }

    public <T extends ConfigWithResource> void loadAndRender(T config) {
        cdn.load(config.getResource(), config)
            .orElseThrow(RuntimeException::new);

        cdn.render(config, config.getResource())
            .orElseThrow(RuntimeException::new);
    }

    public <T extends ConfigWithResource> void render(T config) {
        cdn.render(config, config.getResource())
            .orElseThrow(RuntimeException::new);
    }

}
