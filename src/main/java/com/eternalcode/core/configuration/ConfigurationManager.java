/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.configuration;

import lombok.Getter;
import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;
import java.io.Serializable;

public final class ConfigurationManager {

    private final File dataFolder;
    private final Cdn cdn = CdnFactory.createYamlLike();

    @Getter private PluginConfiguration pluginConfiguration;
    @Getter private MessagesConfiguration messagesConfiguration;

    public ConfigurationManager(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public void saveConfigs() {
        this.save(this.pluginConfiguration, "config.yml");
        this.save(this.messagesConfiguration, "messages.yml");
    }

    public void loadConfigs() {
        this.pluginConfiguration = loadOrCreate(PluginConfiguration.class, "config.yml");
        this.messagesConfiguration = loadOrCreate(MessagesConfiguration.class, "messages.yml");
    }

    public <T extends Serializable> T loadOrCreate(Class<T> configurationClass, String fileName) {
        Resource resource = this.getResourceFromDataFolder(fileName);
        T load = cdn.load(resource, configurationClass)
            .orElseThrow(RuntimeException::new);

        cdn.render(load, resource);
        return load;
    }

    public <T extends Serializable> void save(T config, String fileName) {
        cdn.render(config, this.getResourceFromDataFolder(fileName));
    }

    private Resource getResourceFromDataFolder(String fileName) {
        return Source.of(new File(dataFolder, fileName));
    }

}
