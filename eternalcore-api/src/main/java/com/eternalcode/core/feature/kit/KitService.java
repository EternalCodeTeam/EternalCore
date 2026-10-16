package com.eternalcode.core.feature.kit;

import java.util.List;
import java.util.Optional;

/**
 * Manages kit definitions stored in {@code plugins/EternalCore/kits/<kit name>.yml}.
 * Reads are served from memory, writes update memory immediately and are persisted in the background.
 */
public interface KitService {

    Optional<Kit> findKit(String name);

    boolean exists(String name);

    /**
     * @return all kits sorted by their GUI slot.
     */
    List<Kit> getKits();

    /**
     * Creates the kit or replaces an existing kit with the same name.
     */
    void saveKit(Kit kit);

    /**
     * Deletes the kit file together with all cooldowns bound to it.
     */
    void deleteKit(String name);

}
