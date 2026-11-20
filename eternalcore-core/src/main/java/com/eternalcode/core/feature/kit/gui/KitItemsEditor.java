package com.eternalcode.core.feature.kit.gui;

import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.KitService;
import com.eternalcode.core.feature.kit.KitSettings;
import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Service
public class KitItemsEditor {

    private final KitService kitService;
    private final KitItemRenderer itemRenderer;
    private final KitSettings settings;
    private final KitNotices notices;
    private final Server server;

    @Inject
    KitItemsEditor(KitService kitService, KitItemRenderer itemRenderer, KitSettings settings, KitNotices notices, Server server) {
        this.kitService = kitService;
        this.itemRenderer = itemRenderer;
        this.settings = settings;
        this.notices = notices;
        this.server = server;
    }

    public void open(Player player, Kit kit) {
        List<ItemStack> items = kit.items();
        int rows = Math.max(KitGuiLayout.clampRows(this.settings.gui().editorRows()), KitGuiLayout.rowsFor(items.size()));
        String title = this.itemRenderer.deserializeLegacy(this.settings.gui().editorTitle().replace(KitNotices.KIT, kit.name()));

        KitItemsEditorHolder holder = new KitItemsEditorHolder(kit.name());
        Inventory inventory = this.server.createInventory(holder, KitGuiLayout.slotCount(rows), title);
        holder.attach(inventory);

        inventory.setContents(items.toArray(new ItemStack[0]));
        player.openInventory(inventory);

        this.notices.send(player, KitMessages::itemsEditorOpened, Map.of(KitNotices.KIT, kit.name()));
    }

    void save(Player player, KitItemsEditorHolder holder) {
        this.kitService.findKit(holder.getKitName()).ifPresent(kit -> {
            List<ItemStack> items = Arrays.stream(holder.getInventory().getContents())
                .filter(Objects::nonNull)
                .filter(item -> !item.getType().isAir())
                .toList();

            Kit updated = kit.toBuilder().items(items).build();

            this.kitService.saveKit(updated);

            this.notices.send(player, KitMessages::itemsSaved, Map.of(
                KitNotices.KIT, kit.name(),
                KitNotices.AMOUNT, String.valueOf(items.size())
            ));
        });
    }
}
