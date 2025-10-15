package com.eternalcode.core.configuration.implementation;

import com.eternalcode.core.configuration.AbstractConfigurationFile;
import com.eternalcode.core.database.DatabaseConfig;
import com.eternalcode.core.database.DatabaseSettings;
import com.eternalcode.core.feature.afk.AfkConfig;
import com.eternalcode.core.feature.afk.AfkSettings;
import com.eternalcode.core.feature.automessage.AutoMessageConfig;
import com.eternalcode.core.feature.automessage.AutoMessageSettings;
import com.eternalcode.core.feature.broadcast.BroadcastConfig;
import com.eternalcode.core.feature.broadcast.BroadcastSettings;
import com.eternalcode.core.feature.butcher.ButcherConfig;
import com.eternalcode.core.feature.butcher.ButcherSettings;
import com.eternalcode.core.feature.chat.ChatConfig;
import com.eternalcode.core.feature.chat.ChatSettings;
import com.eternalcode.core.feature.enchant.EnchantConfig;
import com.eternalcode.core.feature.enchant.EnchantSettings;
import com.eternalcode.core.feature.give.GiveConfig;
import com.eternalcode.core.feature.give.GiveSettings;
import com.eternalcode.core.feature.helpop.HelpOpConfig;
import com.eternalcode.core.feature.helpop.HelpOpSettings;
import com.eternalcode.core.feature.home.HomesConfig;
import com.eternalcode.core.feature.home.HomesSettings;
import com.eternalcode.core.feature.jail.JailConfig;
import com.eternalcode.core.feature.jail.JailSettings;
import com.eternalcode.core.feature.lightning.LightningConfig;
import com.eternalcode.core.feature.lightning.LightningSettings;
import com.eternalcode.core.feature.randomteleport.RandomTeleportConfig;
import com.eternalcode.core.feature.randomteleport.RandomTeleportSettings;
import com.eternalcode.core.feature.repair.RepairConfig;
import com.eternalcode.core.feature.repair.RepairSettings;
import com.eternalcode.core.feature.serverlinks.ServerLinksConfig;
import com.eternalcode.core.feature.serverlinks.ServerLinksSettings;
import com.eternalcode.core.feature.spawn.SpawnJoinConfig;
import com.eternalcode.core.feature.spawn.SpawnJoinSettings;
import com.eternalcode.core.feature.teleportrandomplayer.TeleportToRandomPlayerConfig;
import com.eternalcode.core.feature.teleportrandomplayer.TeleportToRandomPlayerSettings;
import com.eternalcode.core.feature.teleportrequest.TeleportRequestConfig;
import com.eternalcode.core.feature.teleportrequest.TeleportRequestSettings;
import com.eternalcode.core.feature.vanish.VanishConfig;
import com.eternalcode.core.feature.vanish.VanishSettings;
import com.eternalcode.core.feature.warp.WarpConfig;
import com.eternalcode.core.feature.warp.WarpSettings;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import com.eternalcode.core.translation.TranslationConfig;
import com.eternalcode.core.translation.TranslationSettings;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import org.bukkit.Sound;

import java.io.File;

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

    @Bean(proxied = TranslationSettings.class)
    @Comment("")
    @Comment("# Language Configuration")
    @Comment("# Settings that determine the language used within the server.")
    @Comment("# Choose the preferred language for all messages and interactions in the plugin.")
    TranslationConfig language = new TranslationConfig();
    
    @Bean(proxied = DatabaseSettings.class)
    @Comment("")
    @Comment("# Database Configuration")
    @Comment("# Settings responsible for the database connection")
    DatabaseConfig database = new DatabaseConfig();

    @Bean(proxied = SpawnJoinSettings.class)
    @Comment("")
    @Comment("# Spawn & Join Configuration")
    @Comment("# Settings responsible for player spawn and join behavior")
    SpawnJoinConfig spawn = new SpawnJoinConfig();

    @Bean(proxied = TeleportRequestSettings.class)
    @Comment("")
    @Comment("# Teleport Request Configuration")
    @Comment("# Settings for teleport requests between players")
    TeleportRequestConfig teleportAsk = new TeleportRequestConfig();

    @Bean(proxied = TeleportToRandomPlayerSettings.class)
    @Comment("")
    @Comment("# Configuration for teleporting to a random player")
    @Comment("# Settings for the /tprp command, allowing you to randomly teleport to any player on the server")
    TeleportToRandomPlayerConfig teleportToRandomPlayer = new TeleportToRandomPlayerConfig();

    @Bean(proxied = RandomTeleportSettings.class)
    @Comment("")
    @Comment("# Settings for random teleportation feature")
    RandomTeleportConfig randomTeleport = new RandomTeleportConfig();

    @Bean(proxied = HomesSettings.class)
    @Comment("")
    @Comment("# Homes Configuration")
    @Comment("# Settings for player home management")
    HomesConfig homes = new HomesConfig();

    @Bean(proxied = ChatSettings.class)
    @Comment("")
    @Comment("# Chat Configuration")
    @Comment("# Settings for chat management and formatting")
    ChatConfig chat = new ChatConfig();

    @Bean(proxied = BroadcastSettings.class)
    @Comment("")
    @Comment("# Broadcast Configuration")
    BroadcastConfig broadcast = new BroadcastConfig();

    @Bean(proxied = HelpOpSettings.class)
    @Comment("")
    @Comment("# HelpOp Configuration")
    @Comment("# Settings for the help operator system")
    HelpOpConfig helpOp = new HelpOpConfig();

    @Bean(proxied = RepairSettings.class)
    @Comment("")
    @Comment("# Repair Configuration")
    @Comment("# Settings for item repair functionality")
    RepairConfig repair = new RepairConfig();

    @Comment("")
    @Comment("# Format Configuration")
    @Comment("# Additional formatting options for various features")
    public Format format = new Format();

    public static class Format extends OkaeriConfig {
        @Comment("# Separator used between list items")
        public String separator = "<gray>,</gray> ";
    }

    @Bean(proxied = AfkSettings.class)
    @Comment("")
    @Comment("# AFK Configuration")
    @Comment("# Settings for Away From Keyboard detection and management")
    AfkConfig afk = new AfkConfig();

    @Bean(proxied = EnchantSettings.class)
    @Comment("")
    @Comment("# Enchant Configuration")
    @Comment("# Settings for enchant functionality")
    EnchantConfig enchant = new EnchantConfig();

    @Bean(proxied = GiveSettings.class)
    @Comment("")
    @Comment("# Give Configuration")
    @Comment("# Settings for item giving functionality")
    GiveConfig give = new GiveConfig();

    @Bean(proxied = WarpSettings.class)
    @Comment("")
    @Comment("# Warp Configuration")
    @Comment("# Settings for warp points management")
    WarpConfig warp = new WarpConfig();

    @Bean(proxied = ButcherSettings.class)
    @Comment("")
    @Comment("# Butcher Configuration")
    @Comment("# Settings for entity removal functionality")
    ButcherConfig butcher = new ButcherConfig();

    @Bean(proxied = AutoMessageSettings.class)
    @Comment("")
    @Comment("# Auto Message Configuration")
    @Comment("# Settings for automatic message broadcasting")
    AutoMessageConfig autoMessage = new AutoMessageConfig();

    @Bean(proxied = JailSettings.class)
    @Comment("")
    @Comment("# Jail Configuration")
    @Comment("# Settings for player jail system")
    JailConfig jail = new JailConfig();

    @Bean(proxied = LightningSettings.class)
    @Comment("")
    @Comment("# Lightning Configuration")
    @Comment("# Settings for lightning strike effects")
    LightningConfig lightning = new LightningConfig();

    @Bean(proxied = ServerLinksSettings.class)
    @Comment("")
    @Comment("# Server Links Configuration")
    @Comment("# Settings for server link management")
    ServerLinksConfig serverLinks = new ServerLinksConfig();

    @Bean(proxied = VanishSettings.class)
    @Comment("")
    @Comment("# Vanish Configuration")
    @Comment("# Settings responsible for player vanish functionality")
    VanishConfig vanish = new VanishConfig();

    @Override
    public File getConfigFile(File dataFolder) {
        return new File(dataFolder, "config.yml");
    }
}
