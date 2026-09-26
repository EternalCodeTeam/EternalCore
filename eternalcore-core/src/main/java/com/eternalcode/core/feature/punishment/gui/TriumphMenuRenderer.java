package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
        return new GuiItem(this.toItemStack(item), event -> {
            if (event.getWhoClicked() instanceof Player clicker) {
                item.click(clicker);
            }
        });
    }

    private ItemStack toItemStack(MenuItem item) {
        ItemStack itemStack = new ItemStack(item.material());

        itemStack.editMeta(meta -> {
            meta.displayName(item.name());
            meta.lore(item.lore());
        });

        return itemStack;
    }
}
