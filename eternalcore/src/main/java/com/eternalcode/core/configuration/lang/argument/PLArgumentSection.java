package com.eternalcode.core.configuration.lang.argument;

import com.eternalcode.core.language.Messages;
import net.dzikoysk.cdn.entity.Contextual;

@Contextual
public class PLArgumentSection implements Messages.ArgumentSection {

    public String permissionMessage = "&4Blad: &cNie masz uprawnien do tej komendy! &7({PERMISSIONS})";
    public String usageMessage = "&8» &ePoprawne uzycie: &7{USAGE}";
    public String offlinePlayer = "&4Blad: &cTen gracz jest offline!";
    public String onlyPlayer = "&4Blad: &cKomenda tylko dla graczy!";
    public String notNumber = "&4Blad: &cArgument nie jest liczba!";
    public String numberBiggerThanOrEqualZero = "&4Blad: &cLiczba musi byc rowna lub wieksza 0!";
    public String noItem = "&4Blad: &cMusisz miec item w rece!";
    public String noMaterial = "&4Blad: &cTaki material nie istnieje!";
    public String noArgument = "&4Blad: &cTaki argument nie istnieje!";
    public String noDamaged = "&4Blad: &cTen przedmiot nie moze być naprawiony!";
    public String noDamagedItems = "&4Blad: &cMusisz miec uszkodzone przedmioty!";
    public String noEnchantment = "&4Blad: &cTaki enchant nie istnieje!";
    public String noValidEnchantmentLevel = "&4Blad: &cTen poziom enchantu nie jest wspierany!";

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
