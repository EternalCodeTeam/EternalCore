/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.configuration;

import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import org.bukkit.Sound;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Contextual
public class PluginConfiguration implements Serializable {
    @Description({"# ",
        "# This is the main configuration file for EternalCore.",
        "# ",
        "# if you need help with the configration or have any questions related to EternalCore, join us in our Discord",
        "# ",
        "# Discord: https://dc.eternalcode.pl/",
        "# Website: https://eternalcode.pl/", " "})

    @Description({"# Useful Event Messages", "# Set to empty, if you want to delete this message"})
    public String deathMessage = "Player {PLAYER} death";
    public String joinMessage = "Welcome {PLAYER}";
    public String firstJoinMessage = "Hello {PLAYER}, for the first time on the server!!";
    public String leaveMessage = "Goodbye {PLAYER}";

    @Description({" ", "# Welcome title message"})
    public boolean enableWelcomeTitle = true;
    public String welcomeTitle = "&6EternalCode.pl";
    public String welcomeSubTitle = "&eWelcome back {PLAYER}";

    @Description({" ", "# Awesome sound"})
    public boolean enabledSoundAfterJoin = true;
    public Sound soundAfterJoin = Sound.BLOCK_NOTE_BLOCK_PLING;
    public float soudnAfterJoinVolume = 1.8F;
    public float soundAfterJoinPitch = 1F;

    public boolean enableSoundAfterQuit = true;
    public Sound soundAfterQuit = Sound.BLOCK_NOTE_BLOCK_BASEDRUM;
    public float soundAfterQuitVolume = 1.8F;
    public float soundAfterQuitPitch = 1F;

    public boolean enableSoundAfterChatMessage = true;
    public Sound soundAfterChatMessage = Sound.ENTITY_ITEM_PICKUP;
    public float soundAfterChatMessageVolume = 1.8F;
    public float soundAfterChatMessagePitch = 1F;

    @Description({" ", "# Chat Settings"})
    public double chatSlowMode = 5.0;
    public boolean chatStatue = true;

    @Description({" ", "# Scoreboard Settings"})
    public boolean enableScoreboard = true;
    public int scoreboardRefresh = 20;
    public String scoreboardTitle = "&6&lEternalCode.pl";

    @Description({"", "Scoreboard Style"})
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
        " &f» &7Deaths: &f%statistic_deaths%",
        "",
        " &ewww.eternalcode.pl",
        ""
    );
}
