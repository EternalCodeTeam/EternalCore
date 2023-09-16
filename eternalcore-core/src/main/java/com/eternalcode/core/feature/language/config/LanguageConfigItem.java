package com.eternalcode.core.feature.language.config;

import com.eternalcode.core.feature.language.Language;
import net.dzikoysk.cdn.entity.Contextual;
import org.bukkit.Material;

import java.util.Collections;
import java.util.List;

@Contextual
public class LanguageConfigItem {

    public Material material = Material.DIRT;

    public Language language = Language.EN;

    public int slot = 1;

    public String name = "none";

    public List<String> lore = Collections.singletonList("none");

    public String texture = "none";

    public LanguageConfigItem(Material material, Language language, int slot, String name, List<String> lore, String texture) {
        this.material = material;
        this.language = language;
        this.slot = slot;
        this.name = name;
        this.lore = lore;
        this.texture = texture;
    }

    public LanguageConfigItem(Material material, Language language, int slot, String name, List<String> lore) {
        this.material = material;
        this.language = language;
        this.slot = slot;
        this.name = name;
        this.lore = lore;
    }

    public LanguageConfigItem() {

    }
}
