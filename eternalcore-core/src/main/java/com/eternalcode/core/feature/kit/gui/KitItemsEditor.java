package com.eternalcode.core.feature.kit.gui;

import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.core.feature.kit.KitService;
import com.eternalcode.core.feature.kit.KitSettings;
import com.eternalcode.core.feature.kit.messages.KitMessages;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.multification.shared.Formatter;
import java.util.Arrays;
import java.util.List;
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
    private final TranslationManager translationManager;

    @Inject
    KitItemsEditor(
        KitService kitService,
        KitItemRenderer itemRenderer,
        KitSettings settings,
        KitNotices notices,
        Server server,
        TranslationManager translationManager
    ) {
        this.kitService = kitService;
        this.itemRenderer = itemRenderer;
        this.settings = settings;
        this.notices = notices;
        this.server = server;
        this.translationManager = translationManager;
    }

    public void open(Player player, Kit kit) {
        List<ItemStack> items = kit.items();
        int rows = Math.max(KitGuiLayout.clampRows(this.settings.gui().editorRows()), KitGuiLayout.rowsFor(items.size()));
        String editorTitle = this.translationManager.getMessages().kit().gui().editorTitle();
        String title = this.itemRenderer.deserializeLegacy(new Formatter().register(KitNotices.KIT, kit.displayName()).format(editorTitle));

        KitItemsEditorHolder holder = new KitItemsEditorHolder(kit.name());
        Inventory inventory = this.server.createInventory(holder, KitGuiLayout.slotCount(rows), title);
        holder.attach(inventory);

        inventory.setContents(items.toArray(new ItemStack[0]));
        player.openInventory(inventory);

        this.notices.send(player, KitMessages::itemsEditorOpened, new Formatter().register(KitNotices.KIT_NAME, kit.name()));
    }

    void save(Player player, KitItemsEditorHolder holder) {
        this.kitService.findKit(holder.getKitName()).ifPresent(kit -> {
            Kit updated = kit.toBuilder().items(Arrays.asList(holder.getInventory().getContents())).build();

            this.kitService.saveKit(updated);

            Formatter formatter = new Formatter()
                .register(KitNotices.KIT_NAME, kit.name())
                .register(KitNotices.AMOUNT, updated.items().size());

            this.notices.send(player, KitMessages::itemsSaved, formatter);
        });
    }
}
