package com.eternalcode.core.feature.warp.config;

import com.eternalcode.core.configuration.contextual.ConfigItem;
import net.dzikoysk.cdn.entity.Contextual;
import org.bukkit.Material;

import java.util.Collections;

@Contextual
public class WarpInventoryItem {

    public String warpName = "default";

    public ConfigItem warpItem = ConfigItem.builder()
        .withName("&6Warp: &fdefault")
        .withLore(Collections.singletonList("&7Click to teleport to warp"))
        .withMaterial(Material.PLAYER_HEAD)
        .withTexture("ewogICJ0aW1lc3RhbXAiIDogMTY2NDAzNTM1MjUyNCwKICAicHJvZmlsZUlkIiA6ICJjYjIzZWZhOWY1N2U0ZTQyOGE0MDU2OTM4NDlhODAxZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJWMUdHTyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS82MThhZjFiODNhZGZmNzM1MDA3ZmVkMjMwMTkxOWMwYjYzZWJmZTgwZTVkNjFiYTkzN2M5MmViMWVhY2Y2ZDI4IgogICAgfQogIH0KfQ==")
        .withSlot(10)
        .withGlow(true)
        .build();

    public WarpInventoryItem(String warpName, ConfigItem warpItem) {
        this.warpName = warpName;
        this.warpItem = warpItem;
    }

    public WarpInventoryItem() {

    }

    public String warpName() {
        return this.warpName;
    }

    public ConfigItem warpItem() {
        return this.warpItem;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String warpName;
        private ConfigItem warpItem;

        public Builder withWarpName(String warpName) {
            this.warpName = warpName;

            return this;
        }

        public Builder withWarpItem(ConfigItem warpItem) {
            this.warpItem = warpItem;

            return this;
        }

        public WarpInventoryItem build() {
            return new WarpInventoryItem(this.warpName, this.warpItem);
        }
    }

}
