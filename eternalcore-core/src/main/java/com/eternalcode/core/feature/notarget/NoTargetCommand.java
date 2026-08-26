package com.eternalcode.core.feature.notarget;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.UUID;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "notarget")
@Permission("eternalcore.notarget")
public class NoTargetCommand {

    private final MobTargetService mobTargetService;
    private final NoticeService noticeService;

    @Inject
    public NoTargetCommand(MobTargetService mobTargetService, NoticeService noticeService) {
        this.mobTargetService = mobTargetService;
        this.noticeService = noticeService;
    }

    @Execute
    void execute(@Sender Player player) {

        UUID uniqueId = player.getUniqueId();

        if (this.mobTargetService.doMobsIgnore(uniqueId)) {
            this.turnOff(uniqueId);
        } else {
            this.turnOn(player);
        }
    }

    @Execute
    void execute(@Sender CommandSender sender, @Arg Player target) {
        UUID uniqueId = target.getUniqueId();

        if (this.mobTargetService.doMobsIgnore(uniqueId)) {
            this.noticeService.create()
                .notice(translation -> translation.noTarget().turnedOn())
                .placeholder("{PLAYER}", target.getName())
                .sender(sender)
                .send();

            this.turnOn(target);
        } else {
            this.noticeService.create()
                .notice(translation -> translation.noTarget().turnedOff())
                .placeholder("{PLAYER}", target.getName())
                .sender(sender)
                .send();

            this.turnOff(uniqueId);
        }
    }

    private void turnOn(Player player) {
        this.mobTargetService.ignorePlayer(player);

        this.noticeService.create()
            .notice(translation -> translation.noTarget().enabled())
            .player(player.getUniqueId())
            .send();
    }

    private void turnOff(UUID uniqueId) {
        this.mobTargetService.removeMobIgnore(uniqueId);

        this.noticeService.create()
            .notice(translation -> translation.noTarget().disabled())
            .player(uniqueId)
            .send();
    }
}
