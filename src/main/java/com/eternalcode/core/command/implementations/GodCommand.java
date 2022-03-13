package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.command.argument.PlayerArgOrSender;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Section(route = "god", aliases = "godmode" )
@Permission("eternalcore.command.god")
public class GodCommand {

    private final NoticeService noticeService;
    private final PluginConfiguration config;

    public GodCommand(NoticeService noticeService, PluginConfiguration configuration) {
        this.config = configuration;
        this.noticeService = noticeService;
    }

    @Execute
    public void execute(CommandSender sender, @Arg(0) @Handler(PlayerArgOrSender.class) Player player) {
        player.setInvulnerable(!player.isInvulnerable());

        this.noticeService
            .notice()
            .placeholder("{STATE}", player.isInvulnerable() ? this.config.format.enabled : this.config.format.disabled)
            .message(messages -> messages.other().godMessage())
            .player(player.getUniqueId())
            .send();

        if (sender.equals(player)) {
            return;
        }

        this.noticeService
            .notice()
            .placeholder("{STATE}", player.isInvulnerable() ? this.config.format.enabled : this.config.format.disabled)
            .placeholder("{PLAYER}", player.getName())
            .message(messages -> messages.other().godSetMessage())
            .sender(sender)
            .send();
    }
}
