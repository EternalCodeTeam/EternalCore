/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.configuration;

import net.dzikoysk.cdn.entity.Description;

import java.io.Serializable;

public final class PluginConfiguration implements Serializable {

    @Description("# Useful Event Messages")
    @Description("Set to empty, if you want to delete this message")
    public String deathMessage = "Player {PLAYER} death";
    public String joinMessage = "Welcome {PLAYER}";
    public String leaveMessage = "Goodbye {PLAYER}";

    @Description("# Useful Assets")
    public boolean disableBlockBreaking = false;
    public boolean disableBlockPlacing = false;
    public boolean disableFood = false;
    public boolean clearDropAtDeath = false;
}
