package com.eternalcode.core.feature.randomteleport;

import com.eternalcode.core.delay.DelaySettings;
import java.time.Duration;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import org.bukkit.Material;

@Contextual
public class RandomTeleportSettingsImpl implements RandomTeleportSettings, DelaySettings {

    @Description("# Time to wait for the random teleportation")
    public Duration randomTeleportTime = Duration.ofSeconds(5);

    @Description({
        "# Type of random teleportation,",
        "# WORLD_BORDER_RADIUS - radius based on the world-border size.",
        "# STATIC_RADIUS - static radius based on the configuration.",
    })
    public RandomTeleportType randomTeleportType = RandomTeleportType.WORLD_BORDER_RADIUS;

    @Description({
        "# Radius of random teleportation, this uses for starting point spawn via /setworldspawn.",
        "# If you want to use a static radius, set the type to STATIC_RADIUS and set the radius here.",
        "# If you using WORLD_BORDER_RADIUS, this value will be ignored."
    })
    public RandomTeleportRadiusRepresenterImpl randomTeleportStaticRadius = RandomTeleportRadiusRepresenterImpl.of(5000, 5000, 5000, 5000);
    // For compatibility reasons, it must be named differently than "randomTeleportRadius".
    // Due to limitations in the configuration library, changing the type of an existing field prevents the plugin from enabling.

    @Description("# Teleport to a specific world, if left empty it will teleport to the player's current world")
    public String randomTeleportWorld = "world";

    @Description("# Number of attempts to teleport to a random location")
    public int randomTeleportAttempts = 10;

    @Description({
        "# Unsafe blocks for random teleportation",
        "# These blocks are considered unsafe for players to be teleported onto.",
        "# The list includes blocks that can cause damage, suffocation, or other",
        "# undesirable effects. Ensure that the list is comprehensive to avoid",
        "# teleporting players to hazardous locations."
    })
    public Set<Material> randomTeleportUnsafeBlocks = EnumSet.of(
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
    public Set<Material> randomTeleportAirBlocks = EnumSet.of(
        Material.AIR,
        Material.CAVE_AIR,
        Material.VOID_AIR,
        Material.GRASS,
        Material.TALL_GRASS,
        Material.VINE,
        Material.LEGACY_AIR,
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

    @Description("# Delay to request next random teleportation")
    public Duration randomTeleportDelay = Duration.ofSeconds(60);

    public RandomTeleportHeightRangeRepresenter heightRange = RandomTeleportHeightRangeRepresenter.of(60, 160);

    @Override
    public Duration randomTeleportTime() {
        return this.randomTeleportTime;
    }

    @Override
    public RandomTeleportRadiusRepresenter randomTeleportRadius() {
        return this.randomTeleportStaticRadius;
    }

    @Override
    public RandomTeleportType randomTeleportType() {
        return this.randomTeleportType;
    }

    @Override
    public String randomTeleportWorld() {
        return this.randomTeleportWorld;
    }

    @Override
    public int randomTeleportAttempts() {
        return this.randomTeleportAttempts;
    }

    @Override
    public Set<Material> unsafeBlocks() {
        return Collections.unmodifiableSet(randomTeleportUnsafeBlocks);
    }

    @Override
    public Set<Material> airBlocks() {
        return Collections.unmodifiableSet(randomTeleportAirBlocks);
    }

    @Override
    public RandomTeleportHeightRangeRepresenter heightRange() {
        return this.heightRange;
    }

    @Override
    public Duration delay() {
        return this.randomTeleportDelay;
    }
}

