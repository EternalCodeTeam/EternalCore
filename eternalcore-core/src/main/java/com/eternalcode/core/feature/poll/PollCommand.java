package com.eternalcode.core.feature.poll;

import com.eternalcode.core.notification.NoticeService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.UUID;

@Route(name = "poll")
@Permission("eternalcore.poll")
public class PollCommand {

    private final NoticeService noticeService;
    private final PollManager pollManager;

    public PollCommand(NoticeService noticeService, PollManager pollManager) {
        this.noticeService = noticeService;
        this.pollManager = pollManager;
    }

    @Execute(required = 0)
    void executeVote(Player player) {
        if (!this.pollManager.isPollActive()) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.poll().cantVote())
                .send();

            return;
        }

        Poll poll = this.pollManager.getActivePoll();

        PollInventory inventory = new PollInventory(poll);
        inventory.openVoteInventory(player);
    }

    @Execute(required = 1)
    @Route(name = "create")
    void executeCreate(Player player, @Arg Duration duration) {
        if (!this.pollManager.markPlayer(player, duration)) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.poll().alreadyCreatingPoll())
                .send();
        }
    }

    @Execute(required = 0)
    @Route(name = "cancel")
    void executeCancel(Player player) {
        if (!this.pollManager.isMarked(player)) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.poll().cantCancelPoll())
                .send();

            return;
        }

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.poll().pollCancelled())
            .send();

        this.pollManager.unmarkPlayer(player);
    }

    @Execute(required = 1)
    @Route(name = "check")
    void executeCheck(Player player, @Arg UUID uuid) {

    }
}
