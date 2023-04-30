package com.eternalcode.core.feature.poll;

import com.eternalcode.core.notification.NoticeService;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

class PollVoteInventory {

    private final PollManager pollManager;
    private final NoticeService noticeService;

    PollVoteInventory(PollManager pollManager, NoticeService noticeService) {
        this.pollManager = pollManager;
        this.noticeService = noticeService;
    }

    void openVoteInventory(Player player) {
        Optional<Poll> pollOptional = this.pollManager.getActivePoll();

        if (pollOptional.isEmpty()) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.poll().cantVote());
            return;
        }

        Poll poll = pollOptional.get();
        Gui gui = Gui.gui()
            .title(Component.text(poll.getDescription()))
            .type(GuiType.HOPPER)
            .disableAllInteractions()
            .create();

        for (PollOption pollOption : poll.getOptionList()) {
            GuiItem item = ItemBuilder.from(Material.PAPER)
                .name(Component.text(pollOption.getOption()))
                .asGuiItem();

            item.setAction(event -> {
                event.getWhoClicked().closeInventory();
                this.vote(player, poll, pollOption);
            });

            gui.addItem(item);
        }

        GuiItem fillItem = ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE)
            .name(Component.text(" "))
            .asGuiItem();

        gui.getFiller().fill(fillItem);
        gui.open(player);
    }

    private void vote(Player player, Poll poll, PollOption pollOption) {
        UUID playerUuid = player.getUniqueId();

        if (!this.pollManager.isPollActive()) {
            return;
        }

        if (poll.isAlreadyVoted(playerUuid)) {
            this.noticeService.create()
                .player(playerUuid)
                .notice(translation -> translation.poll().alreadyVoted())
                .send();

            return;
        }

        poll.vote(playerUuid, pollOption);
        this.noticeService.create()
            .player(playerUuid)
            .notice(translation -> translation.poll().successfullyVoted())
            .send();
    }

}
