package com.eternalcode.core.feature.warp;

import com.eternalcode.core.configuration.contextual.ConfigItem;
import java.io.Serializable;
import java.util.Collections;
import org.bukkit.Material;

public class WarpInventoryItem implements Serializable {

    private String warpName;
    private ConfigItem warpItem;

    public WarpInventoryItem() {
        this.warpName = "default";
        this.warpItem = ConfigItem.builder()
                .withName("&6Warp: &fdefault")
                .withLore(Collections.singletonList("&7Click to teleport to warp"))
                .withMaterial(Material.PLAYER_HEAD)
                .withTexture(
                        "ewogICJ0aW1lc3RhbXAiIDogMTY2NDAzNTM1MjUyNCwKICAicHJvZmlsZUlkIiA6ICJjYjIzZWZhOWY1N2U0ZTQyOGE0MDU2OTM4NDlhODAxZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJWMUdHTyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS82MThhZjFiODNhZGZmNzM1MDA3ZmVkMjMwMTkxOWMwYjYzZWJmZTgwZTVkNjFiYTkzN2M5MmViMWVhY2Y2ZDI4IgogICAgfQogIH0KfQ==")
                .withSlot(10)
                .withGlow(true)
                .build();
    }

    public WarpInventoryItem(String warpName, ConfigItem warpItem) {
        this.warpName = warpName;
        this.warpItem = warpItem;
    }

    public String warpName() {
        return this.warpName;
    }

    public WarpInventoryItem warpName(String warpName) {
        this.warpName = warpName;
        return this;
    }

    public ConfigItem warpItem() {
        return this.warpItem;
    }

    public WarpInventoryItem warpItem(ConfigItem warpItem) {
        this.warpItem = warpItem;
        return this;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String warpName = "default";
        private ConfigItem warpItem = ConfigItem.builder()
                .withName("&6Warp: &fdefault")
                .withLore(Collections.singletonList("&7Click to teleport to warp"))
                .withMaterial(Material.PLAYER_HEAD)
                .withTexture(
                        "ewogICJ0aW1lc3RhbXAiIDogMTY2NDAzNTM1MjUyNCwKICAicHJvZmlsZUlkIiA6ICJjYjIzZWZhOWY1N2U0ZTQyOGE0MDU2OTM4NDlhODAxZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJWMUdHTyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS82MThhZjFiODNhZGZmNzM1MDA3ZmVkMjMwMTkxOWMwYjYzZWJmZTgwZTVkNjFiYTkzN2M5MmViMWVhY2Y2ZDI4IgogICAgfQogIH0KfQ==")
                .withSlot(10)
                .withGlow(true)
                .build();

        public Builder warpName(String warpName) {
            this.warpName = warpName;
            return this;
        }

        public Builder warpItem(ConfigItem warpItem) {
            this.warpItem = warpItem;
            return this;
        }

        public WarpInventoryItem build() {
            return new WarpInventoryItem(this.warpName, this.warpItem);
        }
    }
}
