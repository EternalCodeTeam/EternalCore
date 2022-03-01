package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.audience.AudiencesService;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

@Section(route = "online")
@Permission("eternalcore.command.online")
public class OnlineCommand {

    private final AudiencesService audiencesService;
    private final Server server;

    public OnlineCommand(AudiencesService audiencesService, Server server) {
        this.audiencesService = audiencesService;
        this.server = server;
    }

    @Execute
    public void execute(CommandSender sender) {
        this.audiencesService
            .notice()
            .message(messages -> messages.other().onlineMessage())
            .sender(sender)
            .placeholder("{ONLINE}", String.valueOf(this.server.getOnlinePlayers().size()))
            .send();
    }
}
