package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import panda.utilities.text.Formatter;
import panda.utilities.text.Joiner;

import java.util.stream.Collectors;

@Section(route = "list")
@Permission("eternalcore.command.list")
public class ListCommand {

    private final MessagesConfiguration messages;
    private final PluginConfiguration config;
    private final Server server;

    public ListCommand(PluginConfiguration config, MessagesConfiguration messages, Server server) {
        this.config = config;
        this.messages = messages;
        this.server = server;
    }

    @Execute
    public void execute(CommandSender sender) {
        Formatter formatter = new Formatter()
            .register("{ONLINE}", this.server.getOnlinePlayers().size())
            .register("{PLAYERS}", Joiner.on(this.config.format.separator).join(this.server.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList())));

        sender.sendMessage(ChatUtils.color(formatter.format(this.messages.otherMessages.listMessage)));
    }
}
