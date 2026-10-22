package com.eternalcode.core.feature.punishment.kick;

import com.eternalcode.core.feature.punishment.Punishment;

/**
 * Kick is instantaneous: it never expires and cannot be revoked. Created only by {@link KickService}.
 */
public interface Kick extends Punishment {

    /**
     * @return true when the kick was part of /kickall
     */
    boolean massKick();
}
