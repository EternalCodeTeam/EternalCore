package com.eternalcode.core.feature.poll;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.notification.NoticeService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.suggestion.Suggest;
import org.bukkit.entity.Player;

import java.time.Duration;

@Route(name = "poll")
@Permission("eternalcore.poll")
public class PollCommand {

    private final PollManager pollManager;
    private final PollVoteInventory pollVoteInventory;
    private final PollResultsInventory pollResultsInventory;

    public PollCommand(NoticeService noticeService, PollManager pollManager) {
        this.pollManager = pollManager;
        this.pollVoteInventory = new PollVoteInventory(pollManager, noticeService);
        this.pollResultsInventory = new PollResultsInventory(pollManager, noticeService);
    }

    @Execute(required = 0)
    @DescriptionDocs(description = "Vote for the current poll")
    void openVoteInventory(Player player) {
        this.pollVoteInventory.openVoteInventory(player);
    }

    @Execute(required = 1)
    @Permission("eternalcore.poll.create")
    @Route(name = "create")
    @DescriptionDocs(description = "Create new poll", arguments = "<poll name> <duration>")
    void executeCreate(Player player, @Arg @Suggest("pool_name") String name, @Arg Duration duration) {
        this.pollManager.startCreatingPool(player.getUniqueId(), name, duration);
    }

    @Execute(required = 0)
    @Permission("eternalcore.poll.create")
    @Route(name = "cancel")
    @DescriptionDocs(description = "Cancel creating new poll")
    void executeCancel(Player player) {
        this.pollManager.cancelCreatingPool(player.getUniqueId());
    }

    @Execute(required = 1)
    @Route(name = "check")
    @DescriptionDocs(description = "Check poll results")
    void executeCheck(Player player, @Arg String name) {
        this.pollResultsInventory.openResultsInventory(player, name);
    }

}
