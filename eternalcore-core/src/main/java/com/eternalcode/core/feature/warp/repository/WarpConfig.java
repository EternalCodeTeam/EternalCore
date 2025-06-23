package com.eternalcode.core.feature.warp.repository;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.core.configuration.ReloadableConfig;
import com.eternalcode.core.configuration.contextual.ConfigItem;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Material;

@ConfigurationFile
@Getter
@Accessors(fluent = true)
public class WarpConfig implements ReloadableConfig {

    @Description({
        "# Warp System Configuration",
        "# This file contains all warp locations and their GUI settings.",
        "# You can easily manage your warps here."
    })
    public Map<String, WarpConfigEntry> warps = new HashMap<>();

    @Description({
        " ",
        "# GUI Settings",
        "# Configure the appearance of the warp inventory"
    })
    public WarpGuiSettings guiSettings = new WarpGuiSettings();

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "warps.yml");
    }

    @Contextual
    public static class WarpConfigEntry {
        @Description("# The location of the warp")
        public Position position;
        
        @Description("# Permissions required to use this warp (leave empty for no permissions)")
        public List<String> permissions = new ArrayList<>();
        
        @Description("# GUI item settings for this warp")
        public WarpGuiItem guiItem = new WarpGuiItem();

        public WarpConfigEntry() {
        }

        public WarpConfigEntry(Position position, List<String> permissions) {
            this.position = position;
            this.permissions = permissions;
        }
    }

    @Contextual
    public static class WarpGuiItem {
        @Description("# The name of the warp item in the GUI")
        public String name = "&6Warp: &f{WARP_NAME}";
        
        @Description("# The lore of the warp item in the GUI")
        public List<String> lore = Collections.singletonList("&7Click to teleport to warp");
        
        @Description("# The material of the warp item in the GUI")
        public Material material = Material.PLAYER_HEAD;
        
        @Description("# The texture of the warp item in the GUI (for player heads)")
        public String texture = "ewogICJ0aW1lc3RhbXAiIDogMTY2NDAzNTM1MjUyNCwKICAicHJvZmlsZUlkIiA6ICJjYjIzZWZhOWY1N2U0ZTQyOGE0MDU2OTM4NDlhODAxZiIsCiAgInByb2ZpbGVOYW1lIiA6ICJWMUdHTyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS82MThhZjFiODNhZGZmNzM1MDA3ZmVkMjMwMTkxOWMwYjYzZWJmZTgwZTVkNjFiYTkzN2M5MmViMWVhY2Y2ZDI4IgogICAgfQogIH0KfQ==";
        
        @Description("# The slot of the warp item in the GUI")
        public int slot = 10;
        
        @Description("# Whether the warp item should glow in the GUI")
        public boolean glow = true;
    }

    @Contextual
    public static class WarpGuiSettings {
        @Description("# The title of the warp inventory")
        public String title = "<dark_gray>» <green>Available warps";
        
        @Description("# Whether to enable the border in the warp inventory")
        public boolean borderEnabled = true;
        
        @Description("# The material of the border in the warp inventory")
        public Material borderMaterial = Material.GRAY_STAINED_GLASS_PANE;
        
        @Description("# The fill type of the border in the warp inventory (TOP, BOTTOM, BORDER, ALL)")
        public BorderFillType borderFillType = BorderFillType.BORDER;
        
        @Description("# The name of the border in the warp inventory")
        public String borderName = "";
        
        @Description("# The lore of the border in the warp inventory")
        public List<String> borderLore = Collections.emptyList();
        
        @Description("# Decoration items in the warp inventory")
        public List<ConfigItem> decorationItems = List.of();
        
        public enum BorderFillType {
            TOP, BOTTOM, BORDER, ALL
        }
    }
}
