package com.eternalcode.core.feature.kit;

import com.cryptomorin.xseries.XMaterial;
import com.eternalcode.core.configuration.AbstractConfigurationFile;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import com.eternalcode.core.util.MaterialUtil;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.io.File;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;

@Getter
@Accessors(fluent = true)
@ConfigurationFile
public class KitConfig extends AbstractConfigurationFile implements KitSettings {

    @Comment({
        "# Kits configuration",
        "# Kit definitions live in the kits/ directory - one <kit name>.yml file per kit.",
        "# Create and edit them in-game with /kitadmin or by hand, then use /eternalcore reload.",
        "# Player kit data (claims, cooldowns, one-time usage, buffer) is stored in the database.",
        "# Text content (titles, names, lore) should be configured in language files.",
        " ",
        "# Permission assigned to newly created kits: <prefix><kit name>"
    })
    public String defaultPermissionPrefix = "eternalcore.kits.";

    @Comment("# Hide kits the player has no permission for in /kit GUI")
    public boolean hideKitsWithoutPermission = false;

    @Comment("# Hide already claimed one-time kits (ONE_TIME, FIRST_JOIN) in /kit GUI")
    public boolean hideClaimedOneTimeKits = false;

    @Comment({
        "# What happens when kit items do not fit into the inventory:",
        "# DROP - drop the leftovers on the ground",
        "# CANCEL - do not give the kit until the player makes room",
        "# BUFFER - store the leftovers, the player claims them later with /kit buffer"
    })
    public KitFullInventoryBehavior fullInventoryBehavior = KitFullInventoryBehavior.DROP;

    @Comment("# Notify the player on join that items are waiting in the kit buffer (/kit buffer)")
    public boolean notifyAboutBufferedItems = true;

    @Comment("# Kit GUI layout")
    public GuiSection gui = new GuiSection();

    @Override
    public File getConfigFile(File dataFolder) {
        return new File(dataFolder, "kits.yml");
    }

    @Getter
    @Accessors(fluent = true)
    public static class GuiSection extends OkaeriConfig {

        public int rows = 3;

        public boolean fillEmptySlots = true;
        public Material fillerMaterial = MaterialUtil.parseRequired(XMaterial.GRAY_STAINED_GLASS_PANE);

        @Comment("# Click types: LEFT, RIGHT, SHIFT_LEFT, SHIFT_RIGHT, MIDDLE...")
        public ClickType claimClick = ClickType.LEFT;
        public ClickType previewClick = ClickType.RIGHT;

        @Comment("# Preview GUI buttons, placed in the last row")
        public Button claimButton = new Button(MaterialUtil.parseRequired(XMaterial.LIME_DYE), 5);
        public Button backButton = new Button(MaterialUtil.parseRequired(XMaterial.ARROW), 3);

        @Comment("# Admin items editor (/kitadmin items <kit>)")
        public int editorRows = 4;
    }

    @Getter
    @Accessors(fluent = true)
    public static class Button extends OkaeriConfig {

        public boolean enabled = true;
        public Material material = Material.STONE;

        @Comment("# Column in the last row (0-8)")
        public int column = 0;

        public Button() {
        }

        public Button(Material material, int column) {
            this.material = material;
            this.column = column;
        }
    }
}
