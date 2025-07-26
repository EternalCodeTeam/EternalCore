package com.eternalcode.core.configuration.implementation;

import com.eternalcode.core.configuration.AbstractConfigurationFile;
import com.eternalcode.core.database.DatabaseConfig;
import com.eternalcode.core.database.DatabaseType;
import com.eternalcode.core.feature.afk.AfkSettings;
import com.eternalcode.core.feature.automessage.AutoMessageSettings;
import com.eternalcode.core.feature.catboy.CatBoySettings;
import com.eternalcode.core.feature.chat.ChatSettings;
import com.eternalcode.core.feature.helpop.HelpOpSettings;
import com.eternalcode.core.feature.jail.JailSettings;
import com.eternalcode.core.feature.randomteleport.RandomTeleportSettingsImpl;
import com.eternalcode.core.feature.serverlinks.ServerLinksConfig;
import com.eternalcode.core.feature.spawn.SpawnSettings;
import com.eternalcode.core.feature.teleportrequest.TeleportRequestSettings;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import org.bukkit.Material;
import org.bukkit.Sound;

import java.io.File;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Header({
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

@ConfigurationFile
public class PluginConfiguration extends AbstractConfigurationFile {

    @Comment("# Whether the player should receive information about new plugin updates upon joining the server")
    public boolean shouldReceivePluginUpdates = true;

    @Comment
    @Comment({
        "Settings responsible for the database connection."
    })
    public DatabaseConfig database = new DatabaseConfig();


    @Comment({ "", "# Join settings" })
    public Join join = new Join();

    public static class Join extends OkaeriConfig {
        @Comment("# Teleport to spawn on first join")
        public boolean teleportToSpawnOnFirstJoin = true;

        @Comment("# Teleport to spawn on join")
        public boolean teleportToSpawnOnJoin = false;
    }


    @Bean
    @Comment({ " ", "# Teleport request section" })
    public TeleportAsk teleportAsk = new TeleportAsk();

    public static class TeleportAsk extends OkaeriConfig implements TeleportRequestSettings {
        @Comment({ "# Time of tpa requests expire" })
        public Duration tpaRequestExpire = Duration.ofSeconds(80);

        @Comment({ " ", "# Time of teleportation time in /tpa commands" })
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

    @Bean
    @Comment({ " ", "# Teleport section" })
    public Teleport teleport = new Teleport();

    public static class Teleport extends OkaeriConfig implements SpawnSettings {
        @Comment("# Teleports the player to spawn after death")
        public boolean teleportToSpawnOnDeath = true;

        @Comment("# Teleports the player to respawn point after death")
        public boolean teleportToRespawnPoint = true;

        @Comment("# Time of teleportation to spawn")
        public Duration teleportTimeToSpawn = Duration.ofSeconds(5);

        @Comment("# Include players with op in teleport to random player")
        public boolean includeOpPlayersInRandomTeleport = false;

        @Override
        public Duration teleportationTimeToSpawn() {
            return this.teleportTimeToSpawn;
        }
    }

    @Bean
    @Comment({ "", "# Random Teleport Section" })
    public RandomTeleportSettingsImpl randomTeleport = new RandomTeleportSettingsImpl();

    @Bean
    @Comment({ " ", "# Homes Section" })
    public Homes homes = new Homes();

    public static class Homes extends OkaeriConfig {
        @Comment("# Default home name")
        public String defaultHomeName = "home";

        @Comment("# Time of teleportation to homes")
        public Duration teleportTimeToHomes = Duration.ofSeconds(5);

        @Comment("# Max homes per permission")
        public Map<String, Integer> maxHomes = new LinkedHashMap<>() {
            {
                put("eternalcore.home.default", 1);
                put("eternalcore.home.vip", 2);
                put("eternalcore.home.premium", 3);
            }
        };
    }

    @Comment({ " ", "# Awesome sounds" })
    @Bean
    public Sounds sound = new Sounds();

    public static class Sounds extends OkaeriConfig {
        @Comment("# Do you want to enable sound after player join to server?")
        public boolean enabledAfterJoin = true;
        public Sound afterJoin = Sound.BLOCK_NOTE_BLOCK_PLING;
        public float afterJoinVolume = 1.8F;
        public float afterJoinPitch = 1F;

        @Comment({ " ", "# Do you want to enable sound after player quit server?" })
        public boolean enableAfterQuit = true;
        public Sound afterQuit = Sound.BLOCK_NOTE_BLOCK_BASEDRUM;
        public float afterQuitVolume = 1.8F;
        public float afterQuitPitch = 1F;

        @Comment({ " ", "# Do you want to enable sound after player send message on chat server?" })
        public boolean enableAfterChatMessage = true;
        public Sound afterChatMessage = Sound.ENTITY_ITEM_PICKUP;
        public float afterChatMessageVolume = 1.8F;
        public float afterChatMessagePitch = 1F;

    }

    @Bean
    @Comment({ " ", "# Chat Section" })
    public Chat chat = new Chat();

    public static class Chat extends OkaeriConfig implements ChatSettings {

        @Comment({ "# Custom message for unknown command" })
        public boolean replaceStandardHelpMessage = false;

        @Comment({ " ", "# Chat delay to send next message in chat" })
        public Duration chatDelay = Duration.ofSeconds(5);

        @Comment({ " ", "# Number of lines that will be cleared when using the /chat clear command" })
        public int linesToClear = 256;

        @Comment({ " ", "# Chat should be enabled?" })
        public boolean chatEnabled = true;

        @Override
        public boolean isChatEnabled() {
            return this.chatEnabled;
        }

        @Override
        public void setChatEnabled(boolean chatEnabled) {
            this.chatEnabled = chatEnabled;
        }

        @Override
        public Duration getChatDelay() {
            return this.chatDelay;
        }

        @Override
        public void setChatDelay(Duration chatDelay) {
            this.chatDelay = chatDelay;
        }

        @Override
        public int linesToClear() {
            return this.linesToClear;
        }

    }

    @Comment({ " ", "# HelpOp Section" })
    public HelpOp helpOp = new HelpOp();

    public static class HelpOp extends OkaeriConfig implements HelpOpSettings {

        @Comment("# Delay to send the next message under /helpop")
        public Duration helpOpDelay = Duration.ofSeconds(60);

        @Override
        public Duration getHelpOpDelay() {
            return this.helpOpDelay;
        }
    }

    @Comment({ " ", "# Repair Section" })
    public Repair repair = new Repair();

    public static class Repair extends OkaeriConfig {

        @Comment({ "# Repair command cooldown" })
        public Duration repairDelay = Duration.ofSeconds(5);

        public Duration repairDelay() {
            return this.repairDelay;
        }
    }

    @Comment({ " ", "# Additional formatting options" })
    public Format format = new Format();

    public static class Format extends OkaeriConfig {
        public String separator = "<gray>,</gray> ";
    }

    @Bean
    @Comment({ " ", "# AFK Section" })
    public Afk afk = new Afk();

    public static class Afk extends OkaeriConfig implements AfkSettings {
        @Comment({
            "# Number of interactions a player must make to have AFK status removed",
            "# This is for so that stupid miss-click doesn't disable AFK status"
        })
        public int interactionsCountDisableAfk = 20;

        @Comment({ " ", "# Time before using the /afk command again" })
        public Duration afkCommandDelay = Duration.ofSeconds(60);

        @Comment({
            "# Should a player be marked as AFK automatically?",
            "# If set to true, the player will be marked as AFK after a certain amount of time of inactivity",
            "# If set to false, the player will have to use the /afk command to be marked as AFK"
        })
        public boolean autoAfk = true;

        @Comment({ " ", "# The amount of time a player must be inactive to be marked as AFK" })
        public Duration afkInactivityTime = Duration.ofMinutes(10);

        @Comment({ " ", "# Should a player be kicked from the game when marked as AFK?" })
        public boolean kickOnAfk = false;

        @Override
        public boolean autoAfk() {
            return this.autoAfk;
        }

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

    @Comment({ " ", "# Items" })
    public Items items = new Items();

    public static class Items extends OkaeriConfig {
        @Comment("# Use unsafe enchantments? Allows you to apply custom enchants to various items")
        public boolean unsafeEnchantments = true;

        @Comment({ " ", "# The default item give amount, when no amount is specified in the command." })
        public int defaultGiveAmount = 1;

        @Comment({ " ", "# Determines whether items should be dropped on the ground when the player's inventory is full" })
        public boolean dropOnFullInventory = true;
    }

    @Comment({ " ", "# Warp Section" })
    public Warp warp = new Warp();

    public static class Warp extends OkaeriConfig {
        @Comment("# Time of teleportation to warp")
        public Duration teleportTimeToWarp = Duration.ofSeconds(5);

        @Comment("# Warp inventory should be enabled?")
        public boolean inventoryEnabled = true;

        @Comment("# Warp inventory auto add new warps")
        public boolean autoAddNewWarps = true;

        @Comment({"# Options below allow you to customize item representing warp added to GUI, ",
            "# you can change almost everything inside langueage files, after the warp has been added to the inventory."})
        public String itemNamePrefix = "&8» &6Warp: &f";

        public String itemLore = "&7Click to teleport!";

        public Material itemMaterial = Material.PLAYER_HEAD;

        @Comment("# Texture of the item (only for PLAYER_HEAD material)")
        public String itemTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzk4ODVlODIzZmYxNTkyNjdjYmU4MDkwOTNlMzNhNDc2ZTI3NDliNjU5OGNhNGEyYTgxZWU2OTczODAzZmI2NiJ9fX0=";
    }

    @Comment({ " ", "# Butcher" })
    public Butcher butcher = new Butcher();

    public static class Butcher extends OkaeriConfig {
        @Comment("# Safe number of chunks for command execution (above this number it will not be possible to execute the command)")
        public int safeChunkNumber = 5;
    }

    @Bean
    @Comment({ " ", "# AutoMessage Section" })
    public AutoMessage autoMessage = new AutoMessage();

    public static class AutoMessage extends OkaeriConfig implements AutoMessageSettings {
        @Comment("# AutoMessage should be enabled?")
        public boolean enabled = true;

        @Comment("# Interval between messages")
        public Duration interval = Duration.ofSeconds(60);

        @Comment("# Draw mode (RANDOM, SEQUENTIAL)")
        public DrawMode drawMode = DrawMode.RANDOM;

        @Comment("# Minimum number of players on the server to send an auto message.")
        public int minPlayers = 1;

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

    @Bean
    @Comment({ " ", "# Jail Section" })
    public Jail jail = new Jail();

    public static class Jail extends OkaeriConfig implements JailSettings {

        @Comment("# Default jail duration, set if no duration is specified")
        public Duration defaultJailDuration = Duration.ofMinutes(30);

        @Comment("# Allowed commands in jail")
        public Set<String> allowedCommands = Set.of("help", "msg", "r", "tell", "me", "helpop");

        @Override
        public Duration defaultJailDuration() {
            return this.defaultJailDuration;
        }

        @Override
        public Set<String> allowedCommands() {
            return this.allowedCommands;
        }
    }

    @Bean
    @Comment({ " ", "# 4fun Section" })
    FunSection fun = new FunSection();

    public static class FunSection extends OkaeriConfig implements CatBoySettings {
        @Comment({
            "# Speed of player walk speed while using /catboy feature",
            "# Default minecraft walk speed is 0.2"
        })
        public float catboyWalkSpeed = 0.4F;

        @Override
        public float getCatboyWalkSpeed() {
            return this.catboyWalkSpeed;
        }
    }

    @Bean
    @Comment({ " ", "# ServerLinks Section" })
    ServerLinksConfig serverLinks = new ServerLinksConfig();

    @Override
    public File getConfigFile(File dataFolder) {
        return new File(dataFolder, "config.yml");
    }
}
