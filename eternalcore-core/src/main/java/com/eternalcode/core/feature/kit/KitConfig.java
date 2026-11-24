package com.eternalcode.core.feature.kit;

import com.cryptomorin.xseries.XMaterial;
import com.eternalcode.core.configuration.AbstractConfigurationFile;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import com.eternalcode.core.util.MaterialUtil;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Exclude;
import eu.okaeri.configs.annotation.Header;
import java.io.File;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;

@Getter
@Accessors(fluent = true)
@ConfigurationFile
@Header({
    "Kit definitions live in the kits/ directory - one <kit name>.yml file per kit.",
    "Create and edit them in-game with /kitadmin or by hand, then use /eternalcore reload.",
    "Kit cooldowns are stored in the database."
})
public class KitConfig extends AbstractConfigurationFile implements KitSettings {

    @Exclude
    private static final String FILE_NAME = "kits.yml";

    @Comment("# Permission assigned to newly created kits: <prefix><kit name>")
    public String defaultPermissionPrefix = "eternalcore.kits.";

    @Comment("# Hide kits the player has no permission for in /kit GUI")
    public boolean hideKitsWithoutPermission = false;

    @Comment("# Kit GUI (MiniMessage format)")
    public GuiSection gui = new GuiSection();

    @Getter
    @Accessors(fluent = true)
    public static class GuiSection extends OkaeriConfig {

        @Comment("# Main /kit GUI")
        public String title = "<dark_gray>» <color:#9d6eef>Kits";
        public int rows = 3;

        public boolean fillEmptySlots = true;
        public Material fillerMaterial = MaterialUtil.parseRequired(XMaterial.GRAY_STAINED_GLASS_PANE);

        @Comment("# Click types: LEFT, RIGHT, SHIFT_LEFT, SHIFT_RIGHT, MIDDLE...")
        public ClickType claimClick = ClickType.LEFT;
        public ClickType previewClick = ClickType.RIGHT;

        @Comment({ "# Lines appended to kit icon lore", "# {COOLDOWN} - remaining cooldown, {KIT_COOLDOWN} - kit cooldown" })
        public List<String> availableLore = List.of(
            "",
            "<gray>Cooldown: <color:#9d6eef>{KIT_COOLDOWN}",
            "<color:#9d6eef>► <white>Available to claim!",
            "",
            "<dark_gray>» <gray>LMB <white>to claim, <gray>RMB <white>to preview"
        );

        public List<String> cooldownLore = List.of(
            "",
            "<gray>Cooldown: <color:#9d6eef>{KIT_COOLDOWN}",
            "<red>✘ <dark_red>Available in: <red>{COOLDOWN}",
            "",
            "<dark_gray>» <gray>RMB <white>to preview"
        );

        public List<String> noPermissionLore = List.of(
            "",
            "<red>✘ <dark_red>You don't have access to this kit!",
            "",
            "<dark_gray>» <gray>RMB <white>to preview"
        );

        @Comment({ "# Preview GUI, buttons are placed in the last row", "# {KIT} - kit display name" })
        public String previewTitle = "<dark_gray>» <color:#9d6eef>Kit <white>{KIT}";

        public Button claimButton = new Button(
            MaterialUtil.parseRequired(XMaterial.LIME_DYE),
            "<color:#9d6eef>► <white>Claim kit",
            List.of("<dark_gray>» <gray>Click to claim this kit"),
            5
        );

        public Button backButton = new Button(
            MaterialUtil.parseRequired(XMaterial.ARROW),
            "<dark_gray>« <white>Back",
            List.of("<dark_gray>» <gray>Click to return to the kit list"),
            3
        );

        @Comment({ "# Admin items editor (/kitadmin items <kit>)", "# {KIT} - kit name" })
        public String editorTitle = "<dark_gray>» <color:#9d6eef>Editing kit: <white>{KIT}";
        public int editorRows = 4;
    }

    @Getter
    @Accessors(fluent = true)
    public static class Button extends OkaeriConfig {

        public boolean enabled = true;
        public Material material = Material.STONE;
        public String name = "";
        public List<String> lore = List.of();

        @Comment("# Column in the last row (0-8)")
        public int column = 0;

        public Button() {
        }

        public Button(Material material, String name, List<String> lore, int column) {
            this.material = material;
            this.name = name;
            this.lore = lore;
            this.column = column;
        }
    }

    @Override
    public File getConfigFile(File dataFolder) {
        return new File(dataFolder, FILE_NAME);
    }
}
