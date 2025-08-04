package com.eternalcode.core.feature.vanish;

public interface VanishSettings {

    boolean silentJoin();

    boolean godMode();

    boolean nightVision();

    boolean silentInventoryAccess();

    boolean glowEffect();

    org.bukkit.ChatColor color();

    boolean blockItemDropping();

    boolean blockItemPickup();

    boolean blockHungerLoss();

    boolean blockChatUsage();

    boolean blockBlockBreaking();

    boolean blockBlockPlacing();
}
