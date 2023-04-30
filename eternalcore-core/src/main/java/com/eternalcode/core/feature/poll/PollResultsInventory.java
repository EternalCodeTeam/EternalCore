package com.eternalcode.core.feature.poll;

import com.eternalcode.core.notification.NoticeService;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Optional;

class PollResultsInventory {

    private final PollManager pollManager;
    private final NoticeService noticeService;

    PollResultsInventory(PollManager pollManager, NoticeService noticeService) {
        this.pollManager = pollManager;
        this.noticeService = noticeService;
    }

    void openResultsInventory(Player player, String pollName) {
        Optional<Poll> previousPoll = this.pollManager.getPreviousPoll(pollName);

        if (previousPoll.isEmpty()) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.poll().unavailablePollResults());
            return;
        }

        Poll poll = previousPoll.get();
        Gui gui = Gui.gui()
            .title(Component.text(poll.getDescription()))
            .type(GuiType.HOPPER)
            .disableAllInteractions()
            .create();

        int totalVotes = poll.getTotalVotes();

        for (PollOption pollOption : poll.getOptionList()) {
            int votes = pollOption.getVotes();

            double percentage = (totalVotes > 0) ? ((double) votes / totalVotes) * 100 : 0;

            GuiItem item = ItemBuilder.from(Material.PAPER)
                .name(Component.text(pollOption.getOption()))
                .lore(Component.text(percentage + "%").color(NamedTextColor.YELLOW))
                .asGuiItem();

            gui.addItem(item);
        }

        GuiItem fillItem = ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE)
            .name(Component.text(" "))
            .asGuiItem();

        gui.getFiller().fill(fillItem);

        gui.open(player);
    }

}
