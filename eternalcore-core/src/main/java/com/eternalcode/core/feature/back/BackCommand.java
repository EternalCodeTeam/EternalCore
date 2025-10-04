package com.eternalcode.core.feature.back;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.back.BackService.BackLocation;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.Optional;
import org.bukkit.entity.Player;
import panda.std.Pair;

@Command(name = "back")
public class BackCommand {

    private final BackService backService;
    private final NoticeService noticeService;

    @Inject
    public BackCommand(BackService backService, NoticeService noticeService) {
        this.backService = backService;
        this.noticeService = noticeService;
    }

    @Execute(name = "death")
    @Permission("eternalcore.back.death")
    @DescriptionDocs(description = "Teleport to your last death location")
    public void executeBackDeath(@Sender Player player) {
        Optional<Pair<BackLocation, BackLocation>> backPair = this.backService.getBackLocationPair(player.getUniqueId());

        if (backPair.isEmpty() || backPair.get().getFirst() == null) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.back().lastLocationNoExist());
            return;
        }

        BackLocation deathLocation = backPair.get().getFirst();
        this.backService.teleportBack(player, deathLocation.location());
        this.noticeService.player(player.getUniqueId(), translation -> translation.back().teleportedToLastLocation());
    }

    @Execute(name = "teleport")
    @Permission("eternalcore.back.teleport")
    @DescriptionDocs(description = "Teleport to your last teleport location")
    public void executeBackTeleport(@Sender Player player) {
        Optional<Pair<BackLocation, BackLocation>> backPair = this.backService.getBackLocationPair(player.getUniqueId());

        if (backPair.isEmpty() || backPair.get().getSecond() == null) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.back().lastLocationNoExist());
            return;
        }

        BackLocation teleportLocation = backPair.get().getSecond();
        this.backService.teleportBack(player, teleportLocation.location());
        this.noticeService.player(player.getUniqueId(), translation -> translation.back().teleportedToLastLocation());
    }

    @Execute(name = "death")
    @Permission("eternalcore.back.death.other")
    @DescriptionDocs(description = "Teleport specified player to their last death location", arguments = "<player>")
    public void executeBackDeathOther(@Sender Viewer viewer, @Arg Player target) {
        Optional<Pair<BackLocation, BackLocation>> backPair = this.backService.getBackLocationPair(target.getUniqueId());

        if (backPair.isEmpty() || backPair.get().getFirst() == null) {
            this.noticeService.viewer(viewer, translation -> translation.back().lastLocationNoExist());
            return;
        }

        BackLocation deathLocation = backPair.get().getFirst();
        this.backService.teleportBack(target, deathLocation.location());
        this.noticeService.player(target.getUniqueId(), translation -> translation.back().teleportedToLastLocation());

        this.noticeService.create()
            .viewer(viewer)
            .notice(translation -> translation.back().teleportedSpecifiedPlayerLastLocation())
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

    @Execute(name = "teleport")
    @Permission("eternalcore.back.teleport.other")
    @DescriptionDocs(description = "Teleport specified player to their last teleport location", arguments = "<player>")
    public void executeBackTeleportOther(@Sender Viewer viewer, @Arg Player target) {
        Optional<Pair<BackLocation, BackLocation>> backPair = this.backService.getBackLocationPair(target.getUniqueId());

        if (backPair.isEmpty() || backPair.get().getSecond() == null) {
            this.noticeService.viewer(viewer, translation -> translation.back().lastLocationNoExist());
            return;
        }

        BackLocation teleportLocation = backPair.get().getSecond();
        this.backService.teleportBack(target, teleportLocation.location());
        this.noticeService.player(target.getUniqueId(), translation -> translation.back().teleportedToLastLocation());

        this.noticeService.create()
            .viewer(viewer)
            .notice(translation -> translation.back().teleportedSpecifiedPlayerLastLocation())
            .placeholder("{PLAYER}", target.getName())
            .send();
    }
}
