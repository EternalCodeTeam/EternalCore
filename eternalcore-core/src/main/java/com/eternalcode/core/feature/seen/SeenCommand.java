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
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;

@Command(name = "seen")
@Permission("eternalcore.seen")
class SeenCommand {

    private final NoticeService noticeService;

    @Inject
    public SeenCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Shows when the player was last seen on the server")
    void execute(@Context User sender, @Arg Player target) {
        long lastPlayed = target.getLastPlayed();

        // If the time is 0, it means the player has never joined before, indicating their first join.
        if (lastPlayed == 0) {
            this.noticeService.create()
                .user(sender)
                .notice(translation -> translation.seen().firstJoin())
                .placeholder("{PLAYER}", target.getName())
                .send();

            return;
        }

        Duration lastPlayedBetween = Duration.between(Instant.ofEpochMilli(lastPlayed), Instant.now());
        String lastPlayedFormatted = DurationUtil.format(lastPlayedBetween);

        this.noticeService.create()
            .user(sender)
            .notice(translation -> translation.seen().lastSeen())
            .placeholder("{PLAYER}", target.getName())
            .placeholder("{SEEN}", lastPlayedFormatted)
            .send();
    }
}
