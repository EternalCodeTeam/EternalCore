package com.eternalcode.core.feature.freeze;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "unfreeze")
@Permission("eternalcore.unfreeze")
public class UnfreezeCommand {

    private final NoticeService noticeService;

    @Inject
    public UnfreezeCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
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

        player.setFreezeTicks(0);

        this.noticeService.create()
            .notice(translation -> translation.freeze().unfrozenSelf())
            .player(player.getUniqueId())
            .send();
    }

    @Execute
    @Permission("eternalcore.unfreeze.other")
    public void unfreezeOther(@Context CommandSender sender, @Arg Player target) {
        if (!target.isFrozen()) {
            this.noticeService.create()
                .notice(translation -> translation.freeze().otherNotFrozen())
                .placeholder("{PLAYER}", target.getName())
                .sender(sender)
                .send();
            return;
        }

        target.setFreezeTicks(0);

        this.noticeService.create()
            .notice(translation -> translation.freeze().unfrozenOther())
            .placeholder("{PLAYER}", target.getName())
            .sender(sender)
            .send();

        this.noticeService.create()
            .notice(translation -> translation.freeze().unfrozenByOther())
            .placeholder("{PLAYER}", sender.getName())
            .player(target.getUniqueId())
            .send();
    }
}
