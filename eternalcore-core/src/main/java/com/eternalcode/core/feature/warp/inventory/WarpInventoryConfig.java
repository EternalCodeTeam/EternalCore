package com.eternalcode.core.feature.warp.inventory;

import com.eternalcode.core.configuration.AbstractConfigurationFile;
import com.eternalcode.core.configuration.contextual.ConfigItem;
import com.eternalcode.core.feature.warp.WarpInventoryItem;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Material;

@Getter
@Accessors(fluent = true)
@ConfigurationFile
public class WarpInventoryConfig extends AbstractConfigurationFile {

    @Comment({" ",
              "# Warp inventory configuration",
              "# This file contains the GUI layout and item definitions for the warp inventory",
              "# Text content (titles, names, lore) should be configured in language files"})
    public DisplaySection display = new DisplaySection();

    @Comment({" ",
              "# Border configuration for the warp inventory"})
    public BorderSection border = new BorderSection();

    @Comment({" ",
              "# Decoration items that can be placed in the inventory"})
    public DecorationItemsSection decorationItems = new DecorationItemsSection();

    @Comment({" ",
              "# Warp items configuration - maps warp names to their inventory representation"})
    public Map<String, WarpInventoryItem> items = new HashMap<>();

    @Override
    public File getConfigFile(File dataFolder) {
        return new File(dataFolder, "warp-inventory.yml");
    }

    @Getter
    @Accessors(fluent = true)
    public static class DisplaySection extends OkaeriConfig {
        @Comment("# Title of the warp inventory GUI")
        public String title = "<dark_gray>» <green>Available warps:";
    }

    @Getter
    @Accessors(fluent = true)
    public static class BorderSection extends OkaeriConfig {
        @Comment({" ",
                  "# Changes of border section may affect the appearance of the GUI inventory, after changes adjust slots of existing items."})
        public boolean enabled = true;

        public Material material = Material.GRAY_STAINED_GLASS_PANE;

        public FillType fillType = FillType.BORDER;

        public String name = "";

        public List<String> lore = Collections.emptyList();
    }

    @Getter
    @Accessors(fluent = true)
    public static class DecorationItemsSection extends OkaeriConfig {
        public List<ConfigItem> items = List.of();
    }

    public enum FillType {
        BORDER,
        ALL,
        TOP,
        BOTTOM
    }

    public void setItems(Map<String, WarpInventoryItem> items) {
        this.items = items;
    }

    public void addItem(String warpName, WarpInventoryItem item) {
        this.items.put(warpName, item);
    }

    public WarpInventoryItem removeItem(String warpName) {
        return this.items.remove(warpName);
    }

    public Map<String, WarpInventoryItem> getItems() {
        return this.items;
    }
}
