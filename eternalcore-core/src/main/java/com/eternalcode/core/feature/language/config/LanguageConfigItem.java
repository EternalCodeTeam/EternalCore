package com.eternalcode.core.feature.language.config;

import com.eternalcode.core.configuration.contextual.ConfigItem;
import com.eternalcode.core.feature.language.Language;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LanguageConfigItem extends ConfigItem {

    public String name = "&6Item";
    public List<String> lore = Collections.singletonList("&7Default lore");
    public Material material = Material.PLAYER_HEAD;
    public String texture = "none";
    public boolean glow = false;
    public int slot = 0;
    public List<String> commands = new ArrayList<>();
    public Language language = Language.EN;

    public LanguageConfigItem() {
        super();
    }

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
        this.name = name;
        this.lore = lore;
        this.material = material;
        this.texture = texture;
        this.glow = glow;
        this.slot = slot;
        this.commands = commands;
        this.language = language;
    }
}
