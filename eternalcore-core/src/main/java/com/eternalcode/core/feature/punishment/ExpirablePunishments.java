package com.eternalcode.core.feature.punishment;

import java.util.Optional;

/**
 * Bridge for code that handles every punishment domain at once (history), where kicks are not expirable.
 */
public final class ExpirablePunishments {

    private ExpirablePunishments() {
    }

    public static Optional<ExpirablePunishment> find(Punishment punishment) {
        if (punishment instanceof ExpirablePunishment expirable) {
            return Optional.of(expirable);
        }

        return Optional.empty();
    }
}
