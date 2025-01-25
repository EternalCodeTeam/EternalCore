package com.eternalcode.core.feature.randomteleport;

import com.eternalcode.core.configuration.migration.Migration;
import java.time.Duration;
import java.util.EnumSet;
import java.util.Set;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import org.bukkit.Material;

@Contextual
public class RandomTeleportSettingsImpl implements RandomTeleportSettings, Migration {

    @Description("# Delay to wait for the random teleportation")
    public Duration delay = Duration.ofSeconds(5);

    @Description("# Cooldown for random teleportation")
    public Duration cooldown = Duration.ofSeconds(60);
    @Deprecated public Duration randomTeleportDelay = null;

    @Description({
        "# Type of radius for random teleportation",
        "# WORLD_BORDER_RADIUS - radius based on the world-border size.",
        "# STATIC_RADIUS - static radius based on the configuration.",
    })
    public RandomTeleportType radiusType = RandomTeleportType.WORLD_BORDER_RADIUS;
    @Deprecated public RandomTeleportType randomTeleportType = null;

    @Description({
        "# Radius of random teleportation, this uses for starting point spawn via /setworldspawn.",
        "# If you want to use a static radius, set the type to STATIC_RADIUS and set the radius here.",
        "# If you using WORLD_BORDER_RADIUS, this value will be ignored."
    })
    public RandomTeleportRadiusConfig radius = new RandomTeleportRadiusConfig(-5000, 5000, -5000, 5000);
    @Deprecated public Integer randomTeleportRadius = null;

    @Description("# Teleport to a specific world, if left empty it will teleport to the player's current world")
    public String world = "world";
    @Deprecated public String randomTeleportWorld = null;

    @Description("# Number of attempts to find a safe location for random teleportation")
    public int teleportAttempts = 10;
    @Deprecated  public Integer randomTeleportAttempts = null;

    @Description({
        "# Unsafe blocks for random teleportation",
        "# These blocks are considered unsafe for players to be teleported onto.",
        "# The list includes blocks that can cause damage, suffocation, or other",
        "# undesirable effects. Ensure that the list is comprehensive to avoid",
        "# teleporting players to hazardous locations."
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

    @Description({
        "# Air blocks for random teleportation",
        "# These blocks are considered safe for players to be teleported into.",
        "# The list includes blocks that do not cause damage or impede movement.",
        "# Ensure that the list is comprehensive to avoid teleporting players",
        "# into solid or hazardous blocks."
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
        Material.LEGACY_GRASS,
        Material.LEGACY_LONG_GRASS,
        Material.LEGACY_DEAD_BUSH,
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

    @Description({
        "# Height range for random teleportation",
        "# - Minimum: -64 (1.18+) or 0 (older versions)",
        "# - Maximum: 320 (1.18+) or 256 (older versions)",
        "# - Default range: 60-160 blocks",
        "# Note: Values are automatically capped to world height limits"
    })
    public RandomTeleportHeightRange heightRange = RandomTeleportHeightRange.of(60, 160);

    @Override
    public Duration delay() {
        return this.delay;
    }

    @Override
    public RandomTeleportRadius radius() {
        return this.radius;
    }

    @Override
    public RandomTeleportType radiusType() {
        return this.radiusType;
    }

    @Override
    public String world() {
        return this.world;
    }

    @Override
    public int teleportAttempts() {
        return this.teleportAttempts;
    }

    @Override
    public Set<Material> unsafeBlocks() {
        return unsafeBlocks;
    }

    @Override
    public Set<Material> airBlocks() {
        return airBlocks;
    }

    @Override
    public RandomTeleportHeightRange heightRange() {
        return this.heightRange;
    }

    @Override
    public Duration cooldown() {
        return this.cooldown;
    }

    @Override
    @Deprecated(since = "1.5.0", forRemoval = true)
    public boolean migrate() {
        boolean migrated = false;
        if (randomTeleportDelay != null) {
            cooldown = randomTeleportDelay;
            randomTeleportDelay = null;
            migrated = true;
        }
        if (randomTeleportType != null) {
            radiusType = randomTeleportType;
            randomTeleportType = null;
            migrated = true;
        }
        if (randomTeleportRadius != null) {
            radius = new RandomTeleportRadiusConfig(-randomTeleportRadius, randomTeleportRadius, -randomTeleportRadius, randomTeleportRadius);
            randomTeleportRadius = null;
            migrated = true;
        }
        if (randomTeleportWorld != null) {
            world = randomTeleportWorld;
            randomTeleportWorld = null;
            migrated = true;
        }
        if (randomTeleportAttempts != null) {
            teleportAttempts = randomTeleportAttempts;
            randomTeleportAttempts = null;
            migrated = true;
        }
        return migrated;
    }

}

