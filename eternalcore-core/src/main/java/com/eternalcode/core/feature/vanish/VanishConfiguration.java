package com.eternalcode.core.feature.vanish;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import org.bukkit.ChatColor;

public class VanishConfiguration extends OkaeriConfig {

    @Comment("Should players with vanish permission automatically join in vanish mode?")
    public boolean markVanishOnJoin = true;

    @Comment("Should vanished players be invulnerable to damage from other players?")
    public boolean godMode = true;

    @Comment("Should the player has nightVision effect while vanished?")
    public boolean nightVision = true;

    @Comment("Should vanished players be able to silently view other players' inventories?")
    public boolean silentInventoryAccess = true;

    @Comment("Should vanished players glow to make them visible to other staff members?")
    public boolean glowEffect = true;

    @Comment("Color of the glow effect for vanished players")
    public ChatColor color = ChatColor.AQUA;

    @Comment("Prevent vanished players from dropping items")
    public boolean blockItemDropping = true;

    @Comment("Prevent vanished players from picking up items")
    public boolean blockItemPickup = true;

    @Comment("Prevent vanished players from eating food")
    public boolean blockFoodConsumption = true;

    @Comment("Prevent vanished players from using public chat")
    public boolean blockChatUsage = true;

    @Comment("Prevent vanished players from breaking blocks")
    public boolean blockBlockBreaking = true;

    @Comment("Prevent vanished players from placing blocks")
    public boolean blockBlockPlacing = true;

}
