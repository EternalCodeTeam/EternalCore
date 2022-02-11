/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.configuration;

import lombok.Getter;
import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import panda.std.Result;

import java.io.File;
import java.io.Serializable;

public class ConfigurationManager {

    private final File dataFolder;
    private final Cdn cdn = CdnFactory
        .createYamlLike()
        .getSettings()
        .withComposer(String.class, text -> Result.ok("\"" + text + "\""), text -> {
            if (!text.startsWith("\"") || !text.endsWith("\"")) {
                return Result.error(new IllegalStateException("Brakuje \" w confingu!"));
            }

            return Result.ok(text.substring(1, text.length() - 1));
        })
        .build();

    @Getter private final PluginConfiguration pluginConfiguration = new PluginConfiguration();
    @Getter private final MessagesConfiguration messagesConfiguration = new MessagesConfiguration();

    public ConfigurationManager(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public void loadAndRenderConfigs() {
        this.loadAndRender(pluginConfiguration, "config.yml");
        this.loadAndRender(messagesConfiguration, "messages.yml");
    }

    public <T extends Serializable> void loadAndRender(T config, String fileName) {
        Resource resource = Source.of(new File(dataFolder, fileName));
        cdn.load(resource, config)
            .orElseThrow(RuntimeException::new);

        cdn.render(config, resource);
    }
}
