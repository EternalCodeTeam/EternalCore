package com.eternalcode.core.feature.randomteleport;

import org.bukkit.Location;

/**
 * Filter for validating random teleport locations.
 * <p>
 * Allows external plugins to add custom validation logic for random teleport locations.
 * For example, a plot plugin could implement this to prevent teleporting into claimed plots.
 * <p>
 * Example usage:
 * <pre>{@code
 * public class PlotFilter implements RandomTeleportLocationFilter {
 *     @Override
 *     public boolean isValid(Location location) {
 *         // Check if location is inside a claimed plot
 *         return !plotAPI.isPlotClaimed(location);
 *     }
 *
 *     @Override
 *     public String getFilterName() {
 *         return "PlotFilter";
 *     }
 * }
 *
 * // Register the filter
 * EternalCoreApi api = EternalCoreApiProvider.provide();
 * api.getRandomTeleportService().registerFilter(new PlotFilter());
 * }</pre>
 */
public interface RandomTeleportLocationFilter {

    /**
     * Checks if the given location is valid for random teleportation.
     *
     * @param location The location to validate
     * @return true if the location is valid, false otherwise
     */
    boolean isValid(Location location);

    /**
     * Gets the name of this filter for debugging and logging purposes.
     *
     * @return The filter name
     */
    String getFilterName();
}
