package com.eternalcode.core.feature.enderchest;

import com.cryptomorin.xseries.XMaterial;
import com.eternalcode.core.util.MaterialUtil;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Material;

@Getter
@Accessors(fluent = true)
public class EnderchestConfig extends OkaeriConfig implements EnderchestSettings {

    @Comment({
        "# Replace the vanilla ender chest with the one from EternalCore, kept in the database",
        "#",
        "# This is the switch for the whole feature, not only for the extra pages. It turns on the custom",
        "# inventory, the page limits per permission and everything in the 'pages' section below.",
        "# With it off nothing else here does anything and players use their plain vanilla ender chest.",
        "#",
        "# WARNING: Make a backup of your database before you turn this on.",
        "# After the next restart or /eternalcore reload everything from the vanilla ender chests is moved",
        "# into the database and the vanilla ones are emptied. This cannot be undone.",
        "# Online players are migrated right away, everyone else when they join the server.",
        "#",
        "# Turning it off later leaves the items in the database and players go back to the vanilla chest."
    })
    public boolean replaceVanillaEnderchest = false;

    @Comment("# Take ender chests away from everyone, vanilla and custom alike")
    public boolean enderchestsBlocked = false;

    @Comment({
        "# Block more than one player from having the same ender chest open at once",
        "# Opening one that somebody else is already viewing closes it for them and has to be repeated",
        "#",
        "# Keep this on when you run Folia, two viewers from different regions share one inventory there,",
        "# which the server does not guard and which can duplicate or destroy items"
    })
    public boolean sharedViewingBlocked = true;

    public PageConfig pages = new PageConfig();

    @Getter
    @Accessors(fluent = true)
    public static class PageConfig extends OkaeriConfig implements PageSettings {

        @Comment({ "# Number of inventory rows on a single page (1-6)",
                "# The last row holds the navigation items, so a page with 3 rows stores 26 items" })
        public int rows = 3;

        @Comment("# {PLAYER} - Chest owner, {PAGE} - Current page, {PAGES} - Available pages")
        public String title = "<dark_gray>» <color:#9d6eef>Ender chest <dark_gray>(<white>{PAGE}<dark_gray>/<white>{PAGES}<dark_gray>)";

        @Comment({ "# How long a player has to wait between page switches, set to 0s to turn it off",
                "# It only limits the navigation items, opening a page with a command is not affected" })
        public Duration switchDelay = Duration.ofSeconds(3);

        @Comment("# How many pages a player gets without any permission from the list below")
        public int defaultLimit = 1;

        @Comment({ "# How many pages a permission grants, 'permission : pages'",
                "# A player with several of them gets the highest number, they do not add up",
                "# Pages that already hold items stay reachable even after the permission is gone" })
        public Map<String, Integer> limits = Map.of(
            "eternalcore.enderchest.vip", 2,
            "eternalcore.enderchest.premium", 4
        );

        @Comment("# Items of the navigation row")
        public NavigationConfig navigation = new NavigationConfig();
    }

    @Getter
    @Accessors(fluent = true)
    public static class NavigationConfig extends OkaeriConfig implements NavigationSettings {

        @Comment({ "# Opens the next page, always placed in the last slot of the inventory",
                "# {PAGE} - Current page, {PAGES} - Available pages, {NEXT} - Next page, {PREVIOUS} - Previous page, {PLAYER} - Chest owner" })
        public ItemConfig nextPage = new ItemConfig(
            MaterialUtil.parseRequired(XMaterial.ARROW),
            "<color:#9d6eef>► <white>Next page",
            List.of("<dark_gray>» <gray>Click to open page <color:#9d6eef>{NEXT}"),
            false
        );

        @Comment({ "# Opens the previous page, placed in the first slot of the last row",
                "# Shown from the second page on, the first page uses that slot for items" })
        public ItemConfig previousPage = new ItemConfig(
            MaterialUtil.parseRequired(XMaterial.ARROW),
            "<color:#9d6eef>◄ <white>Previous page",
            List.of("<dark_gray>» <gray>Click to open page <color:#9d6eef>{PREVIOUS}"),
            false
        );
    }

    @Getter
    @Accessors(fluent = true)
    public static class ItemConfig extends OkaeriConfig implements ItemSettings {

        @Comment("# Material of the item")
        public Material material = MaterialUtil.parseRequired(XMaterial.ARROW);

        @Comment("# Display name, leave empty to keep the material's own name")
        public String name = "";

        @Comment("# Lore lines")
        public List<String> lore = List.of();

        @Comment("# Should the item glow?")
        public boolean glow = false;

        public ItemConfig() {}

        public ItemConfig(Material material, String name, List<String> lore, boolean glow) {
            this.material = material;
            this.name = name;
            this.lore = lore;
            this.glow = glow;
        }
    }
}
