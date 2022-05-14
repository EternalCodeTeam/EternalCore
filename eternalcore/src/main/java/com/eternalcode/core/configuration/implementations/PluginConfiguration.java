package com.eternalcode.core.configuration.implementations;

import com.eternalcode.core.chat.ChatSettings;
import com.eternalcode.core.configuration.AbstractConfigWithResource;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.entity.Exclude;
import org.bukkit.Sound;
import panda.utilities.StringUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class PluginConfiguration extends AbstractConfigWithResource {


    @Description({
        //                                                                     ️⬇️ THIS CENTER️
        "#############################################################################################################################################",
        "#                                                                                                                                           #",
        "#                                        ____  __                           __  _____                                                       #",
        "#                                       / __/ / /_ ___   ____  ___  ___ _  / / / ___/ ___   ____ ___                                        #",
        "#                                      / _/  / __// -_) / __/ / _ \\/ _ `/ / / / /__  / _ \\ / __// -_)                                     #",
        "#                                     /___/  \\__/ \\__/ /_/   /_//_/\\_,_/ /_/  \\___/  \\___//_/   \\__/                                  #",
        "#                                                                                                                                           #",
        "#                                          This is the main configuration file for EternalCore.                                             #",
        "#                                                                                                                                           #",
        "#                      if you need help with the configuration or have any questions related to EternalCore, join us in our Discord         #",
        "#                                                                                                                                           #",
        "#                                                 Discord: https://dc.eternalcode.pl/                                                       #",
        "#                                                  Website: https://eternalcode.pl/                                                         #",
        "#                                        Source Code: https://github.com/EternalCodeTeam/EternalCore                                        #",
        "#                                                                                                                                           #",
        "#############################################################################################################################################",
    })


    @Description({ StringUtils.EMPTY, "# Database Section" })
    public Database database = new Database();

    @Description({ StringUtils.EMPTY, "# Useful Event Messages", "# Set to empty, if you want to delete this message" })
    public EventMessage eventMessage = new EventMessage();

    @Description({ StringUtils.EMPTY, "# Awesome sounds" })
    public Sounds sound = new Sounds();

    @Description({ StringUtils.EMPTY, "# Chat Section" })
    public Chat chat = new Chat();

    @Description({ StringUtils.EMPTY, "# Formating on/off" })
    public Format format = new Format();

    @Description({ StringUtils.EMPTY, "# Scoreboard Section" })
    public Scoreboard scoreboard = new Scoreboard();

    @Description({ StringUtils.EMPTY, "# Other Sections" })
    public OtherSettings otherSettings = new OtherSettings();

    public PluginConfiguration(File folder, String child) {
        super(folder, child);
    }

    @Contextual
    public static class Database {
        @Description({
            "# Database types",
            "# SQLITE: org.sqlite.SQLiteDataSource",
            "# MYSQL: com.mysql.jdbc.jdbc2.optional.MysqlDataSource",
            "# POSTGRESQL: org.postgresql.ds.PGSimpleDataSource",
            "# H2: org.h2.jdbcx.JdbcDataSource",
            "# All drivers available at: https://github.com/brettwooldridge/HikariCP#popular-datasource-class-names"
        })
        public String databaseType = "org.sqlite.SQLiteDataSource";
        public String databaseName = "database.db";
        public String databaseHost = "localhost";
        public String databasePort = "3306";
        public String databaseUser = "root";
        public String databasePassword = "";
    }


    @Contextual
    public static class OtherSettings {
        @Description({ StringUtils.EMPTY, "# Gamemode Creative on join Requires permission: eternalcore.staff.gamemodejoin" })
        public boolean gamemodeOnJoin = false;

        @Description({ StringUtils.EMPTY, "# Use unsafe enchantments? Allows you to apply custom enchants to various items" })
        public boolean unsafeEnchantments = true;

        @Description({ StringUtils.EMPTY, "# Time in secounds of tpa requests expire" })
        public int tpaRequestExpire = 80;

        @Description({ StringUtils.EMPTY, "# Time in secounds of teleportation time" })
        public int tpaTimer = 10;

    }

    @Contextual
    public static class EventMessage {
        public String deathMessage = "Player {PLAYER} death";
        public String joinMessage = "Welcome {PLAYER}";
        public String firstJoinMessage = "Hello {PLAYER}, for the first time on the server!!";
        public String leaveMessage = "Goodbye {PLAYER}";

        @Description({ StringUtils.EMPTY, "# Welcome Title settings" }) // TODO: Move to language
        public boolean enableWelcomeTitle = true;
        public String welcomeTitle = "&6EternalCode.pl";
        public String welcomeSubTitle = "&eWelcome back {PLAYER}";
    }

    @Contextual
    public static class Sounds {
        @Description("# Do you want enable sound after player join to server?")
        public boolean enabledAfterJoin = true;
        public Sound afterJoin = Sound.BLOCK_NOTE_BLOCK_PLING;
        public float afterJoinVolume = 1.8F;
        public float afterJoinPitch = 1F;

        @Description({ StringUtils.EMPTY, "# Do you want enable sound after player quit server?" })
        public boolean enableAfterQuit = true;
        public Sound afterQuit = Sound.BLOCK_NOTE_BLOCK_BASEDRUM;
        public float afterQuitVolume = 1.8F;
        public float afterQuitPitch = 1F;

        @Description({ StringUtils.EMPTY, "# Do you want enable sound after player send message on chat server?" })
        public boolean enableAfterChatMessage = true;
        public Sound afterChatMessage = Sound.ENTITY_ITEM_PICKUP;
        public float afterChatMessageVolume = 1.8F;
        public float afterChatMessagePitch = 1F;

    }

    @Contextual
    public static class Chat implements ChatSettings {

        @Description({ StringUtils.EMPTY, "# Cooldown time for helpop message in secconds" })
        public double helpopCooldown = 60.0;

        @Description({ StringUtils.EMPTY, "# Custom message for unknow command" })
        public boolean commandExact = false;

        @Description({ StringUtils.EMPTY, "# Cooldowon time for chat in secconds" })
        public double chatDelay = 5.0;
        public boolean chatEnabled = true;

        @Override
        @Exclude
        public boolean isChatEnabled() {
            return this.chatEnabled;
        }

        @Override
        @Exclude
        public void setChatEnabled(boolean chatEnabled) {
            this.chatEnabled = chatEnabled;
        }

        @Override
        @Exclude
        public double getChatDelay() {
            return chatDelay;
        }

        @Override
        @Exclude
        public void setChatDelay(double chatDelay) {
            this.chatDelay = chatDelay;
        }

    }

    @Contextual
    public static class Format {
        public String separator = "&7, ";
        public List<String> amountArgumentStatement = Arrays.asList("1", "8", "16", "32", "64", "100");
    }

    @Contextual
    public static class Scoreboard {
        public boolean enabled = true;
        public int refresh = 20;
        public String title = "&a&lEternalCode.pl";
        public List<String> style = Arrays.asList(
            "",
            " &aProfile:",
            " &f▪ <gradient:#66ff99:#00ffff>Nickname:</gradient> &f%player_name%",
            " &f▪ <gradient:#66ff99:#00ffff>Rank:</gradient> &f%vault_rank%",
            " &f▪ <gradient:#66ff99:#00ffff>Ping:</gradient> &f%player_ping%ms",
            "",
            " &aStatistics:",
            " &f▪ <gradient:#66ff99:#00ffff>Balance:</gradient> &f%vault_eco_balance_fixed%$",
            " &f▪ <gradient:#66ff99:#00ffff>Kills:</gradient> &f%statistic_player_kills%",
            " &f▪ <gradient:#66ff99:#00ffff>Deaths:</gradient> &f%statistic_deaths%",
            "",
            " &awww.eternalcode.pl",
            ""
        );
    }
}
