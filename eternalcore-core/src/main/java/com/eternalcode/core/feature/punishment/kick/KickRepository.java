package com.eternalcode.core.feature.punishment.kick;

public interface KickRepository {

    /**
     * Blocks until the database answers - call it only from an asynchronous context.
     */
    void save(Kick kick);
}
