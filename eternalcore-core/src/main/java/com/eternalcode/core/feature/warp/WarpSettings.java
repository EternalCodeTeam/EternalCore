package com.eternalcode.core.feature.warp;

import org.bukkit.Material;
import java.time.Duration;

public interface WarpSettings {

    String itemTexture();

    Material itemMaterial();

    String itemLore();

    String itemNamePrefix();

    boolean autoAddNewWarps();

    boolean inventoryEnabled();

    Duration teleportTimeToWarp();

}
