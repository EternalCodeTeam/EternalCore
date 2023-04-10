package com.eternalcode.core.feature.warp.config;

import net.dzikoysk.cdn.entity.Contextual;
import org.bukkit.Material;

import java.util.Collections;
import java.util.List;

@Contextual
public class WarpConfigItem {

    public String name = "&6Warp";
    public List<String> lore = Collections.singletonList("&7Click to teleport to warp");

    public Material material = Material.PLAYER_HEAD;
    public String texture = "none";
    public boolean glow = true;

    public int slot = 10;

    public WarpConfigItem(String name, List<String> lore, Material material, String texture, boolean glow, int slot) {
        this.name = name;
        this.lore = lore;
        this.material = material;
        this.texture = texture;
        this.glow = glow;
        this.slot = slot;
    }

    public WarpConfigItem() {

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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final WarpConfigItem warpConfigItem = new WarpConfigItem();

        public Builder withName(String name) {
            this.warpConfigItem.name = name;

            return this;
        }

        public Builder withLore(List<String> lore) {
            this.warpConfigItem.lore = lore;

            return this;
        }

        public Builder withMaterial(Material material) {
            this.warpConfigItem.material = material;

            return this;
        }

        public Builder withTexture(String texture) {
            this.warpConfigItem.texture = texture;

            return this;
        }

        public Builder withGlow(boolean glow) {
            this.warpConfigItem.glow = glow;

            return this;
        }

        public Builder withSlot(int slot) {
            this.warpConfigItem.slot = slot;

            return this;
        }

        public WarpConfigItem build() {
            return this.warpConfigItem;
        }
    }
}
