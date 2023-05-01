package com.eternalcode.core.feature.poll;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.notification.NoticeService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.argument.basictype.StringArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.suggestion.Suggest;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.List;

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

    @Execute(route = "create", required = 2)
    @Permission("eternalcore.poll.create")
    @DescriptionDocs(description = "Create new poll", arguments = "<poll name> <duration>")
    void executeCreate(Player player, @Arg @Suggest("pool_name") String name, @Arg Duration duration) {
        this.pollManager.startCreatingPool(player.getUniqueId(), name, duration);
    }

    @Execute(route = "cancel-create", required = 0)
    @Permission("eternalcore.poll.create")
    @DescriptionDocs(description = "Cancel creating new poll")
    void executeCancel(Player player) {
        this.pollManager.cancelCreatingPool(player.getUniqueId());
    }

    @Execute(route = "check", required = 1)
    @DescriptionDocs(description = "Check poll results")
    void executeCheck(Player player, @Arg @By(PoolArgument.KEY) String name) {
        this.pollResultsInventory.openResultsInventory(player, name);
    }

    public static class PoolArgument extends StringArgument {

        public static final String KEY = "pool";
        private final PollManager pollManager;

        public PoolArgument(PollManager pollManager) {
            this.pollManager = pollManager;
        }

        @Override
        public List<Suggestion> suggest(LiteInvocation invocation) {
            return this.pollManager.getPreviousPolls().stream()
                .map(poll -> Suggestion.of(poll.getName()))
                .toList();
        }

    }
}
