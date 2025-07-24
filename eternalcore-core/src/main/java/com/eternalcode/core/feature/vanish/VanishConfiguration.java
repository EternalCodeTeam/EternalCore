package com.eternalcode.core.feature.vanish;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import org.bukkit.ChatColor;

public class VanishConfiguration extends OkaeriConfig {

    @Comment("Is joining with vanish enabled by default?")
    public boolean onJoinWithPerm = true;

    @Comment("Should the player be invulnerable to other players while vanished?")
    public boolean godMode = true;

    @Comment("Should the player has nightVision effect while vanished?")
    public boolean nightVision = true;

    @Comment("Should the player be able to open silent inventory while vanished?")
    public boolean silentOpen = true;

    @Comment("Should the player glowing to other players while vanished?")
    public boolean glowing = true;

    @Comment("Glowing color while vanished")
    public ChatColor color = ChatColor.AQUA;

    @Comment
    @Comment("Block actions while vanished")
    public boolean blockItemDrop = true;
    public boolean blockItemPickup = true;
    public boolean blockFood = true;
    public boolean blockUsingChat = true;
    public boolean blockBreak = true;
    public boolean blockPlace = true;

    // to implement
    public boolean blockInteract = true;
    public boolean blockAttack = true;

}
