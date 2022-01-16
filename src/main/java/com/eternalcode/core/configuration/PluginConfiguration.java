/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.configuration;

import net.dzikoysk.cdn.entity.Description;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public final class PluginConfiguration implements Serializable {
    @Description("# This is the main configuration file for EternalCore.")
    @Description("# ")
    @Description("# if you need help with the configration or have any questions related to EternalCore, join us in our Discord")
    @Description("# ")
    @Description("# Discord: https://dc.eternalcode.pl/")
    @Description("# Website: https://eternalcode.pl/")

    @Description(" ")
    @Description("# Useful Event Messages")
    @Description("# Set to empty, if you want to delete this message")
    public String deathMessage = "Player {PLAYER} death";
    public String joinMessage = "Welcome {PLAYER}";
    public String firstJoinMessage = "Hello {PLAYER}, for the first time on the server!!";
    public String leaveMessage = "Goodbye {PLAYER}";

    @Description(" ")
    @Description("# Welcome title message")
    public boolean enableWelcomeTitle = true;
    public String welcomeTitle = "&6EternalCode.pl";
    public String welcomeSubTitle = "&eWelcome back {PLAYER}";

    @Description(" ")
    @Description("# Chat Settings")
    public double chatSlowMode = 5.0;
    public boolean chatStatue = true;

    @Description(" ")
    @Description("# Useful Assets")
    public boolean disableBlockBreaking = false;
    public boolean disableBlockPlacing = false;
    public boolean disableFood = false;
    public boolean clearDropAtDeath = false;
    public boolean antiDisconectSpam = true;

    @Description(" ")
    @Description("# Scoreboard Settings")
    public boolean enableScoreboard = true;
    public int scoreboardRefresh = 20;
    public String scoreboardTitle = "&6&lEternalCode.pl";

    @Description(" ")
    public List<String> scoreboardStyle = Arrays.asList(
        "",
        " &fProfile:",
        " &f» &7Nickname: &f%player_name%",
        " &f» &7Rank: &f%vault_rank%",
        " &f» &7Ping: &f%player_ping%ms",
        "",
        " &fStatistics:",
        " &f» &7Balance: &f%vault_eco_balance_fixed%$",
        " &f» &7Kills: &f%statistic_player_kills%",
        " &f» &7Śmierci: &f%statistic_deaths%",
        "",
        " &ewww.eternalcode.pl",
        ""
    );
}
