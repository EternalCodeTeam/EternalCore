package com.eternalcode.core.feature.language;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.commons.adventure.AdventureUtil;
import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.configuration.contextual.ConfigItem;
import com.eternalcode.core.feature.language.config.LanguageConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.feature.language.config.LanguageConfigItem;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
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
    private final Server server;
    private final Scheduler scheduler;
    private final MiniMessage miniMessage;
    private final LanguageService languageService;

    @Inject
    LanguageInventory(
        LanguageConfiguration languageConfiguration,
        TranslationManager translationManager,
        NoticeService noticeService,
        Server server,
        Scheduler scheduler,
        MiniMessage miniMessage,
        LanguageService languageService
    ) {
        this.languageConfiguration = languageConfiguration;
        this.translationManager = translationManager;
        this.noticeService = noticeService;
        this.server = server;
        this.scheduler = scheduler;
        this.miniMessage = miniMessage;
        this.languageService = languageService;
    }

    void open(Player player) {
        this.languageService.getLanguage(player.getUniqueId()).whenComplete((language, throwable) -> {
            if (language == null) {
                language = Language.DEFAULT;
            }

            this.open(player, language);
        });
    }

    private void open(Player player, Language language) {
        LanguageConfiguration.LanguageSelector languageSelector = this.languageConfiguration.languageSelector;
        Translation translation = this.translationManager.getMessages(language);
        Translation.LanguageSection languageSection = translation.language();

        Gui gui = Gui.gui()
            .title(this.miniMessage.deserialize(languageSelector.title))
            .rows(languageSelector.rows)
            .disableAllInteractions()
            .create();

        if (languageSelector.border.fill) {
            ItemBuilder borderItem = ItemBuilder.from(languageSelector.border.material);

            if (!languageSelector.border.name.isEmpty()) {
                borderItem.name(AdventureUtil.resetItalic(this.miniMessage.deserialize(languageSelector.border.name)));
            }

            if (!languageSelector.border.lore.isEmpty()) {
                borderItem.lore(languageSelector.border.lore.stream()
                    .map(entry -> AdventureUtil.resetItalic(this.miniMessage.deserialize(entry)))
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
            BaseItemBuilder<?> baseItemBuilder = this.createItem(languageConfigItem);
            GuiItem guiItem = baseItemBuilder.asGuiItem();

            guiItem.setAction(event -> {
                languageService.setLanguage(player.getUniqueId(), languageConfigItem.language);

                player.closeInventory();

                this.noticeService.create()
                    .player(player.getUniqueId())
                    .notice(translations -> translations.language().languageChanged())
                    .send();
            });

            gui.setItem(languageConfigItem.slot, guiItem);
        }

        for (ConfigItem item : languageSection.decorationItems()) {
            BaseItemBuilder<?> baseItemBuilder = this.createItem(item);
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

        scheduler.run(() -> gui.open(player));
    }

    private BaseItemBuilder<?> createItem(ConfigItem item) {
        Component name = AdventureUtil.resetItalic(this.miniMessage.deserialize(item.name()));
        List<Component> lore = item.lore()
            .stream()
            .map(entry -> AdventureUtil.resetItalic(this.miniMessage.deserialize(entry)))
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
