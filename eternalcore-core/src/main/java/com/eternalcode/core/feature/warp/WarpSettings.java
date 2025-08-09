package com.eternalcode.core.feature.warp;

public interface WarpSettings {
    String itemTexture();
    org.bukkit.Material itemMaterial();
    String itemLore();
    String itemNamePrefix();
    boolean autoAddNewWarps();
    boolean inventoryEnabled();
    java.time.Duration teleportTimeToWarp();
}
