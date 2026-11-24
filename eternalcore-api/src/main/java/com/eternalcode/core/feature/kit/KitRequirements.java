package com.eternalcode.core.feature.kit;

import java.time.Duration;
import java.util.Collection;
import java.util.Set;
import org.bukkit.GameMode;

/**
 * Conditions checked before a kit can be claimed. Empty collections and {@link Duration#ZERO} mean "no requirement".
 *
 * @param worlds           names of worlds in which the kit can be claimed.
 * @param gameModes        game modes in which the kit can be claimed.
 * @param minimumPlaytime  minimum total playtime of the player.
 */
public record KitRequirements(Set<String> worlds, Set<GameMode> gameModes, Duration minimumPlaytime) {

    private static final KitRequirements NONE = new KitRequirements(Set.of(), Set.of(), Duration.ZERO);

    public KitRequirements {
        if (minimumPlaytime.isNegative()) {
            throw new IllegalArgumentException("minimumPlaytime cannot be negative");
        }

        worlds = Set.copyOf(worlds);
        gameModes = Set.copyOf(gameModes);
    }

    public static KitRequirements none() {
        return NONE;
    }

    public static KitRequirements of(Collection<String> worlds, Collection<GameMode> gameModes, Duration minimumPlaytime) {
        return new KitRequirements(Set.copyOf(worlds), Set.copyOf(gameModes), minimumPlaytime);
    }

    public boolean hasWorldRequirement() {
        return !this.worlds.isEmpty();
    }

    public boolean hasGameModeRequirement() {
        return !this.gameModes.isEmpty();
    }

    public boolean hasPlaytimeRequirement() {
        return !this.minimumPlaytime.isZero();
    }
}
