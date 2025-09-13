package com.eternalcode.core.feature.back;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

import java.util.Optional;

@Command(name = "back")
public class BackCommand {

    private static final String PERMISSION_BACK_ON_DEATH = "eternalcore.back.ondeath";

    private final BackService backService;
    private final NoticeService noticeService;

    @Inject
    public BackCommand(BackService backService, NoticeService noticeService) {
        this.backService = backService;
        this.noticeService = noticeService;
    }

    @Execute
    @Permission("eternalcore.back")
    @DescriptionDocs(description = "Teleport to last location")
    public void execute(@Sender Player player) {
        Optional<BackService.BackLocation> backLocation = this.backService.getBackLocation(player.getUniqueId());

        if (backLocation.isEmpty()) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.back().lastLocationNoExist());
            return;
        }

        BackService.BackLocation location = backLocation.get();

        if (location.isFromDeath() && !player.hasPermission(PERMISSION_BACK_ON_DEATH)) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.back().noPermissionBackOnDeath());
            return;
        }

        this.backService.teleportBack(player, location.location());
        this.noticeService.player(player.getUniqueId(), translation -> translation.back().teleportedToLastLocation());
    }

    @Execute
    @Permission("eternalcore.back.other")
    @DescriptionDocs(description = "Teleport specified player to last location", arguments = "<player>")
    public void execute(@Sender Viewer viewer, @Arg Player target) {
        Optional<BackService.BackLocation> backLocation = this.backService.getBackLocation(target.getUniqueId());

        if (backLocation.isEmpty()) {
            this.noticeService.viewer(viewer, translation -> translation.back().lastLocationNoExist());
            return;
        }

        BackService.BackLocation location = backLocation.get();

        if (location.isFromDeath() && !target.hasPermission(PERMISSION_BACK_ON_DEATH)) {
            this.noticeService.viewer(viewer, translation -> translation.back().targetNoPermissionBackOnDeath());
            return;
        }

        this.backService.teleportBack(target, location.location());
        this.noticeService.player(target.getUniqueId(), translation -> translation.back().teleportedToLastLocation());

        this.noticeService.create()
            .viewer(viewer)
            .notice(translation -> translation.back().teleportedSpecifiedPlayerLastLocation())
            .placeholder("{PLAYER}", target.getName())
            .send();
    }
}
