package com.eternalcode.core.configuration.implementation;

import com.eternalcode.core.configuration.AbstractConfigWithResource;
import com.google.common.collect.ImmutableMap;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Exclude;

import java.io.File;
import java.util.Map;

public class CommandsConfiguration extends AbstractConfigWithResource {

    public CommandsConfiguration(File folder, String child) {
        super(folder, child);
    }

    @Exclude
    public CommandsSection commandsSection = new CommandsSection();

    @Contextual
    public static class CommandsSection {
        public Map<String, String> commands = new ImmutableMap.Builder<String, String>()
            .put("{commands.adminchat}", "adminczaat")
            .build();
    }
}
