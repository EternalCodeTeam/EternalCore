package com.eternalcode.core.feature.vanish;

import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.ChatColor;

@Getter
@Accessors(fluent = true)
public class VanishConfiguration extends OkaeriConfig implements VanishSettings {

    @Comment("Should players with eternalcore.vanish.join permission automatically join in vanish mode?")
    public boolean silentJoin = false;

    @Comment("Should vanished players be invulnerable to damage from other players?")
    public boolean godMode = true;

    @Comment("Should the player has nightVision effect while vanished?")
    public boolean nightVision = true;

    @Comment("Should vanished players be able to silently view other players' inventories?")
    public boolean silentInventoryAccess = true;

    @Comment("Should vanished players glow to make them visible to other staff members? (needs restart to apply)")
    public boolean glowEffect = true;

    @Comment("Color of the glow effect for vanished players (needs restart to apply)")
    public ChatColor color = ChatColor.LIGHT_PURPLE;

    @Comment("Prevent vanished players from dropping items")
    public boolean blockItemDropping = false;

    @Comment("Prevent vanished players from picking up items")
    public boolean blockItemPickup = true;

    @Comment("Prevent vanished players from hunger loss")
    public boolean blockHungerLoss = true;

    @Comment("Prevent vanished players from using public chat")
    public boolean blockChatUsage = false;

    @Comment("Prevent vanished players from breaking blocks")
    public boolean blockBlockBreaking = false;

    @Comment("Prevent vanished players from placing blocks")
    public boolean blockBlockPlacing = false;

}
