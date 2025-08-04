package com.eternalcode.core.feature.vanish;

import org.bukkit.ChatColor;

public interface VanishSettings {

    boolean silentJoin();

    boolean godMode();

    boolean nightVision();

    boolean silentInventoryAccess();

    boolean glowEffect();

    ChatColor color();

    boolean blockItemDropping();

    boolean blockItemPickup();

    boolean blockHungerLoss();

    boolean blockChatUsage();

    boolean blockBlockBreaking();

    boolean blockBlockPlacing();
}
