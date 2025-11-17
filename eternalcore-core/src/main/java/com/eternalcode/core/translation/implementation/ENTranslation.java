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
    @Comment("# This section is responsible for all messages used during bad of a command argument")
    public ENArgumentMessages argument = new ENArgumentMessages();

    @Comment({
        " ",
        "# This answer is responsible for the general formatting of some values",
        "# The purpose of the section is to reduce the repetition of some messages."
    })
    public ENFormatSection format = new ENFormatSection();

    @Comment("# This section is responsible for all messages related to item editing.")
    public ENItemEditMessages itemEdit = new ENItemEditMessages();

    @Comment({
        " ",
        "# This section is responsible for the messages of the /signeditor command",
    })
    public ENSignEditorMessages signEditor = new ENSignEditorMessages();

    @Override
    public File getConfigFile(File dataFolder) {
        return new File(dataFolder, "lang" + File.separator + "en_messages.yml");
    }

    @Getter
    public static class ENFormatSection extends OkaeriConfig implements Format {
        public String enable = "<green>enabled";
        public String disable = "<red>disabled";
    }

    @Comment({
        " ",
        "# This section is responsible for the player support chat /helpop",
    })
    public ENHelpOpMessages helpOp = new ENHelpOpMessages();

    @Comment({
        " ",
        "# This section is responsible for the messages of the /seen command"
    })
    public ENSeenMessages seen = new ENSeenMessages();

    @Comment({
        " ",
        "# This section is responsible for the communication between administration",
    })
    public ENAdminChatMessages adminChat = new ENAdminChatMessages();

    @Comment({
        " ",
        "# This section is responsible for ignore-related messages"
    })
    public ENIgnoreMessages ignore = new ENIgnoreMessages();

    @Comment({
        " ",
        "# This section is responsible for the messages of the /sudo command",
    })
    public ENSudoMessages sudo = new ENSudoMessages();

    @Comment({
        " ",
        "# This section is responsible for messages related to teleportation",
    })
    public ENTeleportMessages teleport = new ENTeleportMessages();

    @Comment({
        " ",
        "# This section is responsible for the messages of the /tprp command",
    })
    public ENTeleportToRandomPlayerMessages teleportToRandomPlayer = new ENTeleportToRandomPlayerMessages();

    @Comment({
        " ",
        "# This section is responsible for messages related to random teleport",
    })
    public ENRandomTeleportMessages randomTeleport = new ENRandomTeleportMessages();

    @Comment({
        " ",
        "# This section is responsible for messages related to chat",
    })
    public ENChatMessages chat = new ENChatMessages();

    @Comment({
        " ",
        "# This section is responsible for the messages of the /broadcast command",
    })
    public ENBroadcastMessages broadcast = new ENBroadcastMessages();

    @Comment({
        " ",
        "# This section is responsible for handling tpa requests,",
        "# It gives you the ability to edit queries of this type",
    })
    public ENTeleportRequestMessages tpa = new ENTeleportRequestMessages();

    @Comment({
        " ",
        "# This section is responsible for setting and editing fast travel points - warp",
    })
    public ENWarpMessages warp = new ENWarpMessages();

    @Comment({
        " ",
        "# The following section is responsible for setting and editing personal fast travel points - home",
    })
    public ENHomeMessages home = new ENHomeMessages();

    @Comment({
        " ",
        "# This section is responsible for setting and editing private messages."
    })
    public ENMsgMessages msg = new ENMsgMessages();

    @Comment({
        " ",
        "# Section responsible for AFK."
    })
    public ENAfkMessages afk = new ENAfkMessages();

    @Comment({
        " ",
        "# This section is responsible for death messages"
    })
    public ENDeathMessages deathMessage = new ENDeathMessages();

    @Comment({
        " ",
        "# This section is responsible for disposal messages"
    })
    public ENDisposalMessages disposal = new ENDisposalMessages();

    @Comment("# This section is responsible for /clear command")
    public ENClearMessages clear = new ENClearMessages();

    @Comment({
        " ",
        "# This section is responsible for /feed command messages"
    })
    public ENFeedMessages feed = new ENFeedMessages();

    @Comment({
        " ",
        "# This section is responsible for /heal command messages"
    })
    public ENHealMessages heal = new ENHealMessages();

    @Comment({
        " ",
        "# This section is responsible for /kill command messages"
    })
    public ENKillMessages kill = new ENKillMessages();

    @Comment({
        " ",
        "# This section is responsible for /speed command messages"
    })
    public ENSpeedMessages speed = new ENSpeedMessages();

    @Comment({
        " ",
        "# This section is responsible for /god command messages"
    })
    public ENGodModeMessages godmode = new ENGodModeMessages();

    @Comment({
        " ",
        "# This section is responsible for /fly command messages"
    })
    public ENFlyMessages fly = new ENFlyMessages();

    @Comment({
        " ",
        "# This section is responsible for /ping command messages"
    })
    public ENPingMessages ping = new ENPingMessages();

    @Comment({
        " ",
        "# This section is responsible for /gamemode command messages"
    })
    public ENGameModeMessages gamemode = new ENGameModeMessages();

    @Comment({
        " ",
        "# This section is responsible for /online command messages"
    })
    public ENOnlineMessages online = new ENOnlineMessages();

    @Comment({
        " ",
        "# This section is responsible for /whois command messages"
    })
    public ENWhoIsMessages whois = new ENWhoIsMessages();

    @Comment({
        " ",
        "# This section is responsible for /butcher command messages"
    })
    public ENButcherMessages butcher = new ENButcherMessages();

    @Comment({
        " ",
        "# This section is responsible for /give command messages"
    })
    public ENGiveMessages give = new ENGiveMessages();

    @Comment({
        " ",
        "# This section is responsible for /skull command messages"
    })
    public ENSkullMessages skull = new ENSkullMessages();

    @Comment({" ", "# This section is responsible for spawn-related stuff."})
    public ENSpawnMessages spawn = new ENSpawnMessages();

    @Comment({
        " ",
        "# This section is responsible for repair messages"
    })
    public ENRepairMessages repair = new ENRepairMessages();

    @Comment({
        " ",
        "# This section is responsible for enchant messages"
    })
    ENEnchantMessages enchant = new ENEnchantMessages();

    @Comment({" ", "# Messages sent on time and weather change."})
    public ENTimeAndWeatherMessages timeAndWeather = new ENTimeAndWeatherMessages();

    @Comment({" ", "# Messages responsible for containers"})
    public ENContainerMessages container = new ENContainerMessages();

    @Comment({" ", "# Set's max players on the server, the messages for the /setslot command"})
    public ENSetSlotMessages setSlot = new ENSetSlotMessages();

    @Comment({" ", "# Auto message"})
    public ENAutoMessageMessages autoMessage = new ENAutoMessageMessages();

    @Comment({" ", "# This section is responsible for handling jail-related stuff."})
    public ENJailMessages jailSection = new ENJailMessages();

    @Comment({" ", "# This section is responsible for elder guardian messages."})
    public ENElderGuardianMessages elderGuardian = new ENElderGuardianMessages();

    @Comment({" ", "# This section is responsible for demo screen messages."})
    public ENDemoScreenMessages demoScreen = new ENDemoScreenMessages();

    @Comment({" ", "# This section is responsible for join message."})
    public ENJoinMessage join = new ENJoinMessage();

    @Comment({" ", "# This section is responsible for quit message."})
    public ENQuitMessage quit = new ENQuitMessage();

    @Comment({" ", "# This section is responsible for end screen messages."})
    public ENEndScreenMessages endScreen = new ENEndScreenMessages();

    @Comment({" ", "# This section is responsible for '/burn' command messages."})
    public ENBurnMessages burn = new ENBurnMessages();

    @Comment({" ", "# This section is responsible for vanish-related stuff."})
    public ENVanishMessages vanish = new ENVanishMessages();

    @Comment({" ", "# This section is responsible for '/near' command messages."})
    public ENNearMessages near = new ENNearMessages();

    @Comment({" ", "# This section is responsible for the messages of the MOTD feature."})
    public ENMotdMessages motd = new ENMotdMessages();

    @Comment({" ", "# This section is responsible for teleporting to offline players."})
    public ENTeleportOfflineMessages teleportToOfflinePlayer = new ENTeleportOfflineMessages();

    @Comment({" ", "# This section is responsible for information about players' game time."})
    public ENPlaytimeMessages playtime = new ENPlaytimeMessages();

    @Comment({" ", "# This section is responsible for the messages of the freeze feature."})
    public ENFreezeMessages freeze = new ENFreezeMessages();

    @Comment({" ", "# This section is responsible for powertool messages."})
    public ENPowertoolMessages powertool = new ENPowertoolMessages();
}
