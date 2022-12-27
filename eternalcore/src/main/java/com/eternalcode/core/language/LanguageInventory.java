package com.eternalcode.core.language;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.configuration.language.LanguageConfiguration;
import com.eternalcode.core.configuration.language.LanguageItem;
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

    private final LanguageConfiguration languageConfiguration;
    private final NoticeService noticeService;
    private final UserManager userManager;
    private final MiniMessage miniMessage;

    public LanguageInventory(LanguageConfiguration languageConfiguration, NoticeService noticeService, UserManager userManager, MiniMessage miniMessage) {
        this.languageConfiguration = languageConfiguration;
        this.noticeService = noticeService;
        this.userManager = userManager;
        this.miniMessage = miniMessage;
    }

    public void open(Player player) {
        LanguageConfiguration.LanguageSelector languageSelector = this.languageConfiguration.languageSelector;

        Gui gui = Gui.gui()
            .title(this.miniMessage.deserialize(languageSelector.title))
            .rows(languageSelector.rows)
            .disableAllInteractions()
            .create();

        Option<User> userOption = this.userManager.getUser(player.getUniqueId());

        if (userOption.isEmpty()) {
            return;
        }

        User user = userOption.get();

        if (languageSelector.border.fill) {
            ItemBuilder borderItem = ItemBuilder.from(languageSelector.border.material);

            if (!languageSelector.border.name.equals("")) {
                borderItem.name(this.miniMessage.deserialize(languageSelector.border.name));
            }

            if (!languageSelector.border.lore.isEmpty()) {
                borderItem.lore(languageSelector.border.lore.stream().map(this.miniMessage::deserialize).collect(Collectors.toList()));
            }

            GuiItem guiItem = new GuiItem(borderItem.build());

            switch (languageSelector.border.type) {
                case BORDER -> gui.getFiller().fillBorder(guiItem);
                case ALL -> gui.getFiller().fill(guiItem);
                case TOP -> gui.getFiller().fillTop(guiItem);
                case BOTTOM -> gui.getFiller().fillBottom(guiItem);
            }
        }

        for (LanguageItem languageItem : languageSelector.languageItemMap) {
            List<Component> lore = languageItem.lore
                .stream()
                .map(this.miniMessage::deserialize)
                .collect(Collectors.toList());

            ItemStack item;

            if (languageItem.material == Material.PLAYER_HEAD) {
                item = ItemBuilder.skull()
                    .name(this.miniMessage.deserialize(languageItem.name))
                    .lore(lore)
                    .texture(languageItem.texture)
                    .build();
            } else {
                item = ItemBuilder.from(languageItem.material)
                    .name(this.miniMessage.deserialize(languageItem.name))
                    .lore(lore)
                    .build();
            }

            GuiItem guiItem = new GuiItem(item);

            guiItem.setAction(event -> {
                user.getSettings().setLanguage(languageItem.language);

                player.closeInventory();

                this.noticeService.create()
                    .player(player.getUniqueId())
                    .notice(messages -> messages.other().languageChanged())
                    .send();
            });

            gui.setItem(languageItem.slot, guiItem);
        }

        gui.open(player);
    }
}
