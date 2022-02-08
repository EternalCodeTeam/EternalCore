/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.configuration;

import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

import java.io.Serializable;

public class MessagesConfiguration implements Serializable {
    @Description({"# ",
        "# This is the part of configuration file for EternalCore.",
        "# ",
        "# if you need help with the configration or have any questions related to EternalCore, join us in our Discord",
        "# ",
        "# Discord: https://dc.eternalcode.pl/",
        "# Website: https://eternalcode.pl/", " "})
    public MessagesSection messagesSection = new MessagesSection();

    @Contextual
    public static class MessagesSection {

        public String permissionMessage = "&8» &cYou don't have permission to perform this command! &7({PERMISSION})";
        public String offlinePlayer = "&8» &cThis player is currently offline!";
        public String onlyPlayer = "&8» &cCommand is only for players!";
        public String notNumber = "&8» &cArgument isnt a number!";
        public String numberBiggerThanOrEqualZero = "&8» &cThe number must be greater than or equal to 0!";
        public String noItem = "&8» &cYou need item to use this command!";

        @Description({" ", "# HelpOp Messages"})
        public String helpOpFormat = "&8[&4HelpOp&8] &c{TEXT}";
        public String helpOpSend = "&8» &aThis message has been successfully sent to administration";

        @Description({" ", "# AdminChat format"})
        public String adminChatFormat = "&8[&4AdminChat&8] &c{TEXT}";

        @Description({" ", "# Chat messages"})
        public String chatDisabled = "&8» &cChat has been disabled by {NICK}!";
        public String chatEnabled = "&8» &aThe chat has been enabled by {NICK}!";
        public String chatCleared = "&8» &6Chat has been cleared by {NICK}!";
        public String chatAlreadyDisabled = "&8» &aChat already off!";
        public String chatAlreadyEnabled = "&8» &aChat already on!";
        public String chatSlowModeSet = "&8» &aSlowmode set to: {SLOWMODE}";
        public String chatSlowMode = "&8» &cYou can write the next message for: &6{TIME}";
        public String chatDisable = "&8» &cChat is currently disabled!";
        public String chatNoCommand = "&8» &cCommand &e{COMMAND} &cdoesn't exists!";

        @Description({"", "# Kill message"})
        public String killMessage = "&8» &cYou were killed by {NICK}";
        public String killedMessage = "&8» &cKilled {NICK}";

        @Description({"", "# Speed message"})
        public String speedBetweenZeroAndTen = "&8» &cEnter speed from 0 to 10!";
        public String speedSetted = "&8» &cSpeed is set to {SPEED}";
        public String speedSet = "&8» &cSpeed for {NICK} is set to {SPEED}";

        @Description({" ", "# Simple Messages"})
        public String successfullyReloaded = "&8» &aThe plugin has been successfully reloaded!";
        public String successfulyyTeleported = "&8» &aSuccessfuly teleported to {PLAYER}!";
        public String alertMessagePrefix = "&c&lBROADCAST: &7{BROADCAST}";
        public String anvilGuiOpenMessage = "&8» &aSuccessfully opened anvil.";
        public String cartographyTableOpenMessage = "&8» &aSuccessfully opened CartographyTable.";
        public String inventoryCleared = "&8» &cYour inventory has been cleared!";
        public String disposalTitle = "&f&lTrash";
        public String disposalOpenMessage = "&8» &aSuccessfully opened Trash.";
        public String enderchestOpenMessage = "&8» &aSuccessfully opened EnderChest {PLAYER}";
        public String workbenchOpenMessage = "&8» &aSuccessfully opened WorkBench.";
        public String stonecutterOpenMessage = "&8» &aSuccessfully opened StoneCutter.";
        public String grindstoneOpenMessage = "&8» &aSuccessfully opened WorkBench.";
        public String foodMessage = "&8» &aYou've been feed!";
        public String foodMessageOther = "&8» &aYou've fed the {PLAYER}";
        public String healMessage = "&8» &aYou've been heal!";
        public String nullHatMessage = "&8» &cYou cannot use the /hat command.";
        public String repairMessage = "&8» &aRepaired!";
        public String skullMessage = "&8» &aPlayer {NICK} heads received";
    }
}
