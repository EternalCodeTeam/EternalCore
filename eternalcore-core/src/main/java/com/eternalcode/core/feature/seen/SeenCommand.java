package com.eternalcode.core.feature.seen;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import com.eternalcode.core.util.DurationUtil;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;

import java.time.Duration;
import java.time.Instant;
import org.bukkit.entity.Player;

@Command(name = "seen", aliases = { "lastonline" })
@Permission("eternalcore.seen")
class SeenCommand {

    public static final int NEVER_JOINED_BEFORE = 0;
    private final Server server;
    private final NoticeService noticeService;

    @Inject
    public SeenCommand(Server server, NoticeService noticeService) {
        this.server = server;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Shows when the player was last seen on the server")
    void execute(@Context Viewer sender, @Arg @Async OfflinePlayer target) {
        OfflinePlayer targetPlayer = this.server.getOfflinePlayer(target.getUniqueId());

        if (targetPlayer.isOnline()) {
            this.noticeService.create()
                .viewer(sender)
                .notice(translation -> translation.seen().nowOnline())
                .placeholder("{PLAYER}", target.getName())
                .send();

            return;
        }

        long lastPlayed = targetPlayer.getLastPlayed();

        if (lastPlayed == NEVER_JOINED_BEFORE) {
            this.noticeService.create()
                .viewer(sender)
                .notice(translation -> translation.seen().neverPlayedBefore())
                .placeholder("{PLAYER}", target.getName())
                .send();

            return;
        }

        Duration lastPlayedBetween = Duration.between(Instant.ofEpochMilli(lastPlayed), Instant.now());
        String lastPlayedFormatted = DurationUtil.format(lastPlayedBetween, true);

        this.noticeService.create()
            .viewer(sender)
            .notice(translation -> translation.seen().lastSeen())
            .placeholder("{PLAYER}", target.getName())
            .placeholder("{SEEN}", lastPlayedFormatted)
            .send();
    }
}
