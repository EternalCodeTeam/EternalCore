package com.eternalcode.core.language;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.language.config.LanguageConfigItem;
import com.eternalcode.core.language.config.LanguageConfiguration;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import com.eternalcode.core.util.AdventureUtil;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@FeatureDocs(
    name = "Language Inventory",
    description = "This feature allows you to create a language selector inventory"
)
@Service
class LanguageInventory {

    private final LanguageConfiguration languageConfiguration;
    private final NoticeService noticeService;
    private final UserManager userManager;
    private final MiniMessage miniMessage;

    @Inject
    LanguageInventory(LanguageConfiguration languageConfiguration, NoticeService noticeService, UserManager userManager, MiniMessage miniMessage) {
        this.languageConfiguration = languageConfiguration;
        this.noticeService = noticeService;
        this.userManager = userManager;
        this.miniMessage = miniMessage;
    }

    void open(Player player) {
        LanguageConfiguration.LanguageSelector languageSelector = this.languageConfiguration.languageSelector;

        Gui gui = Gui.gui()
            .title(this.miniMessage.deserialize(languageSelector.title))
            .rows(languageSelector.rows)
            .disableAllInteractions()
            .create();

        Optional<User> userOption = this.userManager.getUser(player.getUniqueId());

        if (userOption.isEmpty()) {
            return;
        }

        User user = userOption.get();

        if (languageSelector.border.fill) {
            ItemBuilder borderItem = ItemBuilder.from(languageSelector.border.material);

            if (!languageSelector.border.name.equals("")) {
                borderItem.name(AdventureUtil.RESET_ITEM.append(this.miniMessage.deserialize(languageSelector.border.name)));
            }

            if (!languageSelector.border.lore.isEmpty()) {
                borderItem.lore(languageSelector.border.lore.stream()
                    .map(entry -> AdventureUtil.RESET_ITEM.append(this.miniMessage.deserialize(entry)))
                    .collect(Collectors.toList()));
            }

            GuiItem guiItem = new GuiItem(borderItem.build());

            switch (languageSelector.border.type) {
                case BORDER -> gui.getFiller().fillBorder(guiItem);
                case ALL -> gui.getFiller().fill(guiItem);
                case TOP -> gui.getFiller().fillTop(guiItem);
                case BOTTOM -> gui.getFiller().fillBottom(guiItem);
                default -> throw new IllegalStateException("Unexpected value: " + languageSelector.border.type);
            }
        }

        for (LanguageConfigItem languageConfigItem : languageSelector.languageConfigItemMap) {
            Component name = AdventureUtil.RESET_ITEM.append(this.miniMessage.deserialize(languageConfigItem.name));

            List<Component> lore = languageConfigItem.lore
                .stream()
                .map(entry -> AdventureUtil.RESET_ITEM.append(this.miniMessage.deserialize(entry)))
                .collect(Collectors.toList());

            ItemStack item;

            if (languageConfigItem.material == Material.PLAYER_HEAD) {
                item = ItemBuilder.skull()
                    .name(name)
                    .lore(lore)
                    .texture(languageConfigItem.texture)
                    .build();
            }
            else {
                item = ItemBuilder.from(languageConfigItem.material)
                    .name(name)
                    .lore(lore)
                    .build();
            }

            GuiItem guiItem = new GuiItem(item);

            guiItem.setAction(event -> {
                user.getSettings().setLanguage(languageConfigItem.language);

                player.closeInventory();

                this.noticeService.create()
                    .player(player.getUniqueId())
                    .notice(translation -> translation.language().languageChanged())
                    .send();
            });

            gui.setItem(languageConfigItem.slot, guiItem);
        }

        gui.open(player);
    }
}
