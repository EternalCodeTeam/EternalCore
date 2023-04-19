package com.eternalcode.core.feature.poll;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import static com.eternalcode.core.util.LabeledOptionUtil.LabeledOption;

public class PollInventory {

    private final Poll poll;

    public PollInventory(Poll poll) {
        this.poll = poll;
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

            item.setAction((event) -> this.poll.vote(player, labeledOption));

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
            .create();



        return gui;
    }

}
