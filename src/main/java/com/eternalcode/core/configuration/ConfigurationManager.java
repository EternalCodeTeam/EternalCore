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
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.Serializable;

public class ConfigurationManager {

    private final EternalCore plugin;
    private final Cdn cdn = CdnFactory.createYamlLike();
    @Getter private PluginConfiguration pluginConfiguration;
    @Getter private MessagesConfiguration messagesConfiguration;

    public ConfigurationManager(EternalCore plugin) {
        this.plugin = plugin;
    }

    public void loadConfigs() {
        this.pluginConfiguration = generate(PluginConfiguration.class, "config.yml");
        this.messagesConfiguration = generate(MessagesConfiguration.class, "messages.yml");
    }

    @SneakyThrows
    public <T extends Serializable> T generate(Class<T> configurationClass, String fileName) {
        File folderPath = plugin.getDataFolder();
        File file = new File(folderPath, fileName);

        if (!folderPath.exists() && folderPath.mkdir()) {
            Bukkit.getConsoleSender().sendMessage(ChatUtils.color("[EternalCore] &aSuccessfully created file &7" + fileName));
        }

        T load = cdn.load(Source.of(file), configurationClass);
        cdn.render(load, file);
        return load;
    }

}
