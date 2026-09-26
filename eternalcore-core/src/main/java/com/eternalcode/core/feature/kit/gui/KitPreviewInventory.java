package com.eternalcode.core.feature.kit.gui;

import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitClaimNotifier;
import com.eternalcode.core.feature.kit.KitConfig;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.KitSettings;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Service
public class KitPreviewInventory {

    private static final int BUTTON_ROWS = 1;

    private final KitClaimNotifier claimNotifier;
    private final KitItemRenderer itemRenderer;
    private final KitSettings settings;

    @Inject
    KitPreviewInventory(KitClaimNotifier claimNotifier, KitItemRenderer itemRenderer, KitSettings settings) {
        this.claimNotifier = claimNotifier;
        this.itemRenderer = itemRenderer;
        this.settings = settings;
    }

    /**
     * @param backAction executed by the back button, e.g. reopening the kit list.
     */
    public void open(Player player, Kit kit, Runnable backAction) {
        KitConfig.GuiSection gui = this.settings.gui();
        List<ItemStack> items = kit.items();
        int rows = KitGuiLayout.clampRows(Math.min(KitGuiLayout.rowsFor(items.size()), KitGuiLayout.MAX_ROWS - BUTTON_ROWS) + BUTTON_ROWS);
        int itemSlots = (rows - BUTTON_ROWS) * KitGuiLayout.ROW_SIZE;

        Gui inventory = Gui.gui()
            .title(this.itemRenderer.deserialize(gui.previewTitle().replace(KitNotices.KIT, kit.displayName())))
            .rows(rows)
            .disableAllInteractions()
            .create();

        for (int slot = 0; slot < items.size() && slot < itemSlots; slot++) {
            inventory.setItem(slot, new GuiItem(items.get(slot)));
        }

        this.placeButton(inventory, rows, gui.claimButton(), () -> {
            player.closeInventory();
            this.claimNotifier.claimAndNotify(player, kit);
        });
        this.placeButton(inventory, rows, gui.backButton(), backAction);

        inventory.open(player);
    }

    private void placeButton(Gui inventory, int rows, KitConfig.Button button, Runnable action) {
        if (!button.enabled()) {
            return;
        }

        GuiItem guiItem = new GuiItem(this.itemRenderer.renderButton(button), event -> action.run());
        inventory.setItem(KitGuiLayout.lastRowSlot(rows, button.column()), guiItem);
    }
}
