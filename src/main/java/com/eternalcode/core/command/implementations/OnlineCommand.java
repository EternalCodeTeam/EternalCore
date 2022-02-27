package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
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
        this.messages = messages;
        this.server = server;
    }

    @Execute
    public void execute(CommandSender commandSender) {
        commandSender.sendMessage(ChatUtils.color(
            this.messages.otherMessages.onlineMessage.replace("{ONLINE}", String.valueOf(this.server.getOnlinePlayers().size()))));
    }
}
