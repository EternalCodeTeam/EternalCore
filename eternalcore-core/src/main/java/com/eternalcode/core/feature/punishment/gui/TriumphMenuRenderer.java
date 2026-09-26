package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import org.bukkit.Server;
import org.bukkit.entity.Player;

@Service
class TriumphMenuRenderer implements MenuRenderer {

    private final Server server;

    @Inject
    TriumphMenuRenderer(Server server) {
        this.server = server;
    }

    @Override
    public void open(Player viewer, Menu menu) {
        if (!this.server.isPrimaryThread()) {
            throw new IllegalStateException("Menu must be opened on the main thread");
        }

        Gui gui = Gui.gui()
            .title(menu.title())
            .rows(menu.rows())
            .disableAllInteractions()
            .create();

        menu.items().forEach((slot, item) -> gui.setItem(slot, this.toGuiItem(item)));
        gui.open(viewer);
    }

    private GuiItem toGuiItem(MenuItem item) {
        return ItemBuilder.from(item.material())
            .name(item.name())
            .lore(item.lore())
            .asGuiItem(event -> {
                if (event.getWhoClicked() instanceof Player clicker) {
                    item.click(clicker);
                }
            });
    }
}
