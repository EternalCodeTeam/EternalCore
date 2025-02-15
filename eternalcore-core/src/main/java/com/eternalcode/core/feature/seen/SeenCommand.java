package com.eternalcode.core.feature.seen;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import com.eternalcode.core.util.DurationUtil;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;

import java.time.Duration;
import java.time.Instant;

@Command(name = "seen", aliases = { "lastonline" })
@Permission("eternalcore.seen")
class SeenCommand {

    private final Server server;
    private final NoticeService noticeService;

    @Inject
    public SeenCommand(Server server, NoticeService noticeService) {
        this.server = server;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Shows when the player was last seen on the server")
    void execute(@Context User sender, @Arg User target) {
        OfflinePlayer targetPlayer = this.server.getOfflinePlayer(target.getUniqueId());

        if (targetPlayer.isOnline()) {
            this.noticeService.create()
                .user(sender)
                .notice(translation -> translation.seen().nowOnline())
                .placeholder("{PLAYER}", target.getName())
                .send();

            return;
        }

        long lastPlayed = targetPlayer.getLastPlayed();

        // If the time is 0, it means the player has never joined before
        if (lastPlayed == 0) {
            this.noticeService.create()
                .user(sender)
                .notice(translation -> translation.seen().neverPlayedBefore())
                .placeholder("{PLAYER}", target.getName())
                .send();

            return;
        }

        Duration lastPlayedBetween = Duration.between(Instant.ofEpochMilli(lastPlayed), Instant.now());
        String lastPlayedFormatted = DurationUtil.format(lastPlayedBetween, true);

        this.noticeService.create()
            .user(sender)
            .notice(translation -> translation.seen().lastSeen())
            .placeholder("{PLAYER}", target.getName())
            .placeholder("{SEEN}", lastPlayedFormatted)
            .send();
    }
}
