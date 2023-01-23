package com.eternalcode.core.feature.essentials;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Route(name = "god", aliases = "godmode")
@Permission("eternalcore.god")
public class GodCommand {

    private final NoticeService noticeService;

    public GodCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void execute(CommandSender sender, Viewer viewer, @Arg @By("or_sender") Player player) {
        player.setInvulnerable(!player.isInvulnerable());

        this.noticeService
            .create()
            .placeholder("{STATE}", translation -> player.isInvulnerable() ? translation.format().enable() : translation.format().disable())
            .notice(translation -> translation.player().godMessage())
            .player(player.getUniqueId())
            .send();

        if (sender.equals(player)) {
            return;
        }

        this.noticeService
            .create()
            .placeholder("{STATE}", translation -> player.isInvulnerable() ? translation.format().enable() : translation.format().disable())
            .placeholder("{PLAYER}", player.getName())
            .notice(translation -> translation.player().godSetMessage())
            .viewer(viewer)
            .send();
    }
}
