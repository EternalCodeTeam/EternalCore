package com.eternalcode.core.feature.language.config;

import com.eternalcode.core.configuration.contextual.ConfigItem;
import com.eternalcode.core.feature.language.Language;
import eu.okaeri.configs.annotation.Include;
import org.bukkit.Material;

import java.util.List;

@Include(ConfigItem.class)
public class LanguageConfigItem extends ConfigItem {

    public Language language = Language.EN;

    public LanguageConfigItem(
        String name,
        List<String> lore,
        Material material,
        String texture,
        boolean glow,
        int slot,
        List<String> commands,
        Language language
    ) {
        super(name, lore, material, texture, glow, slot, commands);
        this.language = language;
    }

    public LanguageConfigItem() {
    }
}
