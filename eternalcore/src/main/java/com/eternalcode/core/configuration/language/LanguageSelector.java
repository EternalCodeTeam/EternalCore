package com.eternalcode.core.configuration.language;

import com.eternalcode.core.language.Language;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import org.bukkit.Material;
import panda.utilities.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Contextual
public class LanguageSelector {

    @Description({ StringUtils.EMPTY, "# Name of inventory" })
    public String title = "&6Select a language";

    @Description({ StringUtils.EMPTY, "# Size of inventory" })
    public int rows = 5;

    @Description({ StringUtils.EMPTY, "# Border settings" })
    public Border border = new Border();

    @Contextual
    public static class Border {

        @Description(StringUtils.EMPTY)
        public boolean fill = true;

        @Description(StringUtils.EMPTY)
        public Material material = Material.GRAY_STAINED_GLASS_PANE;

        @Description({ StringUtils.EMPTY, "# TOP, BOTTOM, BORDER, ALL" })
        public FillType type = FillType.BORDER;

        @Description({ StringUtils.EMPTY, "# Name (If you dont want name just set \"\")" })
        public String name = "         &8*";

        @Description({ StringUtils.EMPTY, "# Lore (If you dont want lore just set \"\")" })
        public List<String> lore = Collections.singletonList("&7Lore :D");

        public enum FillType {
            TOP, BOTTOM, BORDER, ALL
        }
    }

    @Description({ StringUtils.EMPTY, "# List of languages" })
    public List<LanguageItem> languageItemMap = new ImmutableList.Builder<LanguageItem>()
        .add(new LanguageItem(
            Material.PLAYER_HEAD,
            Language.EN,
            20,
            "&c&lEnglish",
            Collections.singletonList("&7▪ <gradient:#66ff99:#00ffff>Click to change language!"),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODc5ZDk5ZDljNDY0NzRlMjcxM2E3ZTg0YTk1ZTRjZTdlOGZmOGVhNGQxNjQ0MTNhNTkyZTQ0MzVkMmM2ZjlkYyJ9fX0")
        )

        .add(new LanguageItem(
            Material.REPEATER,
            Language.DEFAULT,
            22,
            "&c&lAutomatyczny",
            Collections.singletonList("&7▪ <gradient:#66ff99:#00ffff>Kliknij, aby pobierać język z ustawień klineta!"))
        )

        .add(new LanguageItem(
            Material.PLAYER_HEAD,
            Language.PL,
            24,
            "&c&lPolish",
            Collections.singletonList("&7▪ <gradient:#66ff99:#00ffff>Kliknij aby zmienić język!"),
            "ewogICJ0aW1lc3RhbXAiIDogMTYyNzMxOTA4NjYyOCwKICAicHJvZmlsZUlkIiA6ICJiNTM5NTkyMjMwY2I0MmE0OWY5YTRlYmYxNmRlOTYwYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJtYXJpYW5hZmFnIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2IxZDFlZmVkNjIyZTEzMTJlOTg0NGU4OTgzNjQzMzM5MGEyMTFjN2E1NTVhMzQzMWI0OTk2NWMzZTNiMzhiYjYiCiAgICB9CiAgfQp9")
        )
        .build();
}
