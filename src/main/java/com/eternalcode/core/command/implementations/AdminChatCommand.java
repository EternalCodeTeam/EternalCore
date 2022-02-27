package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.audience.AudiencesService;
import com.eternalcode.core.chat.audience.Notice;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Joiner;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Section(route = "adminchat", aliases = "ac")
@Permission("eternalcore.command.adminchat")
@UsageMessage("&8» &cPoprawne użycie &7/adminchat <text>")
public class AdminChatCommand {

    private final AudiencesService audiencesService;
    private final Server server;

    public AdminChatCommand(AudiencesService audiencesService, Server server) {
        this.audiencesService = audiencesService;
        this.server = server;
    }

    @Execute
    @MinArgs(1)
    public void execute(CommandSender sender, @Joiner String text) {
        Notice notice = audiencesService.notice()
            .console()
            .message(messages -> messages.adminChat().format())
            .placeholder("{NICK}", sender.getName())
            .placeholder("{TEXT}", text);

        for (Player player : this.server.getOnlinePlayers()) {
            if (!player.hasPermission("eternalcore.adminchat.spy")) {
                continue;
            }

            notice.player(player.getUniqueId());
        }

        notice.send();
    }
}
