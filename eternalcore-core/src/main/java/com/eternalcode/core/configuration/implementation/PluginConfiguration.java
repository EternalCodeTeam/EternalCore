package com.eternalcode.core.configuration.implementation;

import com.eternalcode.core.configuration.ReloadableConfig;
import com.eternalcode.core.database.DatabaseType;
import com.eternalcode.core.afk.AfkSettings;
import com.eternalcode.core.feature.automessage.AutoMessageSettings;
import com.eternalcode.core.feature.chat.ChatSettings;
import com.eternalcode.core.feature.spawn.SpawnSettings;
import com.eternalcode.core.teleport.request.TeleportRequestSettings;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.entity.Exclude;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.Sound;

import java.io.File;
import java.time.Duration;
import java.util.Map;

public class PluginConfiguration implements ReloadableConfig {

    @Description({
        "#",
        "# This is the main configuration file for EternalCore.",
        "#",
        "# If you need help with the configuration or have any questions related to EternalCore, join us in our discord, or create an issue on our GitHub.",
        "#",
        "# Issues: https://github.com/EternalCodeTeam/EternalCore/issues",
        "# Discord: https://discord.gg/FQ7jmGBd6c",
        "# Website: https://eternalcode.pl/",
        "# Source Code: https://github.com/EternalCodeTeam/EternalCore",
        "#",
    })

    @Description({ " ", "# Database Section" })
    public Database database = new Database();

    @Contextual
    public static class Database {
        @Description({
            "# SQL Drivers and ports:",
            "# MySQL (3306), MariaDB (3306), PostgresQL (5432)",
            "# SQLite, H2"
        })
        public DatabaseType databaseType = DatabaseType.SQLITE;

        public String hostname = "127.0.0.1";
        public String database = "database";
        public String username = "root";
        public String password = "U5eStr0ngP4ssw0rd";
        public int port = 3306;
    }

    @Description({ " ", "# Teleport request section" })
    public TeleportAsk teleportAsk = new TeleportAsk();

    @Contextual
    public static class TeleportAsk implements TeleportRequestSettings {
        @Description("# Time of tpa requests expire")

        @Description({ " ", "# Time of tpa requests expire" })
        public Duration tpaRequestExpire = Duration.ofSeconds(80);

        @Description({ " ", "# Time of teleportation time in /tpa commands" })
        public Duration tpaTimer = Duration.ofSeconds(10);

        @Override
        public Duration teleportExpire() {
            return this.tpaRequestExpire;
        }

        @Override
        public Duration teleportTime() {
            return this.tpaTimer;
        }
    }

    @Description({" ", "# Teleport section"})
    public Teleport teleport = new Teleport();

    @Contextual
    public static class Teleport implements SpawnSettings {

        @Description("# Teleports the player to spawn after death")
        public boolean teleportToSpawnOnDeath = true;

        @Description("# Time of teleportation to spawn")
        public Duration teleportTimeToSpawn = Duration.ofSeconds(5);

        @Override
        public Duration teleportationTimeToSpawn() {
            return this.teleportTimeToSpawn;
        }

        @Description("# Radius of random teleportation")
        public int randomTeleportRadius = 1000;
    }

    @Description({ " ", "# Homes Section" })
    public Homes homes = new Homes();

    @Contextual
    public static class Homes {
        @Description("# Max homes per permission")
        public Map<String, Integer> maxHomes = Map.of(
            "eternalcore.home.default", 1,
            "eternalcore.home.vip", 2,
            "eternalcore.home.premium", 3
        );
    }

    @Description({ " ", "# Awesome sounds" })
    public Sounds sound = new Sounds();

    @Contextual
    public static class Sounds {
        @Description("# Do you want to enable sound after player join to server?")
        public boolean enabledAfterJoin = true;
        public Sound afterJoin = Sound.BLOCK_NOTE_BLOCK_PLING;
        public float afterJoinVolume = 1.8F;
        public float afterJoinPitch = 1F;

        @Description({ " ", "# Do you want to enable sound after player quit server?" })
        public boolean enableAfterQuit = true;
        public Sound afterQuit = Sound.BLOCK_NOTE_BLOCK_BASEDRUM;
        public float afterQuitVolume = 1.8F;
        public float afterQuitPitch = 1F;

        @Description({ " ", "# Do you want to enable sound after player send message on chat server?" })
        public boolean enableAfterChatMessage = true;
        public Sound afterChatMessage = Sound.ENTITY_ITEM_PICKUP;
        public float afterChatMessageVolume = 1.8F;
        public float afterChatMessagePitch = 1F;

    }

    @Description({ " ", "# Chat Section" })
    public Chat chat = new Chat();

    @Contextual
    public static class Chat implements ChatSettings {
        @Description("# Delay to send the next message under /helpop")
        public Duration helpOpDelay = Duration.ofSeconds(60);

        @Description({ " ", "# Custom message for unknown command" })
        public boolean replaceStandardHelpMessage = false;

        @Description({ " ", "# Chat delay to send next message in chat" })
        public Duration chatDelay = Duration.ofSeconds(5);

        @Description({ " ", "# Number of lines that will be cleared when using the /chat clear command" })
        public int linesToClear = 128;

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
        public Duration getChatDelay() {
            return this.chatDelay;
        }

        @Override
        @Exclude
        public void setChatDelay(Duration chatDelay) {
            this.chatDelay = chatDelay;
        }

    }

    @Description({ " ", "# Additional formatting options" })
    public Format format = new Format();

    @Contextual
    public static class Format {
        public String separator = "&7, ";
    }

    @Description({ " ", "# AFK Section" })
    public Afk afk = new Afk();

    @Contextual
    public static class Afk implements AfkSettings {
        @Description({
            "# Number of interactions a player must make to have AFK status removed",
            "# This is for so that stupid miss-click doesn't disable AFK status"
        })
        public int interactionsCountDisableAfk = 20;

        @Description({ " ", "# Time before using the /afk command again" })
        public Duration afkCommandDelay = Duration.ofSeconds(60);

        @Description({ " ", "# The amount of time a player must be inactive to be marked as AFK" })
        public Duration afkInactivityTime = Duration.ofMinutes(10);

        @Override
        public int interactionsCountDisableAfk() {
            return this.interactionsCountDisableAfk;
        }

        @Override
        public Duration getAfkDelay() {
            return this.afkCommandDelay;
        }

        @Override
        public Duration getAfkInactivityTime() {
            return this.afkInactivityTime;
        }
    }

    @Description({ " ", "# Items" })
    public Items items = new Items();

    @Contextual
    public static class Items {
        @Description("# Use unsafe enchantments? Allows you to apply custom enchants to various items")
        public boolean unsafeEnchantments = true;

        @Description({ " ", "# The default item give amount, when no amount is specified in the command." })
        public int defaultGiveAmount = 1;
    }

    @Description({ " ", "# Warp Section" })
    public Warp warp = new Warp();

    @Contextual
    public static class Warp {
        @Description("# Warp inventory should be enabled?")
        public boolean inventoryEnabled = true;
    }

    @Description({ " ", "# Butcher" })
    public Butcher butcher = new Butcher();

    @Contextual
    public static class Butcher {
        @Description("# Safe number of chunks for command execution (above this number it will not be possible to execute the command)")
        public int safeChunkNumber = 5;
    }

    @Description({ " ", "# AutoMessage Section" })
    public AutoMessage autoMessage = new AutoMessage();

    @Contextual
    public static class AutoMessage implements AutoMessageSettings {

        @Description("# AutoMessage should be enabled?")
        public boolean enabled = true;

        @Description("# Interval between messages")
        public Duration interval = Duration.ofSeconds(60);

        @Description("# Draw mode (RANDOM, SEQUENTIAL)")
        public DrawMode drawMode = DrawMode.RANDOM;

        @Override
        public boolean enabled() {
            return this.enabled;
        }

        @Override
        public Duration interval() {
            return this.interval;
        }

        @Override
        public DrawMode drawMode() {
            return this.drawMode;
        }
    }

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "config.yml");
    }
}
