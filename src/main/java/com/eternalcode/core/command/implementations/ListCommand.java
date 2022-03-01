package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.audience.AudiencesService;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import panda.utilities.text.Joiner;

import java.util.stream.Collectors;

@Section(route = "list")
@Permission("eternalcore.command.list")
public class ListCommand {

    private final AudiencesService audiencesService;
    private final PluginConfiguration config;
    private final Server server;

    public ListCommand(PluginConfiguration config, AudiencesService audiencesService, Server server) {
        this.config = config;
        this.audiencesService = audiencesService;
        this.server = server;
    }

    @Execute
    public void execute(CommandSender sender) {
        this.audiencesService
            .notice()
            .message(messages -> messages.other().listMessage())
            .sender(sender)
            .placeholder("{ONLINE}", String.valueOf(this.server.getOnlinePlayers().size()))
            .placeholder("{PLAYERS}", String.valueOf(
                Joiner.on(this.config.format.separator).join(
                        this.server.getOnlinePlayers()
                            .stream()
                            .map(HumanEntity::getName)
                            .collect(Collectors.toList()))))
            .send();
    }
}
