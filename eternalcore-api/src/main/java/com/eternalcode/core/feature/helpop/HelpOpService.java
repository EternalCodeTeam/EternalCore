package com.eternalcode.core.feature.helpop;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;

/**
 * Service responsible for managing helpop functionality.
 */
public interface HelpOpService {

    /**
     * Marks a player as having sent a helpop request.
     *
     * <p>This allows administrators to reply to the player's request
     * using the helpop reply command.
     *
     * @param playerUuid the UUID of the player who sent the helpop request
     */
    void markSender(@NotNull UUID playerUuid);

    /**
     * Checks if a player has sent a helpop request.
     *
     * <p>Helpop requests expire after a configured duration (default: 1 hour).
     *
     * @param playerUuid the UUID of the player to check
     * @return {@code true} if the player has an active helpop request, {@code false} otherwise
     */
    boolean hasSentHelpOp(@NotNull UUID playerUuid);
}
