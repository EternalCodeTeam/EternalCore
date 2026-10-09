package com.eternalcode.core.feature.enderchest;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * Paginated ender chests. When the feature is disabled the vanilla ender chest is used and only
 * {@link #openEnderchest(Player)} does anything; when ender chests are blocked nothing opens at all.
 */
public interface EnderchestService {

    /**
     * @return whether ender chests are blocked entirely, which overrides {@link #isVanillaEnderchestReplaced()}
     */
    boolean areEnderchestsBlocked();

    /**
     * @return whether paginated ender chests are enabled in the configuration
     */
    boolean isVanillaEnderchestReplaced();

    /**
     * Opens the player's own ender chest: the first page when the feature is enabled, the vanilla
     * ender chest otherwise. Does nothing while ender chests are blocked.
     *
     * @param player the player to open the chest for
     */
    void openEnderchest(Player player);

    /**
     * Opens a page of the owner's ender chest to the viewer. The owner may be offline. The viewer is
     * notified when the page is beyond the owner's reach. Does nothing when the feature is disabled or
     * ender chests are blocked.
     *
     * @param viewer the player who sees and edits the page
     * @param owner the player whose chest is opened, online or not
     * @param page 1-based page number
     */
    void openEnderchest(Player viewer, OfflinePlayer owner, int page);

    /**
     * @param player the player to evaluate
     * @return number of pages the player's permissions grant, at least 1; pages that already hold items
     *     stay reachable beyond this limit
     */
    int getPageLimit(Player player);
}
