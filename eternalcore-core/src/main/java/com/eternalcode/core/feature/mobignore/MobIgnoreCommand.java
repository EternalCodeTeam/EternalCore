package com.eternalcode.core.feature.mobignore;

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

@Command(name = "mobignore", aliases = { "notarget", "nomobtarget" })
@Permission("eternalcore.mobignore")
public class MobIgnoreCommand {

    private final MobIgnoreService mobIgnoreService;
    private final NoticeService noticeService;

    @Inject
    public MobIgnoreCommand(MobIgnoreService mobIgnoreService, NoticeService noticeService) {
        this.mobIgnoreService = mobIgnoreService;
        this.noticeService = noticeService;
    }

    @Execute
    void execute(@Sender Player player) {
        UUID uniqueId = player.getUniqueId();

        if (this.mobIgnoreService.isIgnored(uniqueId)) {
            this.turnOff(uniqueId);
            return;
        }

        this.turnOn(player);
    }

    @Execute
    void execute(@Sender CommandSender sender, @Arg Player target) {
        UUID uniqueId = target.getUniqueId();

        if (this.mobIgnoreService.isIgnored(uniqueId)) {
            this.noticeService.create()
                .notice(translation -> translation.noTarget().mobIgnoreTarget())
                .placeholder("{PLAYER}", target.getName())
                .sender(sender)
                .send();

            this.turnOn(target);
            return;
        }
        this.noticeService.create()
            .notice(translation -> translation.noTarget().noMobIgnoreTarget())
            .placeholder("{PLAYER}", target.getName())
            .sender(sender)
            .send();

        this.turnOff(uniqueId);
    }

    private void turnOn(Player player) {
        this.mobIgnoreService.ignore(player);

        this.noticeService.create()
            .notice(translation -> translation.noTarget().mobIgnore())
            .player(player.getUniqueId())
            .send();
    }

    private void turnOff(UUID uniqueId) {
        this.mobIgnoreService.unignore(uniqueId);

        this.noticeService.create()
            .notice(translation -> translation.noTarget().noMobIgnore())
            .player(uniqueId)
            .send();
    }
}
