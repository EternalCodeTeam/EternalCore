/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.configuration;

import net.dzikoysk.cdn.entity.Description;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public final class MessagesConfiguration implements Serializable {
    @Description("# Arguments")
    public String permissionMessage = "&8» &cYou don't have permission to perform this command! &7({PERMISSION})";
    public String offlinePlayer = "&8» &cThis player is currently offline!";
    public String notNumber = "&8» &cArgument isnt a number!";
    public String numberBiggerThanZero = "&8» &cLiczba musi byc wieksza niz 0!";

    @Description("")
    @Description("# Helpop messages")
    public String helpOpFormat = "&8[&4HelpOp&8] &c{TEXT}";
    public String helpOpSend = "&8» &aThis message has been successfully sent to administration";

    @Description("")
    @Description("# Chat messages")
    public String chatDisabled = "&8» &aThe chat has been off by {NICK}!";
    public String chatEnabled = "&8» &aThe chat has been on by {NICK}!";
    public String chatCleared = "&8» &aChat has been cleared by {NICK}!";
    public String chatAlreadyDisabled = "&8» &aChat already off!";
    public String chatAlreadyEnabled = "&8» &aChat already on!";
    public String chatSlowModeSet = "&8» &aSlowmode set to: {SLOWMODE}";
    public String chatSlowMode = "&8» &cYou can write the next message for: &6{TIME}";
    public String chatDisable = "&8» &cChat is currently disabled!";

    @Description("")
    @Description("# Others messages")
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


    @Description("# Scoreboard Style")
    public String scoreboardTitle = "&e&lExampleCraft.pl";

    public List<String> scoreboardStyle = Arrays.asList(
        "%player_seconds_lived%",
        "%server_tps_15_colored%",
        "%localtime_time%",
        ""
    );
}
