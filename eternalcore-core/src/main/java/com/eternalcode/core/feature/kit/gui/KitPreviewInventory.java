package com.eternalcode.core.feature.kit.gui;

import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitClaimNotifier;
import com.eternalcode.core.feature.kit.KitConfig;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.KitSettings;
import com.eternalcode.core.feature.kit.messages.KitGuiMessages;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.multification.shared.Formatter;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Service
public class KitPreviewInventory {

    private final KitClaimNotifier claimNotifier;
    private final KitItemRenderer itemRenderer;
    private final KitSettings settings;
    private final TranslationManager translationManager;

    @Inject
    KitPreviewInventory(KitClaimNotifier claimNotifier, KitItemRenderer itemRenderer, KitSettings settings, TranslationManager translationManager) {
        this.claimNotifier = claimNotifier;
        this.itemRenderer = itemRenderer;
        this.settings = settings;
        this.translationManager = translationManager;
    }

    public void open(Player player, Kit kit, Runnable backAction) {
        KitConfig.GuiSection layout = this.settings.gui();
        KitGuiMessages messages = this.translationManager.getMessages().kit().gui();
        List<ItemStack> items = kit.items();
        int buttonRows = 1;
        int rows = KitGuiLayout.clampRows(Math.min(KitGuiLayout.rowsFor(items.size()), KitGuiLayout.MAX_ROWS - buttonRows) + buttonRows);
        int itemSlots = (rows - buttonRows) * KitGuiLayout.ROW_SIZE;

        Gui inventory = Gui.gui()
            .title(this.itemRenderer.deserialize(new Formatter().register(KitNotices.KIT, kit.displayName()).format(messages.previewTitle())))
            .rows(rows)
            .disableAllInteractions()
            .create();

        for (int slot = 0; slot < items.size() && slot < itemSlots; slot++) {
            inventory.setItem(slot, new GuiItem(items.get(slot)));
        }

        this.placeButton(inventory, rows, layout.claimButton(), this.itemRenderer.renderButton(
            layout.claimButton().material(),
            messages.claimButtonName(),
            messages.claimButtonLore()
        ), () -> {
            player.closeInventory();
            this.claimNotifier.claimAndNotify(player, kit);
        });

        this.placeButton(inventory, rows, layout.backButton(), this.itemRenderer.renderButton(
            layout.backButton().material(),
            messages.backButtonName(),
            messages.backButtonLore()
        ), backAction);

        inventory.open(player);
    }

    private void placeButton(Gui inventory, int rows, KitConfig.Button button, ItemStack item, Runnable action) {
        if (!button.enabled()) {
            return;
        }

        inventory.setItem(KitGuiLayout.lastRowSlot(rows, button.column()), new GuiItem(item, event -> action.run()));
    }
}
