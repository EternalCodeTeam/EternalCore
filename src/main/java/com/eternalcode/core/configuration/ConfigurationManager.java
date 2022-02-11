/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.configuration;

import com.eternalcode.core.configuration.composers.LocationComposer;
import com.eternalcode.core.configuration.composers.StringComposer;
import lombok.Getter;
import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Location;

import java.io.File;
import java.io.Serializable;

public class ConfigurationManager {

    private final File dataFolder;
    private final Cdn cdn = CdnFactory
        .createYamlLike()
        .getSettings()
        .withComposer(Location.class, new LocationComposer())
        .withComposer(String.class, new StringComposer())
        .build();

    @Getter private final PluginConfiguration pluginConfiguration = new PluginConfiguration();
    @Getter private final MessagesConfiguration messagesConfiguration = new MessagesConfiguration();
    @Getter private final CommandsConfiguration commandsConfiguration = new CommandsConfiguration();
    @Getter private final LocationsConfiguration locationsConfiguration = new LocationsConfiguration();

    public ConfigurationManager(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public void loadAndRenderConfigs() {
        this.loadAndRender(pluginConfiguration, "config.yml");
        this.loadAndRender(messagesConfiguration, "messages.yml");
        this.loadAndRender(commandsConfiguration, "commands.yml");
        this.loadAndRender(locationsConfiguration, "locations.yml");
    }

    public <T extends Serializable> void loadAndRender(T config, String fileName) {
        Resource resource = Source.of(new File(dataFolder, fileName));
        cdn.load(resource, config)
            .orElseThrow(RuntimeException::new);

        cdn.render(config, resource);
    }

    public <T> void render(T config, String fileName){
        Resource resource = Source.of(new File(dataFolder, fileName));

        cdn.render(config, resource);
    }
}
