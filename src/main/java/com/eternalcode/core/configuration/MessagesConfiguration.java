/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.configuration;

import net.dzikoysk.cdn.entity.Description;

import java.io.Serializable;

public final class MessagesConfiguration implements Serializable {
    @Description({"# ",
        "# This is the pat of configuration file for EternalCore.",
        "# ",
        "# if you need help with the configration or have any questions related to EternalCore, join us in our Discord",
        "# ",
        "# Discord: https://dc.eternalcode.pl/",
        "# Website: https://eternalcode.pl/", " "})

    public String permissionMessage = "&8» &cYou don't have permission to perform this command! &7({PERMISSION})";
    public String offlinePlayer = "&8» &cThis player is currently offline!";
    public String notNumber = "&8» &cArgument isnt a number!";
    public String numberBiggerThanOrEqualZero = "&8» &cThe number must be greater than or equal to 0!";

    @Description({" ", "# HelpOp Messages"})
    public String helpOpFormat = "&8[&4HelpOp&8] &c{TEXT}";
    public String helpOpSend = "&8» &aThis message has been successfully sent to administration";

    @Description({" ", "# Chat messages"})
    public String chatDisabled = "&8» &cChat has been disabled by {NICK}!";
    public String chatEnabled = "&8» &aThe chat has been enabled by {NICK}!";
    public String chatCleared = "&8» &6Chat has been cleared by {NICK}!";
    public String chatAlreadyDisabled = "&8» &aChat already off!";
    public String chatAlreadyEnabled = "&8» &aChat already on!";
    public String chatSlowModeSet = "&8» &aSlowmode set to: {SLOWMODE}";
    public String chatSlowMode = "&8» &cYou can write the next message for: &6{TIME}";
    public String chatDisable = "&8» &cChat is currently disabled!";
    public String chatNoCommand = "&8» &cCommand &e{COMMAND} &cdoesnt exists!";

    @Description({" ", "# Simple Messages"})
    public String successfullyReloaded = "&8» &aThe plugin has been successfully reloaded!";
    public String alertMessagePrefix = "&c&lBROADCAST: &7{BROADCAST}";
    public String anvilGuiOpenMessage = "&8» &aSuccessfully opened anvil.";
    public String cartographyTableGuiOpenMessage = "&8» &aSuccessfully opened CartographyTable.";
    public String inventoryCleared = "&8» &cYour inventory has been cleared!";
    public String disposalTitle = "&f&lTrash";
    public String disposalGuiOpenMessage = "&8» &aSuccessfully opened Trash.";
    public String enderchestGuiOpenPlayerMessage = "&8» &aSuccessfully opened EnderChest.";
    public String foodMessage = "&8» &aYou've been feed!";
    public String healMessage = "&8» &aYou've been heal!";
    public String nullHatMessage = "&8» &cYou cannot use the /hat command.";
    public String adminChatFormat = "&8[&4AdminChat&8] &c{TEXT}";
    public String gammaEnabled = "&8» &aGamma has been enabled!";
    public String gammaDisable = "&8» &cGamma has been disabled!";
}
