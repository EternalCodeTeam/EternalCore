/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.configuration;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.utils.ChatUtils;
import lombok.Getter;
import lombok.SneakyThrows;
import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnFactory;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Bukkit;
import panda.std.function.ThrowingFunction;

import java.io.File;
import java.io.Serializable;

public final class ConfigurationManager {

    private final EternalCore eternalCore;
    private final Cdn cdn = CdnFactory.createYamlLike();
    @Getter private PluginConfiguration pluginConfiguration;
    @Getter private MessagesConfiguration messagesConfiguration;

    public ConfigurationManager(EternalCore eternalCore) {
        this.eternalCore = eternalCore;
    }

    public void loadConfigs() {
        this.pluginConfiguration = generate(PluginConfiguration.class, "config.yml");
        this.messagesConfiguration = generate(MessagesConfiguration.class, "messages.yml");
    }

    @SneakyThrows
    public <T extends Serializable> T generate(Class<T> configurationClass, String fileName) {
        File folderPath = eternalCore.getDataFolder();
        File file = new File(folderPath, fileName);

        if (!folderPath.exists() && folderPath.mkdir()) {
            Bukkit.getLogger().warning(ChatUtils.color("Can't create configuration files, please try again!"));
        }

        Resource resource = Source.of(file);
        T load = cdn.load(resource, configurationClass)
            .orElseThrow(ThrowingFunction.identity());

        cdn.render(load, resource);
        return load;
    }
}
