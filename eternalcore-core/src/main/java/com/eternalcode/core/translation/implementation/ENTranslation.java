package com.eternalcode.core.translation.implementation;

import com.eternalcode.core.feature.adminchat.messages.ENAdminChatMessages;
import com.eternalcode.core.feature.afk.messages.ENAfkMessages;
import com.eternalcode.core.feature.automessage.messages.ENAutoMessageMessages;
import com.eternalcode.core.feature.broadcast.messages.ENBroadcastMessages;
import com.eternalcode.core.feature.burn.messages.ENBurnMessages;
import com.eternalcode.core.feature.butcher.messages.ENButcherMessages;
import com.eternalcode.core.feature.chat.messages.ENChatMessages;
import com.eternalcode.core.feature.clear.messages.ENClearMessages;
import com.eternalcode.core.feature.container.messages.ENContainerMessages;
import com.eternalcode.core.feature.deathmessage.messages.ENDeathMessages;
import com.eternalcode.core.feature.disposal.messages.ENDisposalMessages;
import com.eternalcode.core.feature.enchant.messages.ENEnchantMessages;
import com.eternalcode.core.feature.feed.messages.ENFeedMessages;
import com.eternalcode.core.feature.fly.messages.ENFlyMessages;
import com.eternalcode.core.feature.freeze.messages.ENFreezeMessages;
import com.eternalcode.core.feature.fun.demoscreen.messages.ENDemoScreenMessages;
import com.eternalcode.core.feature.fun.elderguardian.messages.ENElderGuardianMessages;
import com.eternalcode.core.feature.fun.endscreen.messages.ENEndScreenMessages;
import com.eternalcode.core.feature.gamemode.messages.ENGameModeMessages;
import com.eternalcode.core.feature.give.messages.ENGiveMessages;
import com.eternalcode.core.feature.godmode.messages.ENGodModeMessages;
import com.eternalcode.core.feature.heal.messages.ENHealMessages;
import com.eternalcode.core.feature.helpop.messages.ENHelpOpMessages;
import com.eternalcode.core.feature.home.messages.ENHomeMessages;
import com.eternalcode.core.feature.ignore.messages.ENIgnoreMessages;
import com.eternalcode.core.feature.itemedit.messages.ENItemEditMessages;
import com.eternalcode.core.feature.jail.messages.ENJailMessages;
import com.eternalcode.core.feature.joinmessage.messages.ENJoinMessage;
import com.eternalcode.core.feature.kill.messages.ENKillMessages;
import com.eternalcode.core.feature.motd.messages.ENMotdMessages;
import com.eternalcode.core.feature.msg.messages.ENMsgMessages;
import com.eternalcode.core.feature.near.messages.ENNearMessages;
import com.eternalcode.core.feature.onlineplayers.messages.ENOnlineMessages;
import com.eternalcode.core.feature.ping.ENPingMessages;
import com.eternalcode.core.feature.playtime.messages.ENPlaytimeMessages;
import com.eternalcode.core.feature.powertool.messages.ENPowertoolMessages;
import com.eternalcode.core.feature.quitmessage.messages.ENQuitMessage;
import com.eternalcode.core.feature.randomteleport.messages.ENRandomTeleportMessages;
import com.eternalcode.core.feature.repair.messages.ENRepairMessages;
import com.eternalcode.core.feature.seen.messages.ENSeenMessages;
import com.eternalcode.core.feature.setslot.messages.ENSetSlotMessages;
import com.eternalcode.core.feature.signeditor.messages.ENSignEditorMessages;
import com.eternalcode.core.feature.skull.messages.ENSkullMessages;
import com.eternalcode.core.feature.spawn.messages.ENSpawnMessages;
import com.eternalcode.core.feature.speed.messages.ENSpeedMessages;
import com.eternalcode.core.feature.sudo.messages.ENSudoMessages;
import com.eternalcode.core.feature.teleport.messages.ENTeleportMessages;
import com.eternalcode.core.feature.teleportoffline.ENTeleportOfflineMessages;
import com.eternalcode.core.feature.teleportrandomplayer.messages.ENTeleportToRandomPlayerMessages;
import com.eternalcode.core.feature.teleportrequest.messages.ENTeleportRequestMessages;
import com.eternalcode.core.feature.time.messages.ENTimeAndWeatherMessages;
import com.eternalcode.core.feature.vanish.messages.ENVanishMessages;
import com.eternalcode.core.feature.warp.messages.ENWarpMessages;
import com.eternalcode.core.feature.whois.ENWhoIsMessages;
import com.eternalcode.core.litecommand.argument.messages.ENArgumentMessages;
import com.eternalcode.core.translation.AbstractTranslation;
import com.eternalcode.core.translation.Language;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.io.File;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENTranslation extends AbstractTranslation {

    public ENTranslation(Language language) {
        super(language);
    }

    public ENTranslation() {
        this(Language.EN);
    }

    @Comment({
        "#",
        "# This file is responsible for the English translation in eternalcore.",
        "#",
        "# If you need help with setup or have any questions related to EternalCore,",
        "# join us in our discord, or write a request in the \"Issues\" tab on GitHub",
        "#",
        "# Issues: https://github.com/EternalCodeTeam/EternalCore/issues",
        "# Discord: https://discord.gg/FQ7jmGBd6c",
        "# Website: https://eternalcode.pl/",
        "# SourceCode: https://github.com/EternalCodeTeam/EternalCore",
    })
    @Comment({
        " ",
        "# You can use MiniMessages formatting everywhere, or standard &7, &e etc.",
        "# More information about MiniMessages: https://docs.adventure.kyori.net/minimessage/format.html",
        "# You can use the web generator to generate and preview messages: https://webui.adventure.kyori.net/",
        "#",
        "# The new notification system supports various formats and options:",
        "#",
        "# Examples:",
        "#",
        "# example: []",
        "#",
        "# example: \"Hello world\"",
        "#",
        "# example:",
        "#   - \"Hello\"",
        "#   - \"world\"",
        "#",
        "# example:",
        "#   title: \"Hello world\"",
        "#   subtitle: \"Subtitle\"",
        "#   times: \"1s 2s 1s\"",
        "#",
        "# example:",
        "#   subtitle: \"Subtitle\"",
        "#   chat: \"Death message\"",
        "#",
        "# example:",
        "#   actionbar: \"Hello world\"",
        "#   chat:",
        "#     - \"Hello\"",
        "#     - \"world\"",
        "#",
        "# example:",
        "#   actionbar: \"Hello world\"",
        "#   # https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html ",
        "#   sound: \"ENTITY_PLAYER_LEVELUP 2.0 1.0\"",
        "#",
        "# example:",
        "#   actionbar: \"Hello world\"",
        "#   # Sound categories: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/SoundCategory.html",
        "#   sound: \"ENTITY_PLAYER_LEVELUP WEATHER 2.0 1.0\" # If you want to play a sound in a certain category, for example if a player has the sound category \"WEATHER\" in the game settings set to 0%, the sound will not play.",
        "#",
        "# example:",
        "#   titleHide: true # This clearing the title from the screen, example if other plugin send title you can clear it with this for any action",
        "#",
        " "
    })
    @Comment("# Command arguments")
    public ENArgumentMessages argument = new ENArgumentMessages();

    @Comment("# General value formatting")
    public ENFormatSection format = new ENFormatSection();

    @Getter
    public static class ENFormatSection extends OkaeriConfig implements Format {
        public String enable = "<color:#9d6eef>enabled";
        public String disable = "<red>disabled";
    }

    @Comment("# Chat")
    public ENChatMessages chat = new ENChatMessages();

    @Comment("# Admin chat")
    public ENAdminChatMessages adminChat = new ENAdminChatMessages();

    @Comment("# Private messages")
    public ENMsgMessages msg = new ENMsgMessages();

    @Comment("# Broadcast")
    public ENBroadcastMessages broadcast = new ENBroadcastMessages();

    @Comment("# Auto messages")
    public ENAutoMessageMessages autoMessage = new ENAutoMessageMessages();

    @Comment("# Join message")
    public ENJoinMessage join = new ENJoinMessage();

    @Comment("# Quit message")
    public ENQuitMessage quit = new ENQuitMessage();

    @Comment("# MOTD (Message of the Day)")
    public ENMotdMessages motd = new ENMotdMessages();

    @Comment("# Death messages")
    public ENDeathMessages deathMessage = new ENDeathMessages();

    @Comment("# AFK")
    public ENAfkMessages afk = new ENAfkMessages();

    @Comment("# Ignore")
    public ENIgnoreMessages ignore = new ENIgnoreMessages();

    @Comment("# Teleportation")
    public ENTeleportMessages teleport = new ENTeleportMessages();

    @Comment("# Teleport requests (TPA)")
    public ENTeleportRequestMessages tpa = new ENTeleportRequestMessages();

    @Comment("# Teleport to random player")
    public ENTeleportToRandomPlayerMessages teleportToRandomPlayer = new ENTeleportToRandomPlayerMessages();

    @Comment("# Random teleport")
    public ENRandomTeleportMessages randomTeleport = new ENRandomTeleportMessages();

    @Comment("# Teleport to offline players")
    public ENTeleportOfflineMessages teleportToOfflinePlayer = new ENTeleportOfflineMessages();

    @Comment("# Home")
    public ENHomeMessages home = new ENHomeMessages();

    @Comment("# Warp")
    public ENWarpMessages warp = new ENWarpMessages();

    @Comment("# Spawn")
    public ENSpawnMessages spawn = new ENSpawnMessages();

    @Comment("# Near")
    public ENNearMessages near = new ENNearMessages();

    @Comment("# Heal")
    public ENHealMessages heal = new ENHealMessages();

    @Comment("# Feed")
    public ENFeedMessages feed = new ENFeedMessages();

    @Comment("# Fly")
    public ENFlyMessages fly = new ENFlyMessages();

    @Comment("# Speed")
    public ENSpeedMessages speed = new ENSpeedMessages();

    @Comment("# God mode")
    public ENGodModeMessages godmode = new ENGodModeMessages();

    @Comment("# Freeze")
    public ENFreezeMessages freeze = new ENFreezeMessages();

    @Comment("# Vanish")
    public ENVanishMessages vanish = new ENVanishMessages();

    @Comment("# Game mode")
    public ENGameModeMessages gamemode = new ENGameModeMessages();

    @Comment("# Time and weather")
    public ENTimeAndWeatherMessages timeAndWeather = new ENTimeAndWeatherMessages();

    @Comment("# Ping")
    public ENPingMessages ping = new ENPingMessages();

    @Comment("# Online players")
    public ENOnlineMessages online = new ENOnlineMessages();

    @Comment("# Player information")
    public ENWhoIsMessages whois = new ENWhoIsMessages();

    @Comment("# Last seen")
    public ENSeenMessages seen = new ENSeenMessages();

    @Comment("# Playtime")
    public ENPlaytimeMessages playtime = new ENPlaytimeMessages();

    @Comment("# Give items")
    public ENGiveMessages give = new ENGiveMessages();

    @Comment("# Clear inventory")
    public ENClearMessages clear = new ENClearMessages();

    @Comment("# Disposal")
    public ENDisposalMessages disposal = new ENDisposalMessages();

    @Comment("# Containers")
    public ENContainerMessages container = new ENContainerMessages();

    @Comment("# Set slot")
    public ENSetSlotMessages setSlot = new ENSetSlotMessages();

    @Comment("# Item edit")
    public ENItemEditMessages itemEdit = new ENItemEditMessages();

    @Comment("# Enchant")
    public ENEnchantMessages enchant = new ENEnchantMessages();

    @Comment("# Repair")
    public ENRepairMessages repair = new ENRepairMessages();

    @Comment("# Skull")
    public ENSkullMessages skull = new ENSkullMessages();

    @Comment("# Sign editor")
    public ENSignEditorMessages signEditor = new ENSignEditorMessages();

    @Comment("# Powertool")
    public ENPowertoolMessages powertool = new ENPowertoolMessages();

    @Comment("# Kill")
    public ENKillMessages kill = new ENKillMessages();

    @Comment("# Burn")
    public ENBurnMessages burn = new ENBurnMessages();

    @Comment("# Butcher")
    public ENButcherMessages butcher = new ENButcherMessages();

    @Comment("# Jail")
    public ENJailMessages jailSection = new ENJailMessages();

    @Comment("# Sudo")
    public ENSudoMessages sudo = new ENSudoMessages();

    @Comment("# Help chat")
    public ENHelpOpMessages helpOp = new ENHelpOpMessages();

    @Comment("# Elder Guardian")
    public ENElderGuardianMessages elderGuardian = new ENElderGuardianMessages();

    @Comment("# Demo Screen")
    public ENDemoScreenMessages demoScreen = new ENDemoScreenMessages();

    @Comment("# End Screen")
    public ENEndScreenMessages endScreen = new ENEndScreenMessages();

    @Override
    public File getConfigFile(File dataFolder) {
        return new File(dataFolder, "lang" + File.separator + "en_messages.yml");
    }
}
