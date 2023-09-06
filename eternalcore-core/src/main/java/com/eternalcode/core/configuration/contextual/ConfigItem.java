package com.eternalcode.core.configuration.contextual;

import com.eternalcode.commons.adventure.AdventureUtil;
import dev.triumphteam.gui.builder.item.BaseItemBuilder;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import java.util.Arrays;
import net.dzikoysk.cdn.entity.Contextual;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.inventory.ItemFlag;

@Contextual
public class ConfigItem {

    public String name = "&6Item";
    public List<String> lore = Collections.singletonList("&7Default lore");
    public Material material = Material.PLAYER_HEAD;
    public String texture = "none";
    public boolean glow = false;
    public List<ItemFlag> flags = new ArrayList<>();
    public int slot = 0;
    public List<String> commands = new ArrayList<>();

    public ConfigItem(String name, List<String> lore, Material material, String texture, boolean glow, List<ItemFlag> flags, int slot, List<String> commands) {
        this.name = name;
        this.lore = lore;
        this.material = material;
        this.texture = texture;
        this.glow = glow;
        this.slot = slot;
        this.commands = commands;
    }

    public ConfigItem() {

    }

    public String name() {
        return this.name;
    }

    public List<String> lore() {
        return this.lore;
    }

    public Material material() {
        return this.material;
    }

    public String texture() {
        return this.texture;
    }

    public boolean glow() {
        return this.glow;
    }

    public int slot() {
        return this.slot;
    }

    public List<String> commands() {
        return this.commands;
    }

    public ItemFlag[] flags() {
        return flags.toArray(value -> new ItemFlag[value]);
    }

    public BaseItemBuilder<?> createItemBuilder(ComponentSerializer<Component, Component, String> serializer) {
        Component name = AdventureUtil.resetItalic(serializer.deserialize(this.name()));

        List<Component> lore = this.lore()
            .stream()
            .map(entry -> AdventureUtil.resetItalic(serializer.deserialize(entry)))
            .toList();

        if (this.material() == Material.PLAYER_HEAD && !this.texture().isEmpty()) {
            return ItemBuilder.skull()
                .name(name)
                .lore(lore)
                .glow(this.glow())
                .flags(this.flags())
                .texture(this.texture());
        }

        return ItemBuilder.from(this.material())
            .name(name)
            .lore(lore)
            .glow(this.glow())
            .flags(this.flags());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final ConfigItem configItem = new ConfigItem();

        public Builder withName(String name) {
            this.configItem.name = name;

            return this;
        }

        public Builder withLore(List<String> lore) {
            this.configItem.lore = lore;

            return this;
        }

        public Builder withMaterial(Material material) {
            this.configItem.material = material;

            return this;
        }

        public Builder withTexture(String texture) {
            this.configItem.texture = texture;

            return this;
        }

        public Builder withGlow(boolean glow) {
            this.configItem.glow = glow;

            return this;
        }

        public Builder withFlags(ItemFlag... flags) {
            configItem.flags.addAll(Arrays.asList(flags));
            return this;
        }

        public Builder withSlot(int slot) {
            this.configItem.slot = slot;

            return this;
        }

        public Builder withCommands(List<String> commands) {
            this.configItem.commands = commands;

            return this;
        }

        public ConfigItem build() {
            return this.configItem;
        }
    }
}
