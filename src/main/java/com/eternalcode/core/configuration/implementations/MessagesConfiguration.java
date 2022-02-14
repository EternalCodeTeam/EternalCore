/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.configuration.implementations;

import com.eternalcode.core.configuration.AbstractConfigWithResource;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class MessagesConfiguration extends AbstractConfigWithResource {

    public MessagesConfiguration(File folder, String child) {
        super(folder, child);
    }

    @Description({ "# ",
        "# This is the part of configuration file for EternalCore.",
        "# ",
        "# if you need help with the configration or have any questions related to EternalCore, join us in our Discord",
        "# ",
        "# Discord: https://dc.eternalcode.pl/",
        "# Website: https://eternalcode.pl/", " " })

    @Description("# Argument Section")
    public ArgumentSection argumentSection = new ArgumentSection();
    @Description({ "", "# HelpOp Section" })
    public HelpopSection helpOpSection = new HelpopSection();
    @Description({ "", "# AdminChat Section" })
    public AdminChatSection adminChatSection = new AdminChatSection();
    @Description({ "", "# Chat Section" })
    public ChatSection chatSection = new ChatSection();
    @Description({ "", "# Teleport Section" })
    public TeleportSection teleportSection = new TeleportSection();

    @Description({ "", "# Other messages" })
    public OtherMessages otherMessages = new OtherMessages();

    @Contextual
    public static class ArgumentSection {
        public String permissionMessage = "&8» &cYou don't have permission to perform this command! &7({PERMISSIONS})";
        public String offlinePlayer = "&8» &cThis player is currently offline!";
        public String onlyPlayer = "&8» &cCommand is only for players!";
        public String notNumber = "&8» &cArgument isnt a number!";
        public String numberBiggerThanOrEqualZero = "&8» &cThe number must be greater than or equal to 0!";
        public String noItem = "&8» &cYou need item to use this command!";
        public String noMaterial = "&8» &cThis item doesn't exist";
        public String noArgument = "&8» &cThis argument doesn't exist";
    }

    @Contextual
    public static class HelpopSection {
        public String format = "&8[&4HelpOp&8] &e{NICK}&8: &f{TEXT}";
        public String send = "&8» &aThis message has been successfully sent to administration";
    }

    @Contextual
    public static class AdminChatSection {
        public String format = "&8[&4AdminChat&8] &c{NICK}&8: &f{TEXT}";
    }

    @Contextual
    public static class TeleportSection {
        public String actionBarMessage = "&aTeleporting in &f{TIME}";
        public String cancel = "&8» &cYou've moved, teleportation canceled!";
        public String teleported = "&8» &aTeleported!";
        public String teleporting = "&8» &aTeleporting...";
        public String haveTeleport = "&8» &cYou are in teleport!";
    }

    @Contextual
    public static class ChatSection {
        public String disabled = "&8» &cChat has been disabled by {NICK}!";
        public String enabled = "&8» &aThe chat has been enabled by {NICK}!";
        public String cleared = "&8» &6Chat has been cleared by {NICK}!";
        public String alreadyDisabled = "&8» &aChat already off!";
        public String alreadyEnabled = "&8» &aChat already on!";
        public String slowModeSet = "&8» &aSlowmode set to: {SLOWMODE}";
        public String slowMode = "&8» &cYou can write the next message for: &6{TIME}";
        public String disable = "&8» &cChat is currently disabled!";
        public String noCommand = "&8» &cCommand &e{COMMAND} &cdoesn't exists!";
    }

    @Contextual
    public static class OtherMessages {
        public String successfullyReloaded = "&8» &aThe plugin has been successfully reloaded!";
        public String successfulyyTeleported = "&8» &aSuccessfuly teleported to {PLAYER}!";
        public String successfulyyTeleportedPlayer = "&8» &aSuccessfuly teleported {PLAYER} to {ARG-PLAYER}!";
        public String alertMessagePrefix = "&c&lBROADCAST: &7{BROADCAST}";
        public String clearMessage = "&8» &cYour inventory has been cleared!";
        public String clearByMessage = "&8» &cPlayer {PLAYER} inventory cleared";
        public String disposalTitle = "&f&lTrash";
        public String foodMessage = "&8» &aYou've been feed!";
        public String foodOtherMessage = "&8» &aYou've fed the {PLAYER}";
        public String healMessage = "&8» &aYou've been heal!";
        public String healedMessage = "&8» &cHealed &6{PLAYER}";
        public String nullHatMessage = "&8» &cYou cannot use the /hat command.";
        public String repairMessage = "&8» &aRepaired!";
        public String skullMessage = "&8» &aPlayer {NICK} heads received";
        public String killSelf = "&8» &cYou kill yourself!";
        public String killedMessage = "&8» &cKilled {PLAYER}";
        public String speedBetweenZeroAndTen = "&8» &cEnter speed from 0 to 10!";
        public String speedSet = "&8» &cSpeed is set to {SPEED}";
        public String speedSettedBy = "&8» &cSpeed for {PLAYER} is set to {SPEED}";
        public String godMessage = "&8» &cGod is now {STATE}";
        public String godSetMessage = "&8» &cPlayer &6{PLAYER} god is now: {STATE}";
        public String flyMessage = "&8» &cFly is now {STATE}";
        public String flySetMessage = "&8» &cFly for &6{PLAYER} &cis now {STATE}";
        public String giveRecived = "&8» &cYou have received: &6{ITEM}";
        public String giveGiven = "&8» &cPlayer &6{PLAYER} &chas received &6{ITEM}";
        public List<String> whoisCommand = Arrays.asList("&8» &7Target name: &f{PLAYER}",
            "&8» &7Target UUID: &f{UUID}",
            "&8» &7Target address: &f{IP}",
            "&8» &7Target walk speed: &f{WALK-SPEED}",
            "&8» &7Target fly speed: &f{SPEED}",
            "&8» &7Target ping: &f{PING}ms",
            "&8» &7Target level: &f{LEVEL}",
            "&8» &7Target health: &f{HEALTH}",
            "&8» &7Target food level: &f{FOOD}");
        public String spawnSet = "&8» &aSpawn set!";
        public String spawnNoSet = "&8» &cSpawn is not set!";
        public String spawnTeleportedBy = "&8» &cYou have been teleported to spawn by {NICK}!";
        public String spawnTeleportedOther = "&8» &cYou teleported player {NICK} to spawn!";
        public String gameModeNotCorrect = "&8» &cNot a valid gamemode type";
        public String gameModeMessage = "&8» &cGamemode now is set to: {GAMEMODE}";
        public String gameModeSetMessage = "&8» &cGamemode for &6{PLAYER} &cnow is set to: &6{GAMEMODE}";
        public String enchantNotFound = "&8» &cNo enchantment found!";
        public String enchantSuccess = "&8» &cEnchantment &6{ENCHANTMENT} &cadded to your item!";
        public String enchantNotNumber = "&8» &cYou must enter a number!";
    }
}
