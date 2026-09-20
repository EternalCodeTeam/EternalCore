package com.eternalcode.core.feature.home.inventory;

import com.cryptomorin.xseries.XMaterial;
import com.eternalcode.core.configuration.AbstractConfigurationFile;
import com.eternalcode.core.configuration.contextual.ConfigItem;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import com.eternalcode.core.util.MaterialUtil;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.io.File;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Material;

@Getter
@Accessors(fluent = true)
@ConfigurationFile
public class HomeInventoryConfig extends AbstractConfigurationFile {

    @Comment({
        "# Home inventory (GUI) configuration.",
        "# This file only controls the GUI's appearance. Enable/disable the GUI itself",
        "# in the main config.yml under 'homes.inventoryEnabled'."
    })
    public DisplaySection display = new DisplaySection();

    @Comment("# Border configuration for the home inventory")
    public BorderSection border = new BorderSection();

    @Comment({
        "# Template for a slot representing a home the player has already set.",
        "# {HOME} in the name is replaced with the home's name.",
        "# Note: the 'slot' and 'commands' fields of this item template are not used - slots are computed automatically."
    })
    public ConfigItem occupiedItem = ConfigItem.builder()
        .withName("<color:#9d6eef><bold>{HOME}")
        .withLore(List.of(
            "<dark_gray>▸ <color:#9d6eef>ʟᴘᴍ <gray>- teleportacja",
            "<dark_gray>▸ <color:#9d6eef>sʜɪꜰᴛ + ʟᴘᴍ <gray>- pokaż/ukryj koordynaty",
            "<dark_gray>▸ <color:#9d6eef>ᴘᴘᴍ <gray>- zmień nazwę",
            "<dark_gray>▸ <color:#9d6eef>sʜɪꜰᴛ + ᴘᴘᴍ <gray>- usuń dom"
        ))
        .withMaterial(MaterialUtil.parseRequired(XMaterial.ORANGE_BED))
        .build();

    @Comment({
        "# Extra lore lines appended to an occupied slot only while coordinates are toggled on (SHIFT + left-click).",
        "# Placeholders: {WORLD}, {X}, {Y}, {Z}"
    })
    public List<String> coordsLore = List.of(
        "<gray>Świat: <color:#9d6eef>{WORLD}",
        "<gray>X: <color:#9d6eef>{X} <gray>Y: <color:#9d6eef>{Y} <gray>Z: <color:#9d6eef>{Z}"
    );

    @Comment("# Template for a free slot the player is allowed to set a home in.")
    public ConfigItem availableItem = ConfigItem.builder()
        .withName("<green><bold>ᴡᴏʟɴʏ sʟᴏᴛ")
        .withLore(List.of("<dark_gray>▸ <color:#9d6eef>ʟᴘᴍ <gray>- ustaw dom w tym miejscu"))
        .withMaterial(MaterialUtil.parseRequired(XMaterial.YELLOW_BED))
        .build();

    @Comment("# Template for a slot beyond the player's current permission limit.")
    public ConfigItem lockedItem = ConfigItem.builder()
        .withName("<red><bold>ᴢᴀʙʟᴏᴋᴏᴡᴀɴʏ sʟᴏᴛ")
        .withLore(List.of("<dark_gray>▸ <gray>Nie posiadasz uprawnień do stworzenia tego domu."))
        .withMaterial(MaterialUtil.parseRequired(XMaterial.RED_BED))
        .build();

    @Comment("# Lines shown on the sign when creating a new home in a free slot. First line is where the player types.")
    public List<String> createSignLines = List.of("", "^^^^^^^^", "Wpisz nazwę", "nowego domu");

    @Comment("# Lines shown on the sign when renaming an existing home.")
    public List<String> renameSignLines = List.of("", "^^^^^^^^", "Wpisz nową", "nazwę domu");

    @Override
    public File getConfigFile(File dataFolder) {
        return new File(dataFolder, "home-inventory.yml");
    }

    @Getter
    @Accessors(fluent = true)
    public static class DisplaySection extends OkaeriConfig {
        @Comment("# Title of the home inventory GUI")
        public String title = "<dark_gray>» <color:#9d6eef><bold>ᴅᴏᴍʏ";
    }

    @Getter
    @Accessors(fluent = true)
    public static class BorderSection extends OkaeriConfig {
        @Comment("# Material for border items")
        public Material material = MaterialUtil.parseRequired(XMaterial.PURPLE_STAINED_GLASS_PANE);

        @Comment("# Display name for border items (empty for no name)")
        public String name = "";
    }
}
