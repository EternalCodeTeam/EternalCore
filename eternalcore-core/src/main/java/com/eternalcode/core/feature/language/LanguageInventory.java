package com.eternalcode.core.feature.language;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.configuration.contextual.ConfigItem;
import com.eternalcode.core.feature.language.config.LanguageConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.feature.language.config.LanguageConfigItem;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import com.eternalcode.core.util.AdventureUtil;
import dev.triumphteam.gui.builder.item.BaseItemBuilder;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;

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
    private final TranslationManager translationManager;
    private final NoticeService noticeService;
    private final UserManager userManager;
    private final Server server;
    private final MiniMessage miniMessage;

    @Inject
    LanguageInventory(LanguageConfiguration languageConfiguration, TranslationManager translationManager, NoticeService noticeService, UserManager userManager, Server server, MiniMessage miniMessage) {
        this.languageConfiguration = languageConfiguration;
        this.translationManager = translationManager;
        this.noticeService = noticeService;
        this.userManager = userManager;
        this.server = server;
        this.miniMessage = miniMessage;
    }

    void open(Player player, Language language) {
        LanguageConfiguration.LanguageSelector languageSelector = this.languageConfiguration.languageSelector;
        Translation translation = this.translationManager.getMessages(language);
        Translation.LanguageSection languageSection = translation.language();

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
            BaseItemBuilder baseItemBuilder = this.createItem(languageConfigItem);
            GuiItem guiItem = baseItemBuilder.asGuiItem();

            guiItem.setAction(event -> {
                user.getSettings().setLanguage(languageConfigItem.language);

                player.closeInventory();

                this.noticeService.create()
                    .player(player.getUniqueId())
                    .notice(translations -> translations.language().languageChanged())
                    .send();
            });

            gui.setItem(languageConfigItem.slot, guiItem);
        }

        for (ConfigItem item : languageSection.decorationItems()) {
            BaseItemBuilder baseItemBuilder = this.createItem(item);
            GuiItem guiItem = baseItemBuilder.asGuiItem();

            guiItem.setAction(event -> {
                if (item.commands.isEmpty()) {
                    return;
                }

                for (String command : item.commands) {
                    this.server.dispatchCommand(player, command);
                }

                player.closeInventory();
            });

            gui.setItem(item.slot(), guiItem);
        }

        gui.open(player);
    }

    private BaseItemBuilder createItem(ConfigItem item) {
        Component name = AdventureUtil.RESET_ITEM.append(this.miniMessage.deserialize(item.name()));
        List<Component> lore = item.lore()
            .stream()
            .map(entry -> AdventureUtil.RESET_ITEM.append(this.miniMessage.deserialize(entry)))
            .toList();

        if (item.material() == Material.PLAYER_HEAD && !item.texture().isEmpty()) {
            return ItemBuilder.skull()
                .name(name)
                .lore(lore)
                .texture(item.texture())
                .glow(item.glow());
        }

        return ItemBuilder.from(item.material())
            .name(name)
            .lore(lore)
            .glow(item.glow());
    }
}
