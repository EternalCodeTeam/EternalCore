package com.eternalcode.core.configuration.lang;

import com.eternalcode.core.configuration.AbstractConfigWithResource;
import com.eternalcode.core.language.Language;
import com.eternalcode.core.language.Messages;
import net.dzikoysk.cdn.entity.Contextual;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class PLMessagesConfiguration extends AbstractConfigWithResource implements Messages {
    private final Language language = Language.PL;

    public PLArgumentSection argument = new PLArgumentSection();
    public PLHelpOpSection helpOp = new PLHelpOpSection();
    public PLAdminChatSection adminChat = new PLAdminChatSection();
    public PLTeleportSection teleport = new PLTeleportSection();
    public PLChatSection chat = new PLChatSection();
    public PLOtherMessages other = new PLOtherMessages();
    public PLWarpSection warp = new PLWarpSection();

    public PLMessagesConfiguration(File folder, String child) {
        super(folder, child);
    }

    public PLArgumentSection argument() {
        return this.argument;
    }

    public PLHelpOpSection helpOp() {
        return this.helpOp;
    }

    public PLAdminChatSection adminChat() {
        return this.adminChat;
    }

    public PLTeleportSection teleport() {
        return this.teleport;
    }

    public PLChatSection chat() {
        return this.chat;
    }

    public PLOtherMessages other() {
        return this.other;
    }

    public PLWarpSection warp() {
        return this.warp;
    }

    public Language getLanguage() {
        return this.language;
    }

    @Contextual
    public static class PLWarpSection implements WarpSection {
        public String availableList = "&8» Lista warpów: {WARPS}";
        public String notExist = "&8» &cNie odnaleziono takiego warpa!";
        public String noPermission = "&8» &cNie masz uprawnien do tego warpa!";
        public String disabled = "&8» &cTen warp jest aktualnie wyłączony!";

        public String availableList() {
            return this.availableList;
        }

        public String notExist() {
            return this.notExist;
        }

        public String noPermission() {
            return this.noPermission;
        }

        public String disabled() {
            return this.disabled;
        }
    }

    @Contextual
    public static class PLArgumentSection implements ArgumentSection {
        public String permissionMessage = "&4Blad: &cNie masz uprawnien do tej komendy! &7({PERMISSIONS})";
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

    @Contextual
    public static class PLHelpOpSection implements HelpOpSection {
        public String format = "&8[&4HelpOp&8] &e{NICK}&8: &f{TEXT}";
        public String send = "&8» &aWiadomość została wysłana do administracji";
        public String coolDown = "&8» &cMożesz użyć tej komendy dopiero za &6{TIME}!";

        public String format() {
            return this.format;
        }

        public String send() {
            return this.send;
        }

        public String coolDown() {
            return this.coolDown;
        }
    }

    @Contextual
    public static class PLAdminChatSection implements AdminChatSection {
        public String format = "&8[&4Administracja&8] &c{NICK}&8: &f{TEXT}";

        public String format() {
            return this.format;
        }
    }

    @Contextual
    public static class PLTeleportSection implements TeleportSection {
        public String actionBarMessage = "&aTeleportacja za &f{TIME}";
        public String cancel = "&4Blad: &cRuszyłeś się, teleportacja przerwana!";
        public String teleported = "&8» &aPrzeteleportowano!";
        public String teleporting = "&8» &aTeleportuje...";
        public String haveTeleport = "&4Blad: &cTeleportujesz się już!";

        public String actionBarMessage() {
            return this.actionBarMessage;
        }

        public String cancel() {
            return this.cancel;
        }

        public String teleported() {
            return this.teleported;
        }

        public String teleporting() {
            return this.teleporting;
        }

        public String haveTeleport() {
            return this.haveTeleport;
        }
    }

    @Contextual
    public static class PLChatSection implements ChatSection {
        public String disabled = "&8» &cCzat został wyłączony przez &6{NICK}&c!";
        public String enabled = "&8» &aCzat został włączony przez &f{NICK}&a!";
        public String cleared = "&8» &6Czat zotał wyczyszczony przez &c{NICK}&6!";
        public String alreadyDisabled = "&4Blad: &cCzat jest juz wylaczony!";
        public String alreadyEnabled = "&4Blad: &cCzat jest juz wlaczony!";
        public String slowModeSet = "&8» &aSlowmode został ustawiony na {SLOWMODE}";
        public String slowMode = "&8» &cNastępną wiadomość możesz wysłać za: &6{TIME}&c!";
        public String disabledChatInfo = "&8» &cCzat jest aktualnie wyłaczony!";
        public String noCommand = "&8» &cKomenda &e{COMMAND} &cnie istnieje!";

        public String disabled() {
            return this.disabled;
        }

        public String enabled() {
            return this.enabled;
        }

        public String cleared() {
            return this.cleared;
        }

        public String alreadyDisabled() {
            return this.alreadyDisabled;
        }

        public String alreadyEnabled() {
            return this.alreadyEnabled;
        }

        public String slowModeSet() {
            return this.slowModeSet;
        }

        public String slowMode() {
            return this.slowMode;
        }

        public String disabledChatInfo() {
            return this.disabledChatInfo;
        }

        public String noCommand() {
            return this.noCommand;
        }
    }

    @Contextual
    public static class PLOtherMessages implements OtherMessages {
        public String successfullyReloaded = "&8» &aPrzeładowano plugin!";
        public String successfullyTeleported = "&8» &aPrzeteleportowano do {PLAYER}!";
        public String successfullyTeleportedPlayer = "&8» &aPrzeteleportowano {PLAYER} do {ARG-PLAYER}!";
        public String alertMessagePrefix = "&c&lOGLOSZENIE: &7{BROADCAST}";
        public String clearMessage = "&8» &aWyczyszczono ekwipunek!";
        public String clearByMessage = "&8» &aWyczyszczono ekwipunek gracza {PLAYER}";
        public String disposalTitle = "&f&lKosz";
        public String foodMessage = "&8» &aZostałeś najedzony!";
        public String foodOtherMessage = "&8» &aNajadłeś gracza {PLAYER}";
        public String healMessage = "&8» &aZostałeś uleczony!";
        public String healedMessage = "&8» &aUleczyłeś gracza {PLAYER}";
        public String nullHatMessage = "&4Blad: &cNie możesz użyć /hat!";
        public String repairMessage = "&8» &aNaprawiono!";
        public String skullMessage = "&8» &aOtrzymałeś głowę gracza {PLAYER}";
        public String killSelf = "&8» &cPopełniłeś samobójstwo!";
        public String killedMessage = "&8» &cZabito gracza {PLAYER}";
        public String speedBetweenZeroAndTen = "&4Blad: &cUstaw speed w przedziale 0-10!";
        public String speedSet = "&8» &aUstawiono speed na {SPEED}";
        public String speedSetBy = "&8» &cUstawiono {PLAYER} speeda na {SPEED}";
        public String godMessage = "&8» &aGod został {STATE}";
        public String godSetMessage = "&8» &aGod dla gracza &f{PLAYER} &azostał {STATE}";
        public String flyMessage = "&8» &aLatanie zostało {STATE}";
        public String flySetMessage = "&8» &aLatanie dla gracza &f{PLAYER} &azostało {STATE}";
        public String giveReceived = "&8» &aOtrzymałeś &6{ITEM}";
        public String giveGiven = "&8» &aGracz &f{PLAYER} &aotrzymał: &6{ITEM}";
        public String spawnSet = "&8» &aUstawiono spawn!";
        public String spawnNoSet = "&4Blad: &cSpawn nie jest ustawiony!";
        public String spawnTeleportedBy = "&8» &aZostałeś przeteleportowany na spawn przez {NICK}!";
        public String spawnTeleportedOther = "&8» &aGracz &f{NICK} &azostał przeteleportowany na spawn!";
        public String gameModeNotCorrect = "&4Blad: &cNie prawidłowy typ!";
        public String gameModeMessage = "&8» &aUstawiono tryb gry na: {GAMEMODE}";
        public String gameModeSetMessage = "&8» &aUstawiono tryb gry graczowi &f{PLAYER} &ana: &f{GAMEMODE}";
        public String pingMessage = "&8» &aTwój ping: &f{PING}ms";
        public String pingOtherMessage = "&8» &aGracz &f{PLAYER} &ama: &f{PING}ms";
        public String onlineMessage = "&8» &6Na serwerze jest: &f{ONLINE} &6graczy online!";
        public String listMessage = "&8» &6Na serwerze jest: &8(&7{ONLINE}&8)&7: &f{PLAYERS}";
        public String tposMessage = "&8» &6Przeteleportowano na x: &c{X}&6, y: &c{Y}&6, z: &c{Z}";
        public String tposByMessage = "&8» &6Przeteleportowano &c{PLAYER} &6na x: &c{X}&6, y: &c{Y}&6, z: &c{Z}";
        public String nameMessage = "&8» &6Nowa nazwa itemu: &c{NAME}";
        public String enchantedMessage = "&8» &6Item w rece zostal zenchantowany!";
        public String languageChanged = "&8» &6Zmieniono język na &cPolski&6!";

        public List<String> whoisCommand = Arrays.asList("&8» &7Gracz: &f{PLAYER}",
                "&8» &7UUID: &f{UUID}",
                "&8» &7IP: &f{IP}",
                "&8» &7Szybkość chodzenia: &f{WALK-SPEED}",
                "&8» &7Szybkość latania: &f{SPEED}",
                "&8» &7Opóźnienie: &f{PING}ms",
                "&8» &7Poziom: &f{LEVEL}",
                "&8» &7Zdrowie: &f{HEALTH}",
                "&8» &7Poziom najedzenia: &f{FOOD}");

        public String successfullyReloaded() {
            return this.successfullyReloaded;
        }

        public String successfullyTeleported() {
            return this.successfullyTeleported;
        }

        public String successfullyTeleportedPlayer() {
            return this.successfullyTeleportedPlayer;
        }

        public String alertMessagePrefix() {
            return this.alertMessagePrefix;
        }

        public String clearMessage() {
            return this.clearMessage;
        }

        public String clearByMessage() {
            return this.clearByMessage;
        }

        public String disposalTitle() {
            return this.disposalTitle;
        }

        public String foodMessage() {
            return this.foodMessage;
        }

        public String foodOtherMessage() {
            return this.foodOtherMessage;
        }

        public String healMessage() {
            return this.healMessage;
        }

        public String healedMessage() {
            return this.healedMessage;
        }

        public String nullHatMessage() {
            return this.nullHatMessage;
        }

        public String repairMessage() {
            return this.repairMessage;
        }

        public String skullMessage() {
            return this.skullMessage;
        }

        public String killSelf() {
            return this.killSelf;
        }

        public String killedMessage() {
            return this.killedMessage;
        }

        public String speedBetweenZeroAndTen() {
            return this.speedBetweenZeroAndTen;
        }

        public String speedSet() {
            return this.speedSet;
        }

        public String speedSetBy() {
            return this.speedSetBy;
        }

        public String godMessage() {
            return this.godMessage;
        }

        public String godSetMessage() {
            return this.godSetMessage;
        }

        public String flyMessage() {
            return this.flyMessage;
        }

        public String flySetMessage() {
            return this.flySetMessage;
        }

        public String giveReceived() {
            return this.giveReceived;
        }

        public String giveGiven() {
            return this.giveGiven;
        }

        public String spawnSet() {
            return this.spawnSet;
        }

        public String spawnNoSet() {
            return this.spawnNoSet;
        }

        public String spawnTeleportedBy() {
            return this.spawnTeleportedBy;
        }

        public String spawnTeleportedOther() {
            return this.spawnTeleportedOther;
        }

        public String gameModeNotCorrect() {
            return this.gameModeNotCorrect;
        }

        public String gameModeMessage() {
            return this.gameModeMessage;
        }

        public String gameModeSetMessage() {
            return this.gameModeSetMessage;
        }

        public String pingMessage() {
            return this.pingMessage;
        }

        public String pingOtherMessage() {
            return this.pingOtherMessage;
        }

        public String onlineMessage() {
            return this.onlineMessage;
        }

        public String listMessage() {
            return this.listMessage;
        }

        public String tposMessage() {
            return this.tposMessage;
        }

        public String tposByMessage() {
            return this.tposByMessage;
        }

        public String nameMessage() {
            return this.nameMessage;
        }

        public List<String> whoisCommand() {
            return this.whoisCommand;
        }

        public String enchantedMessage() {
            return this.enchantedMessage;
        }

        public String languageChanged() {
            return this.languageChanged;
        }
    }
}
