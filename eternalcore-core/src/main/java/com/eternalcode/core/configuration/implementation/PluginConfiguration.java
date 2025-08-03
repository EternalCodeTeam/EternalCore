package com.eternalcode.core.configuration.implementation;

import com.eternalcode.core.configuration.AbstractConfigurationFile;
import com.eternalcode.core.database.DatabaseConfig;
import com.eternalcode.core.feature.afk.AfkConfig;
import com.eternalcode.core.feature.automessage.AutoMessageConfig;
import com.eternalcode.core.feature.burn.BurnConfig;
import com.eternalcode.core.feature.butcher.ButcherConfig;
import com.eternalcode.core.feature.chat.ChatConfig;
import com.eternalcode.core.feature.fun.catboy.CatboyConfig;
import com.eternalcode.core.feature.helpop.HelpOpConfig;
import com.eternalcode.core.feature.home.HomesConfig;
import com.eternalcode.core.feature.jail.JailConfig;
import com.eternalcode.core.feature.lightning.LightningConfig;
import com.eternalcode.core.feature.randomteleport.RandomTeleportSettingsImpl;
import com.eternalcode.core.feature.repair.RepairConfig;
import com.eternalcode.core.feature.serverlinks.ServerLinksConfig;
import com.eternalcode.core.feature.spawn.SpawnJoinConfig;
import com.eternalcode.core.feature.spawn.SpawnSettings;
import com.eternalcode.core.feature.teleportrequest.TeleportRequestConfig;
import com.eternalcode.core.feature.warp.WarpConfig;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import org.bukkit.Sound;

import java.io.File;
import java.time.Duration;

@Header({
    "#",
    "# This is the main configuration file for EternalCore.",
    "#",
    "# If you need help with the configuration or have any questions related to EternalCore, join our discord, or create an issue on our GitHub.",
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

    @Comment("")
    @Comment("# Database Configuration")
    @Comment("# Settings responsible for the database connection")
    public DatabaseConfig database = new DatabaseConfig();

    @Comment("")
    @Comment("# Spawn & Join Configuration")
    @Comment("# Settings responsible for player spawn and join behavior")
    public SpawnJoinConfig join = new SpawnJoinConfig();

    @Bean
    @Comment("")
    @Comment("# Teleport Request Configuration")
    @Comment("# Settings for teleport requests between players")
    public TeleportRequestConfig teleportAsk = new TeleportRequestConfig();

    @Bean
    @Comment("")
    @Comment("# Teleport Configuration")
    @Comment("# General teleportation settings")
    public Teleport teleport = new Teleport();

    // TODO: Add migration, move option's to domain-specific configuration classes
    // teleportToSpawnOnDeath -> com.eternalcode.core.feature.spawn.SpawnConfig
    // teleportToRespawnPoint -> com.eternalcode.core.feature.spawn.SpawnConfig
    // teleportTimeToSpawn -> com.eternalcode.core.feature.spawn.SpawnConfig
    // includeOpPlayersInRandomTeleport -> com.eternalcode.core.feature.teleportrandomplayer.TeleportRandomPlayerConfig
    public static class Teleport extends OkaeriConfig implements SpawnSettings {
        @Comment("# Teleports the player to spawn after death")
        public boolean teleportToSpawnOnDeath = true;

        @Comment("# Teleports the player to respawn point after death")
        public boolean teleportToRespawnPoint = true;

        @Comment("# Time delay before teleporting to spawn")
        public Duration teleportTimeToSpawn = Duration.ofSeconds(5);

        @Comment("# Include players with OP permission in random teleport selection")
        public boolean includeOpPlayersInRandomTeleport = false;

        @Override
        public Duration teleportationTimeToSpawn() {
            return this.teleportTimeToSpawn;
        }
    }

    @Bean
    @Comment("")
    @Comment("# Random Teleport Configuration")
    @Comment("# Settings for random teleportation feature")
    public RandomTeleportSettingsImpl randomTeleport = new RandomTeleportSettingsImpl();

    @Bean
    @Comment("")
    @Comment("# Homes Configuration")
    @Comment("# Settings for player home management")
    public HomesConfig homes = new HomesConfig();

    @Comment("")
    @Comment("# Sound Configuration")
    @Comment("# Settings for various sound effects")
    @Bean
    public Sounds sound = new Sounds();

    public static class Sounds extends OkaeriConfig {
        @Comment("# Enable sound when player joins the server")
        public boolean enabledAfterJoin = true;
        public Sound afterJoin = Sound.BLOCK_NOTE_BLOCK_PLING;
        public float afterJoinVolume = 1.8F;
        public float afterJoinPitch = 1F;

        @Comment("# Enable sound when player leaves the server")
        public boolean enableAfterQuit = true;
        public Sound afterQuit = Sound.BLOCK_NOTE_BLOCK_BASEDRUM;
        public float afterQuitVolume = 1.8F;
        public float afterQuitPitch = 1F;

        @Comment("# Enable sound when player sends a chat message")
        public boolean enableAfterChatMessage = true;
        public Sound afterChatMessage = Sound.ENTITY_ITEM_PICKUP;
        public float afterChatMessageVolume = 1.8F;
        public float afterChatMessagePitch = 1F;
    }

    @Bean
    @Comment("")
    @Comment("# Chat Configuration")
    @Comment("# Settings for chat management and formatting")
    public ChatConfig chat = new ChatConfig();

    @Comment("")
    @Comment("# HelpOp Configuration")
    @Comment("# Settings for the help operator system")
    public HelpOpConfig helpOp = new HelpOpConfig();

    @Comment("")
    @Comment("# Repair Configuration")
    @Comment("# Settings for item repair functionality")
    public RepairConfig repair = new RepairConfig();

    @Comment("")
    @Comment("# Format Configuration")
    @Comment("# Additional formatting options for various features")
    public Format format = new Format();

    public static class Format extends OkaeriConfig {
        @Comment("# Separator used between list items")
        public String separator = "<gray>,</gray> ";
    }

    @Bean
    @Comment("")
    @Comment("# AFK Configuration")
    @Comment("# Settings for Away From Keyboard detection and management")
    public AfkConfig afk = new AfkConfig();

    @Comment("")
    @Comment("# Items Configuration")
    @Comment("# Settings for item management and behavior")
    public Items items = new Items();

    // TODO: Add migration, move option's to domain-specific configuration classes
    // unsafeEnchantments -> com.eternalcode.core.enchant.EnchantConfig
    // defaultGiveAmount -> com.eternalcode.core.feature.give.GiveConfig
    // dropOnFullInventory -> com.eternalcode.core.feature.give.GiveConfig
    public static class Items extends OkaeriConfig {
        @Comment("# Allow unsafe enchantments (enables custom enchants on various items)")
        public boolean unsafeEnchantments = true;

        @Comment("# Default amount of items to give when no amount is specified")
        public int defaultGiveAmount = 1;

        @Comment("# Drop items on ground when player's inventory is full")
        public boolean dropOnFullInventory = true;
    }

    @Comment("")
    @Comment("# Warp Configuration")
    @Comment("# Settings for warp points management")
    public WarpConfig warp = new WarpConfig();

    @Comment("")
    @Comment("# Butcher Configuration")
    @Comment("# Settings for entity removal functionality")
    public ButcherConfig butcher = new ButcherConfig();

    @Bean
    @Comment("")
    @Comment("# Auto Message Configuration")
    @Comment("# Settings for automatic message broadcasting")
    public AutoMessageConfig autoMessage = new AutoMessageConfig();

    @Bean
    @Comment("")
    @Comment("# Jail Configuration")
    @Comment("# Settings for player jail system")
    public JailConfig jail = new JailConfig();

    @Bean
    @Comment("")
    @Comment("# Catboy Configuration")
    @Comment("# Settings for catboy feature")
    public CatboyConfig catboy = new CatboyConfig();

    @Bean
    @Comment("")
    @Comment("# Lightning Configuration")
    @Comment("# Settings for lightning strike effects")
    public LightningConfig lightning = new LightningConfig();

    @Bean
    @Comment("")
    @Comment("# Server Links Configuration")
    @Comment("# Settings for server link management")
    public ServerLinksConfig serverLinks = new ServerLinksConfig();

    @Bean
    @Comment("")
    @Comment("# Burn /command Configuration")
    public BurnConfig burn = new BurnConfig();

    @Override
    public File getConfigFile(File dataFolder) {
        return new File(dataFolder, "config.yml");
    }
}
