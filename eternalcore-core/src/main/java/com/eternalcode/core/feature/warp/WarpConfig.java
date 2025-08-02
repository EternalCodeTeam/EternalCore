package com.eternalcode.core.feature.warp;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.time.Duration;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Material;

@Getter
@Accessors(fluent = true)
public class WarpConfig extends OkaeriConfig {
    @Comment("# Time of teleportation to warp")
    public Duration teleportTimeToWarp = Duration.ofSeconds(5);

    @Comment("# Warp inventory should be enabled?")
    public boolean inventoryEnabled = true;

    @Comment("# Warp inventory auto add new warps")
    public boolean autoAddNewWarps = true;

    @Comment({"# Options below allow you to customize item representing warp added to GUI, ",
              "# you can change almost everything inside language files, after the warp has been added to the inventory."})
    public String itemNamePrefix = "&8» &6Warp: &f";

    public String itemLore = "&7Click to teleport!";

    public Material itemMaterial = Material.PLAYER_HEAD;

    @Comment("# Texture of the item (only for PLAYER_HEAD material)")
    public String itemTexture =
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzk4ODVlODIzZmYxNTkyNjdjYmU4MDkwOTNlMzNhNDc2ZTI3NDliNjU5OGNhNGEyYTgxZWU2OTczODAzZmI2NiJ9fX0=";
}
