package com.eternalcode.core.feature.language.config;

import com.eternalcode.core.configuration.contextual.ConfigItem;
import com.eternalcode.core.feature.language.Language;
import net.dzikoysk.cdn.entity.Contextual;
import org.bukkit.Material;

import java.util.List;
import org.bukkit.inventory.ItemFlag;

@Contextual
public class LanguageConfigItem extends ConfigItem {

    public Language language = Language.EN;

    public LanguageConfigItem(String name, List<String> lore, Material material, String texture, boolean glow, List<ItemFlag> flags, int slot, List<String> commands, Language language) {
        super(name, lore, material, texture, glow, flags, slot, commands);
        this.language = language;
    }

    public LanguageConfigItem() {
    }
}
