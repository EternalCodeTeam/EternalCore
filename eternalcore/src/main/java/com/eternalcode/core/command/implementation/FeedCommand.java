package com.eternalcode.core.command.implementation;

import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.chat.notification.NoticeService;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Route(name = "feed")
@Permission("eternalcore.feed")
public class FeedCommand {

    private final NoticeService noticeService;

    public FeedCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void execute(Viewer audience, CommandSender sender, @Arg @By("or_sender") Player player) {
        player.setFoodLevel(20);

        this.noticeService.create()
            .message(messages -> messages.other().feedMessage())
            .player(player.getUniqueId())
            .send();

        if (sender.equals(player)) {
            return;
        }

        this.noticeService.create()
            .placeholder("{PLAYER}", player.getName())
            .message(messages -> messages.other().feedMessageBy())
            .viewer(audience)
            .send();
    }
}

