package com.eternalcode.core.inventory;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.configuration.implementations.inventories.LanguageSelector;
import com.eternalcode.core.configuration.implementations.inventories.item.LanguageItem;
import com.eternalcode.core.language.Language;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import panda.std.Option;

import java.util.List;
import java.util.stream.Collectors;

public class LanguageInventory {

    private final LanguageSelector languageSelector;
    private final NoticeService noticeService;
    private final UserManager userManager;
    private final MiniMessage miniMessage;

    public LanguageInventory(LanguageSelector languageSelector, NoticeService noticeService, UserManager userManager, MiniMessage miniMessage) {
        this.languageSelector = languageSelector;
        this.noticeService = noticeService;
        this.userManager = userManager;
        this.miniMessage = miniMessage;
    }

    public void open(Player player) {
        Gui gui = Gui.gui()
            .title(this.miniMessage.deserialize(this.languageSelector.title))
            .rows(this.languageSelector.rows)
            .disableAllInteractions()
            .create();

        Option<User> userOption = this.userManager.getUser(player.getUniqueId());

        if (userOption.isEmpty()) {
            return;
        }

        User user = userOption.get();

        if (this.languageSelector.border.fill) {
            ItemStack borderItem = new ItemStack(this.languageSelector.border.material);
            GuiItem guiItem = new GuiItem(borderItem);

            switch (this.languageSelector.border.type) {
                case BORDER -> gui.getFiller().fillBorder(guiItem);
                case ALL -> gui.getFiller().fill(guiItem);
                case TOP -> gui.getFiller().fillTop(guiItem);
                case BOTTOM -> gui.getFiller().fillBottom(guiItem);
            }
        }

        for (LanguageItem languageItem : this.languageSelector.languageItemMap.values()) {
            List<Component> lore = languageItem.getLore()
                .stream()
                .map(this.miniMessage::deserialize)
                .collect(Collectors.toList());

            ItemStack item;

            if (languageItem.getMaterial() == Material.PLAYER_HEAD) {
                item = ItemBuilder.skull()
                    .name(this.miniMessage.deserialize(languageItem.getName()))
                    .lore(lore)
                    .texture(languageItem.getTexture())
                    .build();
            } else {
                item = ItemBuilder.from(languageItem.getMaterial())
                    .name(this.miniMessage.deserialize(languageItem.getName()))
                    .lore(lore)
                    .build();
            }

            GuiItem guiItem = new GuiItem(item);

            guiItem.setAction(event -> {
                user.getSettings().setLanguage(Language.fromLocate(languageItem.getLocale()));

                player.closeInventory();

                this.noticeService.notice()
                    .player(player.getUniqueId())
                    .message(messages -> messages.other().languageChanged())
                    .placeholder("{LANGUAGE}", languageItem.getLocaleName())
                    .send();

            });

            gui.setItem(languageItem.getSlot(), guiItem);
        }

        gui.open(player);
    }
}
