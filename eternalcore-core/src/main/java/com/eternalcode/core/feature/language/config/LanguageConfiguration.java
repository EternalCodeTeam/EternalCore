package com.eternalcode.core.feature.language.config;

import com.eternalcode.core.configuration.AbstractConfigurationFile;
import com.eternalcode.core.feature.language.Language;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import com.google.common.collect.ImmutableList;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.bukkit.Material;

@ConfigurationFile
public class LanguageConfiguration extends AbstractConfigurationFile {

    @Comment(" ")
    public Language defaultLanguage = Language.EN;
    public List<Language> languages = Arrays.asList(Language.EN, Language.PL);

    @Comment(" ")
    public LanguageSelector languageSelector = new LanguageSelector();

    @Override
    public File getConfigFile(File folder) {
        return new File(folder, "language.yml");
    }

    public static class LanguageSelector extends OkaeriConfig {
        @Comment("# Name of inventory")
        public String title = "&6Select a language";

        @Comment({" ", "# Size of inventory"})
        public int rows = 5;

        @Comment({" ", "# Border settings"})
        public Border border = new Border();
        @Comment({" ", "# List of languages"})
        public List<LanguageConfigItem> languageConfigItemMap = List.of(
            new LanguageConfigItem(
                "&c&lEnglish",
                Collections.singletonList("&7▪ <gradient:#66ff99:#00ffff>Click to change language!"),
                Material.PLAYER_HEAD,
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODc5ZDk5ZDljNDY0NzRlMjcxM2E3ZTg0YTk1ZTRjZTdlOGZmOGVhNGQxNjQ0MTNhNTkyZTQ0MzVkMmM2ZjlkYyJ9fX0",
                false,
                20,
                Collections.emptyList(),
                Language.EN
            ),
            new LanguageConfigItem(
                "&c&lAuto",
                Collections.singletonList("&7▪ <gradient:#66ff99:#00ffff>Kliknij, aby pobierać język z ustawień klienta!"),
                Material.REPEATER,
                "none",
                false,
                22,
                Collections.emptyList(),
                Language.DEFAULT
            ),
            new LanguageConfigItem(
                "&c&lPolish",
                Collections.singletonList("&7▪ <gradient:#66ff99:#00ffff>Kliknij aby zmienić język!"),
                Material.PLAYER_HEAD,
                "ewogICJ0aW1lc3RhbXAiIDogMTYyNzMxOTA4NjYyOCwKICAicHJvZmlsZUlkIiA6ICJiNTM5NTkyMjMwY2I0MmE0OWY5YTRlYmYxNmRlOTYwYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJtYXJpYW5hZmFnIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2IxZDFlZmVkNjIyZTEzMTJlOTg0NGU4OTgzNjQzMzM5MGEyMTFjN2E1NTVhMzQzMWI0OTk2NWMzZTNiMzhiYjYiCiAgICB9CiAgfQp9",
                false,
                24,
                Collections.emptyList(),
                Language.PL
            )
        );

        public static class Border extends OkaeriConfig {
            public boolean fill = true;

            @Comment(" ")
            public Material material = Material.GRAY_STAINED_GLASS_PANE;

            @Comment({" ", "# TOP, BOTTOM, BORDER, ALL"})
            public Border.FillType type = Border.FillType.BORDER;

            @Comment({" ", "# Name (If you don't want name just set \"\")"})
            public String name = " ";

            @Comment({" ", "# Lore (If you don't want lore just set [])"})
            public List<String> lore = Collections.emptyList();

            public enum FillType {
                TOP,
                BOTTOM,
                BORDER,
                ALL
            }
        }
    }
}
