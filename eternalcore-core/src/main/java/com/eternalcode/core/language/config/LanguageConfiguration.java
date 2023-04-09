package com.eternalcode.core.language.config;

import com.eternalcode.core.configuration.ReloadableConfig;
import com.eternalcode.core.language.Language;
import com.google.common.collect.ImmutableList;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Material;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LanguageConfiguration implements ReloadableConfig {

    public Language defaultLanguage = Language.EN;
    public List<Language> languages = Arrays.asList(Language.EN, Language.PL);

    public LanguageSelector languageSelector = new LanguageSelector();

    @Contextual
    public static class LanguageSelector  {
        @Description("# Name of inventory")
        public String title = "&6Select a language";

        @Description({ " ", "# Size of inventory" })
        public int rows = 5;

        @Description({ " ", "# Border settings" })
        public Border border = new Border();

        @Contextual
        public static class Border {
            public boolean fill = true;

            @Description(" ")
            public Material material = Material.GRAY_STAINED_GLASS_PANE;

            @Description({ " ", "# TOP, BOTTOM, BORDER, ALL" })
            public Border.FillType type = Border.FillType.BORDER;

            @Description({ " ", "# Name (If you don't want name just set \"\")" })
            public String name = "         &8*";

            @Description({ " ", "# Lore (If you don't want lore just set \"\")" })
            public List<String> lore = Collections.singletonList("&7Lore :D");

            public enum FillType {
                TOP, BOTTOM, BORDER, ALL
            }
        }

        @Description({ " ", "# List of languages" })
        public List<LanguageConfigItem> languageConfigItemMap = new ImmutableList.Builder<LanguageConfigItem>()
            .add(new LanguageConfigItem(
                Material.PLAYER_HEAD,
                Language.EN,
                20,
                "&c&lEnglish",
                Collections.singletonList("&7▪ <gradient:#66ff99:#00ffff>Click to change language!"),
                "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODc5ZDk5ZDljNDY0NzRlMjcxM2E3ZTg0YTk1ZTRjZTdlOGZmOGVhNGQxNjQ0MTNhNTkyZTQ0MzVkMmM2ZjlkYyJ9fX0")
            )

            .add(new LanguageConfigItem(
                Material.REPEATER,
                Language.DEFAULT,
                22,
                "&c&lAuto",
                Collections.singletonList("&7▪ <gradient:#66ff99:#00ffff>Kliknij, aby pobierać język z ustawień klienta!"))
            )

            .add(new LanguageConfigItem(
                Material.PLAYER_HEAD,
                Language.PL,
                24,
                "&c&lPolish",
                Collections.singletonList("&7▪ <gradient:#66ff99:#00ffff>Kliknij aby zmienić język!"),
                "ewogICJ0aW1lc3RhbXAiIDogMTYyNzMxOTA4NjYyOCwKICAicHJvZmlsZUlkIiA6ICJiNTM5NTkyMjMwY2I0MmE0OWY5YTRlYmYxNmRlOTYwYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJtYXJpYW5hZmFnIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2IxZDFlZmVkNjIyZTEzMTJlOTg0NGU4OTgzNjQzMzM5MGEyMTFjN2E1NTVhMzQzMWI0OTk2NWMzZTNiMzhiYjYiCiAgICB9CiAgfQp9")
            )
            .build();
    }

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "language.yml");
    }

}
