/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.configuration.implementations;

import com.eternalcode.core.configuration.AbstractConfigWithResource;
import com.eternalcode.core.language.Language;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import org.bukkit.Sound;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class PluginConfiguration extends AbstractConfigWithResource {

    @Description({ "# ",
        "# This is the main configuration file for EternalCore.",
        "# ",
        "# if you need help with the configration or have any questions related to EternalCore, join us in our Discord",
        "# ",
        "# Discord: https://dc.eternalcode.pl/",
        "# Website: https://eternalcode.pl/", " " })

    @Description({ "", "# Useful Event Messages", "# Set to empty, if you want to delete this message" })
    public EventMessage eventMessage = new EventMessage();
    @Description({ "", "# Awesome sounds" })
    public Sounds sound = new Sounds();
    @Description({ " ", "# Chat Section" })
    public Chat chat = new Chat();
    @Description({ "", "# Formating on/off" })
    public Format format = new Format();
    @Description({ "", "# Scoreboard Section" })
    public Scoreboard scoreboard = new Scoreboard();

    public PluginConfiguration(File folder, String child) {
        super(folder, child);
    }

    @Contextual
    public static class EventMessage {
        public String deathMessage = "Player {PLAYER} death";
        public String joinMessage = "Welcome {PLAYER}";
        public String firstJoinMessage = "Hello {PLAYER}, for the first time on the server!!";
        public String leaveMessage = "Goodbye {PLAYER}";
        @Description("")
        public boolean enableWelcomeTitle = true;
        public String welcomeTitle = "&6EternalCode.pl";
        public String welcomeSubTitle = "&eWelcome back {PLAYER}";
    }

    @Contextual
    public static class Sounds {
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

    @Contextual
    public static class Chat {
        public List<Language> languages = Arrays.asList(Language.EN, Language.PL);
        public double helpopCooldown = 60.0;
        public double slowMode = 5.0;
        public boolean enabled = true;
        public boolean commandExact = false;
    }

    @Contextual
    public static class Format {
        public String enabled = "&aenabled";
        public String disabled = "&6disabled";
    }

    @Contextual
    public static class Scoreboard {
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
