package com.eternalcode.core.feature.kit.gui;

import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitClaimNotifier;
import com.eternalcode.core.feature.kit.KitClaimResult;
import com.eternalcode.core.feature.kit.KitClaimService;
import com.eternalcode.core.feature.kit.KitClaimStatus;
import com.eternalcode.core.feature.kit.KitConfig;
import com.eternalcode.core.feature.kit.KitDisplayFormatter;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.KitService;
import com.eternalcode.core.feature.kit.KitSettings;
import com.eternalcode.core.feature.kit.KitType;
import com.eternalcode.core.feature.kit.KitUsageService;
import com.eternalcode.core.feature.kit.messages.KitGuiMessages;
import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.multification.shared.Formatter;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@Service
public class KitInventory {

    private final KitService kitService;
    private final KitClaimService claimService;
    private final KitUsageService usageService;
    private final KitClaimNotifier claimNotifier;
    private final KitPreviewInventory previewInventory;
    private final KitItemRenderer itemRenderer;
    private final KitDisplayFormatter displayFormatter;
    private final KitSettings settings;
    private final KitNotices notices;
    private final TranslationManager translationManager;

    @Inject
    KitInventory(
        KitService kitService,
        KitClaimService claimService,
        KitUsageService usageService,
        KitClaimNotifier claimNotifier,
        KitPreviewInventory previewInventory,
        KitItemRenderer itemRenderer,
        KitDisplayFormatter displayFormatter,
        KitSettings settings,
        KitNotices notices,
        TranslationManager translationManager
    ) {
        this.kitService = kitService;
        this.claimService = claimService;
        this.usageService = usageService;
        this.claimNotifier = claimNotifier;
        this.previewInventory = previewInventory;
        this.itemRenderer = itemRenderer;
        this.displayFormatter = displayFormatter;
        this.settings = settings;
        this.notices = notices;
        this.translationManager = translationManager;
    }

    public void open(Player player) {
        List<Kit> visibleKits = this.kitService.getKits().stream()
            .filter(kit -> this.isVisible(player, kit))
            .toList();

        if (visibleKits.isEmpty()) {
            this.notices.send(player, KitMessages::noKits);
            return;
        }

        this.create(player, visibleKits).open(player);
    }

    private boolean isVisible(Player player, Kit kit) {
        if (this.settings.hideKitsWithoutPermission() && !player.hasPermission(kit.permission())) {
            return false;
        }

        if (this.settings.hideClaimedOneTimeKits() && kit.type() != KitType.RECURRING) {
            return !this.usageService.getUsage(player.getUniqueId(), kit).isClaimed();
        }

        return true;
    }

    private Gui create(Player player, List<Kit> kits) {
        KitConfig.GuiSection layout = this.settings.gui();
        KitGuiMessages messages = this.translationManager.getMessages().kit().gui();
        int rows = KitGuiLayout.clampRows(layout.rows());
        int slotCount = KitGuiLayout.slotCount(rows);

        Gui inventory = Gui.gui()
            .title(this.itemRenderer.deserialize(messages.title()))
            .rows(rows)
            .disableAllInteractions()
            .create();

        for (Kit kit : kits) {
            if (kit.slot() >= slotCount) {
                continue;
            }

            GuiItem guiItem = new GuiItem(
                this.renderIcon(player, kit, messages),
                event -> this.handleClick(player, kit, event.getClick())
            );

            inventory.setItem(kit.slot(), guiItem);
        }

        if (layout.fillEmptySlots()) {
            inventory.getFiller().fill(new GuiItem(this.itemRenderer.renderFiller(layout.fillerMaterial())));
        }

        return inventory;
    }

    private ItemStack renderIcon(Player player, Kit kit, KitGuiMessages messages) {
        KitClaimResult result = this.claimService.canClaim(player, kit);
        List<String> lore = new ArrayList<>(messages.kitLore());
        lore.add(result.status() == KitClaimStatus.SUCCESS ? messages.claimHint() : messages.previewHint());

        Formatter formatter = new Formatter()
            .register(KitNotices.TYPE, this.displayFormatter.type(messages, kit.type()))
            .register(KitNotices.KIT_COOLDOWN, this.displayFormatter.cooldown(messages, kit))
            .register(KitNotices.CLAIMS, this.usageService.getUsage(player.getUniqueId(), kit).claimCount())
            .register(KitNotices.STATUS, this.displayFormatter.status(messages, result));

        return this.itemRenderer.renderKitIcon(kit, lore, formatter);
    }

    private void handleClick(Player player, Kit kit, ClickType clickType) {
        KitConfig.GuiSection layout = this.settings.gui();

        if (clickType == layout.claimClick()) {
            player.closeInventory();
            this.claimNotifier.claimAndNotify(player, kit);
            return;
        }

        if (clickType == layout.previewClick()) {
            this.previewInventory.open(player, kit, () -> this.open(player));
        }
    }
}
