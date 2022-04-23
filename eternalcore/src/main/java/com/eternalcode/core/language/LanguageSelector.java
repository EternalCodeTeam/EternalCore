package com.eternalcode.core.language;

import com.google.common.collect.ImmutableMap;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import org.bukkit.Material;

import java.util.Collections;
import java.util.Map;

@Contextual
public class LanguageSelector {

    public String title = "&6Select a language";

    public int rows = 5;

    public Border border = new Border();

    @Contextual
    public static class Border {
        public boolean fill = true;
        public Material material = Material.GRAY_STAINED_GLASS_PANE;

        @Description("# TOP, BOTTOM, BORDER, ALL")
        public FillType type = FillType.BORDER;

        public enum FillType {
            TOP, BOTTOM, BORDER, ALL
        }
    }

    public Map<Integer, LanguageItem> languageItemMap = ImmutableMap.<Integer, LanguageItem>builder()
        .put(1, new LanguageItem(
            Material.PLAYER_HEAD,
            Language.EN,
            21,
            "&c&lEnglish",
            Collections.singletonList("&7▪ <gradient:#66ff99:#00ffff>Click to change language!"),
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODc5ZDk5ZDljNDY0NzRlMjcxM2E3ZTg0YTk1ZTRjZTdlOGZmOGVhNGQxNjQ0MTNhNTkyZTQ0MzVkMmM2ZjlkYyJ9fX0")
        )
        .put(2, new LanguageItem(
            Material.PLAYER_HEAD,
            Language.PL,
            23,
            "&c&lPolish",
            Collections.singletonList("&7▪ <gradient:#66ff99:#00ffff>Kliknij aby zmienić język!"),
            "ewogICJ0aW1lc3RhbXAiIDogMTYyNzMxOTA4NjYyOCwKICAicHJvZmlsZUlkIiA6ICJiNTM5NTkyMjMwY2I0MmE0OWY5YTRlYmYxNmRlOTYwYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJtYXJpYW5hZmFnIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2IxZDFlZmVkNjIyZTEzMTJlOTg0NGU4OTgzNjQzMzM5MGEyMTFjN2E1NTVhMzQzMWI0OTk2NWMzZTNiMzhiYjYiCiAgICB9CiAgfQp9")
        )
        .build();
}
