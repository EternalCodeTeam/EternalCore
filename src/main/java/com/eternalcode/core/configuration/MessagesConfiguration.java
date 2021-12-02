package com.eternalcode.core.configuration;

import net.dzikoysk.cdn.entity.Description;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public final class MessagesConfiguration implements Serializable {
    @Description("# Global Messages")
    @Description("Placeholders: {PERMISSION}, {BROADCAST")
    public String successfullyReloaded = "&8» &aThe plugin has been successfully reloaded!";
    public String permissionMessage = "&8» &cYou don't have permission to perform this command! &7({PERMISSION})";
    public String alertMessagePrefix = "&c&lBROADCAST: &7{BROADCAST}";
    public String anvilGuiOpenMessage = "&8» &aSuccessfully opened anvil.";
    public String cartographyTableGuiOpenMessage = "&8» &aSuccessfully opened CartographyTable.";
    public String offlinePlayer = "&8» &cThis player is currently offline!";
    public String inventoryCleared = "&8» &cYour inventory has been cleared!";
    public String disposalTitle = "&f&lTrash";
    public String disposalGuiOpenMessage = "&8» &aSuccessfully opened Trash.";
    public String enderchestGuiOpenPlayerMessage = "&8» &aSuccessfully opened EnderChest.";
    public String foodMessage = "&8» &aYou've been feed!";
    public String healMessage = "&8» &aYou've been heal!";
    public String nullHatMessage = "&8» &cYou cannot use the /hat command.";

    @Description("# Chat manager")
    @Description("Placeholders: {NICK}")
    public String chatDisabled = "&8» &aThe chat has been off by {NICK}!";
    public String chatEnabled = "&8» &aThe chat has been on by {NICK}!";
    public String chatCleared = "&8» &aChat has been cleared by {NICK}!";
    public String chatAlreadyDisabled = "&8» &aChat already off!";
    public String chatAlreadyEnabled = "&8» &aChat already on";

    @Description("# Gargabage Collector command style")
    public List<String> garbageCollectorMessage = Arrays.asList(
        "&8[&e⭐&8]",
        ""
    );
}
