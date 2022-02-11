package com.eternalcode.core.configuration;

import com.google.common.collect.ImmutableMap;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.entity.Exclude;

import java.io.Serializable;
import java.util.Map;

public class CommandsConfiguration implements Serializable {

    @Description({ "# ",
        "# This is the part of configuration file for EternalCore.",
        "# ",
        "# if you need help with the configration or have any questions related to EternalCore, join us in our Discord",
        "# ",
        "# Discord: https://dc.eternalcode.pl/",
        "# Website: https://eternalcode.pl/", " " })

    @Exclude
    public CommandsSection commandsSection = new CommandsSection();

    @Contextual
    public static class CommandsSection {
        public Map<String, String> commands = new ImmutableMap.Builder<String, String>()
            .put("{commands.adminchat}", "adminczaat")
            .build();
    }
}
