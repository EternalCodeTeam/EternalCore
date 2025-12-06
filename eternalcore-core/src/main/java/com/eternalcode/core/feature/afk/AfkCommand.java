package com.eternalcode.core.feature.afk;

import static com.eternalcode.core.feature.afk.AfkCommand.AFK_BYPASS_PERMISSION;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.delay.Delay;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.time.Duration;
import java.util.UUID;
import org.bukkit.entity.Player;

@Command(name = "afk")
@Permission("eternalcore.afk")
@PermissionDocs(
    name = "Afk bypass",
    permission = AFK_BYPASS_PERMISSION,
    description = "Allows player to bypass AFK delay when using /afk command."
)
class AfkCommand {

    static final String AFK_BYPASS_PERMISSION = "eternalcore.afk.bypass";

    private final NoticeService noticeService;
    private final AfkSettings afkSettings;
    private final AfkService afkService;
    private final Delay<UUID> delay;

    @Inject
    AfkCommand(NoticeService noticeService, AfkSettings afkSettings, AfkService afkService) {
        this.noticeService = noticeService;
        this.afkSettings = afkSettings;
        this.afkService = afkService;
        this.delay = Delay.withDefault(() -> this.afkSettings.afkCommandDelay());
    }

    @Execute
    @DescriptionDocs(description = "Toggles AFK status for the player.")
    void execute(@Sender Player player) {
        UUID uuid = player.getUniqueId();

        if (player.hasPermission(AFK_BYPASS_PERMISSION)) {
            this.afkService.switchAfk(uuid, AfkReason.COMMAND);
            return;
        }

        if (this.delay.hasDelay(uuid)) {
            Duration time = this.delay.getRemaining(uuid);

            this.noticeService
                .create()
                .notice(translation -> translation.afk().afkDelay())
                .placeholder("{TIME}", DurationUtil.format(time, true))
                .player(uuid)
                .send();

            return;
        }

        this.afkService.switchAfk(uuid, AfkReason.COMMAND);
        this.delay.markDelay(uuid);
    }
}
