package com.eternalcode.core.configuration.implementations.inventories.item;

import net.dzikoysk.cdn.entity.Contextual;
import org.bukkit.Material;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Contextual
public class LanguageItem {

    public Material material = Material.DIRT;

    public String locale = "none";
    public String localeName = "none";

    public int slot = 1;

    public String name = "none";

    public List<String> lore = Collections.singletonList("none");

    public String texture = "none";

    public LanguageItem(Material material, Locale locale, String localeName, int slot, String name, List<String> lore, String texture) {
        this.material = material;
        this.locale = locale.getLanguage();
        this.localeName = localeName;
        this.slot = slot;
        this.name = name;
        this.lore = lore;
        this.texture = texture;
    }

    public LanguageItem() {

    }

    public Material getMaterial() {
        return this.material;
    }

    public Locale getLocale() {
        return new Locale(this.locale);
    }

    public int getSlot() {
        return this.slot;
    }

    public String getName() {
        return this.name;
    }

    public String getLocaleName() {
        return localeName;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public String getTexture() {
        return this.texture;
    }
}
