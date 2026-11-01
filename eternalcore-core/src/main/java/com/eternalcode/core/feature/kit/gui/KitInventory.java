package com.eternalcode.core.feature.kit.gui;

import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitClaimNotifier;
import com.eternalcode.core.feature.kit.KitConfig;
import com.eternalcode.core.feature.kit.KitCooldownService;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.KitService;
import com.eternalcode.core.feature.kit.KitSettings;
import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.util.DurationUtil;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@Service
public class KitInventory {

    private static final String KIT_COOLDOWN_PLACEHOLDER = "{KIT_COOLDOWN}";

    private final KitService kitService;
    private final KitCooldownService cooldownService;
    private final KitClaimNotifier claimNotifier;
    private final KitPreviewInventory previewInventory;
    private final KitItemRenderer itemRenderer;
    private final KitSettings settings;
    private final KitNotices notices;

    @Inject
    KitInventory(
        KitService kitService,
        KitCooldownService cooldownService,
        KitClaimNotifier claimNotifier,
        KitPreviewInventory previewInventory,
        KitItemRenderer itemRenderer,
        KitSettings settings,
        KitNotices notices
    ) {
        this.kitService = kitService;
        this.cooldownService = cooldownService;
        this.claimNotifier = claimNotifier;
        this.previewInventory = previewInventory;
        this.itemRenderer = itemRenderer;
        this.settings = settings;
        this.notices = notices;
    }

    public void open(Player player) {
        List<Kit> visibleKits = this.kitService.getKits().stream()
            .filter(kit -> !this.settings.hideKitsWithoutPermission() || player.hasPermission(kit.permission()))
            .toList();

        if (visibleKits.isEmpty()) {
            this.notices.send(player, KitMessages::noKits);
            return;
        }

        Map<String, Duration> cooldowns = this.cooldownService.getRemainingCooldowns(player.getUniqueId());
        this.create(player, visibleKits, cooldowns).open(player);
    }

    private Gui create(Player player, List<Kit> kits, Map<String, Duration> cooldowns) {
        KitConfig.GuiSection gui = this.settings.gui();
        int rows = KitGuiLayout.clampRows(gui.rows());
        int slotCount = KitGuiLayout.slotCount(rows);

        Gui inventory = Gui.gui()
            .title(this.itemRenderer.deserialize(gui.title()))
            .rows(rows)
            .disableAllInteractions()
            .create();

        for (Kit kit : kits) {
            if (kit.slot() >= slotCount) {
                continue;
            }

            GuiItem guiItem = new GuiItem(
                this.renderIcon(player, kit, cooldowns.get(kit.name())),
                event -> this.handleClick(player, kit, event.getClick())
            );

            inventory.setItem(kit.slot(), guiItem);
        }

        if (gui.fillEmptySlots()) {
            inventory.getFiller().fill(new GuiItem(this.itemRenderer.renderFiller(gui.fillerMaterial())));
        }

        return inventory;
    }

    private ItemStack renderIcon(Player player, Kit kit, Duration remainingCooldown) {
        KitConfig.GuiSection gui = this.settings.gui();
        Map<String, String> placeholders = Map.of(
            KitNotices.COOLDOWN, remainingCooldown == null ? "" : DurationUtil.format(remainingCooldown, true),
            KIT_COOLDOWN_PLACEHOLDER, DurationUtil.format(kit.cooldown(), true)
        );

        List<String> statusLore;

        if (!player.hasPermission(kit.permission())) {
            statusLore = gui.noPermissionLore();
        }
        else if (remainingCooldown != null) {
            statusLore = gui.cooldownLore();
        }
        else {
            statusLore = gui.availableLore();
        }

        return this.itemRenderer.renderKitIcon(kit, statusLore, placeholders);
    }

    private void handleClick(Player player, Kit kit, ClickType clickType) {
        KitConfig.GuiSection gui = this.settings.gui();

        if (clickType == gui.claimClick()) {
            player.closeInventory();
            this.claimNotifier.claimAndNotify(player, kit);
            return;
        }

        if (clickType == gui.previewClick()) {
            this.previewInventory.open(player, kit, () -> this.open(player));
        }
    }
}
