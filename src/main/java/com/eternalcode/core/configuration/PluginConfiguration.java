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

public class PluginConfiguration implements Serializable {
    @Description({"# ",
        "# This is the main configuration file for EternalCore.",
        "# ",
        "# if you need help with the configration or have any questions related to EternalCore, join us in our Discord",
        "# ",
        "# Discord: https://dc.eternalcode.pl/",
        "# Website: https://eternalcode.pl/", " "})


    @Description({"", "# Useful Event Messages", "# Set to empty, if you want to delete this message"})
    public eventMessage eventMessage = new eventMessage();

    @Contextual
    public static class eventMessage {
        public String deathMessage = "Player {PLAYER} death";
        public String joinMessage = "Welcome {PLAYER}";
        public String firstJoinMessage = "Hello {PLAYER}, for the first time on the server!!";
        public String leaveMessage = "Goodbye {PLAYER}";
        @Description("")
        public boolean enableWelcomeTitle = true;
        public String welcomeTitle = "&6EternalCode.pl";
        public String welcomeSubTitle = "&eWelcome back {PLAYER}";
    }

    @Description({ "", "# Awesome sounds"})
    public sound sound = new sound();

    @Contextual
    public static class sound {
        public boolean enabledAfterJoin = true;
        public Sound afterJoin = Sound.BLOCK_NOTE_BLOCK_PLING;
        public float afterJoinVolume = 1.8F;
        public float afterJoinPitch = 1F;

        @Description("")
        public boolean enableAfterQuit = true;
        public Sound afterQuit = Sound.BLOCK_NOTE_BLOCK_BASEDRUM;
        public float afterQuitVolume = 1.8F;
        public float afterQuitPitch = 1F;

        @Description("")
        public boolean enableAfterChatMessage = true;
        public Sound afterChatMessage = Sound.ENTITY_ITEM_PICKUP;
        public float afterChatMessageVolume = 1.8F;
        public float afterChatMessagePitch = 1F;
    }

    @Description({" ", "# Chat Section"})
    public chat chat = new chat();

    @Contextual
    public static class chat {
        public double slowMode = 5.0;
        public boolean enabled = true;
        public boolean commandExact = false;
    }

    @Description({ "", "# Formating on/off" })
    public format format = new format();

    @Contextual
    public static class format {
        public String enabled = "&aenabled";
        public String disabled = "&6disabled";
    }

    @Description({ "", "# Scoreboard Section"})
    public scoreboard scoreboard = new scoreboard();

    @Contextual
    public static class scoreboard {
        public boolean enabled = true;
        public int refresh = 20;
        public String title = "&6&lEternalCode.pl";
        public List<String> style = Arrays.asList(
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
}
