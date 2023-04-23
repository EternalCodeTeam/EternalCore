package com.eternalcode.core.feature.poll;

import com.eternalcode.core.notification.NoticeService;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import static com.eternalcode.core.util.LabeledOptionUtil.LabeledOption;

public class PollInventory {

    private final Poll poll;
    private final PollManager pollManager;
    private final NoticeService noticeService;
    private final MiniMessage miniMessage;

    public PollInventory(Poll poll, PollManager pollManager, NoticeService noticeService, MiniMessage miniMessage) {
        this.poll = poll;
        this.pollManager = pollManager;
        this.noticeService = noticeService;
        this.miniMessage = miniMessage;
    }

    public void openVoteInventory(Player player) {
        Gui gui = Gui.gui()
            .title(Component.text(this.poll.getDescription()))
            .type(GuiType.HOPPER)
            .disableAllInteractions()
            .create();

        for (LabeledOption<String> labeledOption : this.poll.getOptionList()) {
            GuiItem item = ItemBuilder.from(Material.PAPER)
                .name(Component.text(labeledOption.getOption()))
                .asGuiItem();

            item.setAction((event) -> {

                event.getWhoClicked().closeInventory();

                if (!this.pollManager.isPollActive()) {
                    return;
                }

                if (this.poll.isAlreadyVoted(player)) {
                    this.noticeService.create()
                        .player(player.getUniqueId())
                        .notice(translation -> translation.poll().alreadyVoted())
                        .send();

                    return;
                }

                this.poll.vote(player, labeledOption);

                this.noticeService.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.poll().successfullyVoted())
                    .send();
            });

            gui.addItem(item);
        }

        GuiItem fillItem = ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE)
            .name(Component.text(" "))
            .asGuiItem();

        gui.getFiller().fill(fillItem);

        gui.open(player);
    }

    public Gui createResultsInventory() {
        Gui gui = Gui.gui()
            .title(Component.text(this.poll.getDescription()))
            .type(GuiType.HOPPER)
            .disableAllInteractions()
            .create();

        int totalVotes = this.poll.getTotalVotes();

        this.poll.getOptionList().forEach(labeledOption -> {
            int votes = this.poll.getVotesOf(labeledOption);

            double percentage = (totalVotes > 0) ? ((double) votes / totalVotes) * 100 : 0;

            GuiItem item = ItemBuilder.from(Material.PAPER)
                .name(Component.text(labeledOption.getOption()))
                .lore(this.miniMessage.deserialize(" <yellow>" + percentage + "%"))
                .asGuiItem();

            gui.addItem(item);
        });

        GuiItem fillItem = ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE)
            .name(Component.text(" "))
            .asGuiItem();

        gui.getFiller().fill(fillItem);

        return gui;
    }

}
