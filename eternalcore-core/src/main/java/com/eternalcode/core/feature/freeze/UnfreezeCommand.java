package com.eternalcode.core.feature.freeze;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

import java.time.Duration;

@Command(name = "unfreeze")
@Permission("eternalcore.unfreeze")
public class UnfreezeCommand {

    private final NoticeService noticeService;
    private final FreezeServiceImpl freezeServiceImpl;

    @Inject
    public UnfreezeCommand(NoticeService noticeService, FreezeServiceImpl freezeServiceImpl) {
        this.noticeService = noticeService;
        this.freezeServiceImpl = freezeServiceImpl;
    }

    @Execute
    public void unfreezeSelf(@Context Player player) {
        if (!player.isFrozen()) {
            this.noticeService.create()
                .notice(translation -> translation.freeze().selfNotFrozen())
                .placeholder("{PLAYER}", player.getName())
                .player(player.getUniqueId())
                .send();
            return;
        }

        this.freezeServiceImpl.freezePlayer(player, Duration.ZERO);

        this.noticeService.create()
            .notice(translation -> translation.freeze().unfrozenSelf())
            .player(player.getUniqueId())
            .send();
    }

    @Execute
    @Permission("eternalcore.unfreeze.other")
    public void unfreezeOther(@Context Player player, @Arg Player target) {
        if (!target.isFrozen()) {
            this.noticeService.create()
                .notice(translation -> translation.freeze().otherNotFrozen())
                .placeholder("{PLAYER}", target.getName())
                .player(player.getUniqueId())
                .send();
            return;
        }

        this.freezeServiceImpl.freezePlayer(target, Duration.ZERO);

        this.noticeService.create()
            .notice(translation -> translation.freeze().unfrozenOther())
            .placeholder("{PLAYER}", target.getName())
            .player(player.getUniqueId())
            .send();

        this.noticeService.create()
            .notice(translation -> translation.freeze().unfrozenByOther())
            .placeholder("{PLAYER}", player.getName())
            .player(target.getUniqueId())
            .send();
    }
}
