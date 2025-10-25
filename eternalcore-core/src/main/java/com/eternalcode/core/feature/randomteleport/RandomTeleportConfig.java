package com.eternalcode.core.feature.randomteleport;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.time.Duration;
import java.util.EnumSet;
import java.util.Set;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Material;

@Getter
@Accessors(fluent = true)
public class RandomTeleportConfig extends OkaeriConfig implements RandomTeleportSettings {

    @Comment({
        "# Delay before teleportation",
        "# Time to wait before teleporting (player must stand still)",
        "# Movement or damage during this time cancels the teleportation"
    })
    public Duration delay = Duration.ofSeconds(5);

    @Comment({
        "# Cooldown between random teleport uses",
        "# Time players must wait before using /rtp command again",
        "# Prevents spam and reduces server load"
    })
    public Duration cooldown = Duration.ofSeconds(60);

    @Comment({
        "# Type of radius for random teleportation",
        "# WORLD_BORDER_RADIUS - radius based on the world-border size",
        "# STATIC_RADIUS - static radius based on the configuration below"
    })
    public RandomTeleportType radiusType = RandomTeleportType.WORLD_BORDER_RADIUS;

    @Comment({
        "# Static radius configuration for random teleportation",
        "# Uses spawn point as center (set via /setworldspawn)",
        "# Only used when radiusType is set to STATIC_RADIUS",
        "# Ignored when using WORLD_BORDER_RADIUS"
    })
    public RandomTeleportRadiusConfig radius = new RandomTeleportRadiusConfig(-5000, 5000, -5000, 5000);

    @Comment({
        "# Target world for random teleportation",
        "# Leave empty (\"\") to use player's current world",
        "# Specify world name to always teleport to that world"
    })
    public String world = "world_nether";

    @Comment({
        "# Maximum attempts to find a safe teleport location",
        "# Higher values increase chance of finding safe spot but may cause lag",
        "# Recommended: 10-20 attempts"
    })
    public int teleportAttempts = 10;

    @Comment({
        "# Hazardous blocks that players cannot be teleported onto",
        "# These blocks cause damage, suffocation, or other harmful effects",
        "# Players will never be teleported directly onto these blocks",
        "# Add or remove materials as needed for your server"
    })
    public Set<Material> unsafeBlocks = EnumSet.of(
        Material.LAVA,
        Material.WATER,
        Material.CACTUS,
        Material.FIRE,
        Material.COBWEB,
        Material.SWEET_BERRY_BUSH,
        Material.MAGMA_BLOCK,
        Material.BEDROCK,
        Material.TNT,
        Material.SEAGRASS,
        Material.TALL_SEAGRASS,
        Material.BUBBLE_COLUMN,
        Material.POWDER_SNOW,
        Material.WITHER_ROSE
    );

    @Comment({
        "# Safe blocks that players can be teleported into",
        "# These blocks don't cause damage and allow free movement",
        "# Players can safely spawn in these blocks or pass through them",
        "# Includes air, grass, flowers, and other non-solid blocks"
    })
    public Set<Material> airBlocks = EnumSet.of(
        Material.AIR,
        Material.CAVE_AIR,
        Material.SHORT_GRASS,
        Material.TALL_GRASS,
        Material.VINE,
        Material.STRUCTURE_VOID,
        Material.DEAD_BUSH,
        Material.DANDELION,
        Material.POPPY,
        Material.BLUE_ORCHID,
        Material.ALLIUM,
        Material.AZURE_BLUET,
        Material.RED_TULIP,
        Material.ORANGE_TULIP,
        Material.WHITE_TULIP,
        Material.PINK_TULIP,
        Material.OXEYE_DAISY,
        Material.CORNFLOWER,
        Material.LILY_OF_THE_VALLEY,
        Material.SUNFLOWER,
        Material.LILAC,
        Material.ROSE_BUSH,
        Material.PEONY,
        Material.LARGE_FERN,
        Material.RAIL,
        Material.POWERED_RAIL,
        Material.DETECTOR_RAIL,
        Material.ACTIVATOR_RAIL,
        Material.REDSTONE_WIRE,
        Material.WALL_TORCH,
        Material.COMPARATOR,
        Material.REPEATER,
        Material.LEVER,
        Material.STRING,
        Material.SNOW
    );

    @Comment({
        "# Y-coordinate range for random teleportation",
        "# Minimum: -64 (1.18+) or 0 (older versions)",
        "# Maximum: 320 (1.18+) or 256 (older versions)",
        "# Default: 60-160 (surface level, avoiding deep caves and sky)",
        "# Values are automatically adjusted to world height limits"
    })
    public RandomTeleportHeightRange heightRange = RandomTeleportHeightRange.of(60, 160);

}
