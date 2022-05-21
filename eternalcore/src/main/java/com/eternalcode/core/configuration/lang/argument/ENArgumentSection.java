package com.eternalcode.core.configuration.lang.argument;

import com.eternalcode.core.language.Messages;
import net.dzikoysk.cdn.entity.Contextual;

@Contextual
public class ENArgumentSection implements Messages.ArgumentSection {
    public String permissionMessage = "&8» &cYou don't have permission to perform this command! &7({PERMISSIONS})";
    public String usageMessage = "&8» &eCorrect usage: &7{USAGE}";
    public String offlinePlayer = "&8» &cThis player is currently offline!";
    public String onlyPlayer = "&8» &cCommand is only for players!";
    public String notNumber = "&8» &cArgument isnt a number!";
    public String numberBiggerThanOrEqualZero = "&8» &cThe number must be greater than or equal to 0!";
    public String noItem = "&8» &cYou need item to use this command!";
    public String noMaterial = "&8» &cThis item doesn't exist";
    public String noArgument = "&8» &cThis argument doesn't exist";
    public String noDamaged = "&8» &cThis item can't be repaired";
    public String noDamagedItems = "&8» &cYou need damaged items to use this command!";
    public String noEnchantment = "&8» &cThis enchantment doesn't exist";
    public String noValidEnchantmentLevel = "&8» &cThis enchantment level is not supported!";

    public String permissionMessage() {
        return this.permissionMessage;
    }

    public String usageMessage() {
        return this.usageMessage;
    }

    public String offlinePlayer() {
        return this.offlinePlayer;
    }

    public String onlyPlayer() {
        return this.onlyPlayer;
    }

    public String notNumber() {
        return this.notNumber;
    }

    public String numberBiggerThanOrEqualZero() {
        return this.numberBiggerThanOrEqualZero;
    }

    public String noItem() {
        return this.noItem;
    }

    public String noMaterial() {
        return this.noMaterial;
    }

    public String noArgument() {
        return this.noArgument;
    }

    public String noDamaged() {
        return this.noDamaged;
    }

    public String noDamagedItems() {
        return this.noDamagedItems;
    }

    public String noEnchantment() {
        return this.noEnchantment;
    }

    public String noValidEnchantmentLevel() {
        return this.noValidEnchantmentLevel;
    }
}
