package com.eternalcode.core.configuration.implementation.lang;

import com.eternalcode.core.chat.notification.NoticeType;
import com.eternalcode.core.chat.notification.Notification;
import com.eternalcode.core.configuration.ReloadableMessages;
import com.eternalcode.core.language.Language;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;
import java.util.List;

@Getter
@Accessors(fluent = true)
public class PLMessagesConfiguration implements ReloadableMessages {

    public PLArgumentSection argument = new PLArgumentSection();
    public PLFormatSection format = new PLFormatSection();
    public PLHelpOpSection helpOp = new PLHelpOpSection();
    public PLAdminChatSection adminChat = new PLAdminChatSection();
    public PLTeleportSection teleport = new PLTeleportSection();
    public PLChatSection chat = new PLChatSection();
    public PLWarpSection warp = new PLWarpSection();
    public PLHomeSection home = new PLHomeSection();
    public PLPrivateMessageSection privateMessage = new PLPrivateMessageSection();
    public PLTpaSection tpa = new PLTpaSection();
    public PLEventMessageSection eventMessages = new PLEventMessageSection();
    public PLOtherMessages other = new PLOtherMessages();
    public PLAfkSection afk = new PLAfkSection();

    @Getter
    @Contextual
    public static class PLArgumentSection implements ArgumentSection {
        public Notification permissionMessage = Notification.chat("<dark_red>Błąd: <red>Nie masz uprawnień do tej komendy! <gray>({PERMISSIONS})");
        public Notification usageMessage = Notification.chat("<dark_gray>» <yellow>Poprawne użycie: <gray>{USAGE}");
        public Notification usageMessageHead = Notification.chat("<dark_gray>» <yellow>Poprawne użycie:");
        public Notification usageMessageEntry = Notification.chat("<dark_gray>» <gray>{USAGE}");
        public Notification offlinePlayer = Notification.chat("<dark_red>Błąd: <red>Ten gracz jest offline!");
        public Notification onlyPlayer = Notification.chat("<dark_red>Błąd: <red>Komenda tylko dla graczy!");
        public Notification numberBiggerThanOrEqualZero = Notification.chat("<dark_red>Błąd: <red>Liczba musi byc równa lub większa 0!");
        public Notification noItem = Notification.chat("<dark_red>Błąd: <red>Musisz mieć item w ręce!");
        public Notification noMaterial = Notification.chat("<dark_red>Błąd: <red>Taki material nie istnieje!");
        public Notification noArgument = Notification.chat("<dark_red>Błąd: <red>Taki argument nie istnieje!");
        public Notification noDamaged = Notification.chat("<dark_red>Błąd: <red>Ten przedmiot nie może być naprawiony!");
        public Notification noDamagedItems = Notification.chat("<dark_red>Błąd: <red>Musisz mieć uszkodzone przedmioty!");
        public Notification noEnchantment = Notification.chat("<dark_red>Błąd: <red>Taki enchant nie istnieje!");
        public Notification noValidEnchantmentLevel = Notification.chat("<dark_red>Błąd: <red>Ten poziom zaklęcia nie jest wspierany!");
    }

    @Getter
    @Contextual
    public static class PLFormatSection implements Format {
        public String enable = "<green>włączona";
        public String disable = "<red>wyłączona";
    }

    @Getter
    @Contextual
    public static class PLHelpOpSection implements HelpOpSection {
        public Notification format = Notification.chat("<dark_gray>[<dark_red>HelpOp<dark_gray>] <yellow>{NICK}<dark_gray>: <white>{TEXT}");
        public Notification send = Notification.chat("<dark_gray>» <green>Wiadomość została wysłana do administracji");
        public Notification coolDown = Notification.chat("<dark_gray>» <red>Możesz użyć tej komendy dopiero za <gold>{TIME}!");
    }

    @Getter
    @Contextual
    public static class PLAdminChatSection implements AdminChatSection {
        public Notification format = Notification.chat("<dark_gray>[<dark_red>Administracja<dark_gray>] <red>{NICK}<dark_gray>: <white>{TEXT}");
    }

    @Getter
    @Contextual
    public static class PLTeleportSection implements TeleportSection {
        // teleport
        public Notification teleportedToPlayer = Notification.chat("<dark_gray>» <green>Przeteleportowano do {PLAYER}!");
        public Notification teleportedPlayerToPlayer = Notification.chat("<dark_gray>» <green>Przeteleportowano {PLAYER} do {ARG-PLAYER}!");

        // Task
        public Notification teleportTimerFormat = Notification.actionbar("<green>Teleportacja za <white>{TIME}");
        public Notification teleported = Notification.of("<dark_gray>» <green>Przeteleportowano!", NoticeType.CHAT, NoticeType.ACTIONBAR);
        public Notification teleporting = Notification.chat("<dark_gray>» <green>Teleportuje...");
        public Notification teleportTaskCanceled = Notification.chat("<dark_red>Błąd: <red>Ruszyłeś się, teleportacja przerwana!");
        public Notification teleportTaskAlreadyExist = Notification.chat("<dark_red>Błąd: <red>Teleportujesz się już!");

        // Coordinates XYZ
        public Notification teleportedToCoordinates = Notification.chat("<dark_gray>» <gold>Przeteleportowano na x: <red>{X}<gold>, y: <red>{Y}<gold>, z: <red>{Z}");
        public Notification teleportedSpecifiedPlayerToCoordinates = Notification.chat("<dark_gray>» <gold>Przeteleportowano <red>{PLAYER} <gold>na x: <red>{X}<gold>, y: <red>{Y}<gold>, z: <red>{Z}");

        // Back
        public Notification teleportedToLastLocation = Notification.chat("<dark_gray>» <gray>Przeteleportowano do poprzedniej lokalizacji!");
        public Notification teleportedSpecifiedPlayerLastLocation = Notification.chat("<dark_gray>» <gray>Przeteleportowano {PLAYER} do poprzedniej lokalizacji!");
        public Notification lastLocationNoExist = Notification.chat("<dark_gray>» <red>Nie ma zapisanej poprzedniej lokalizacji!");
    }

    @Getter
    @Contextual
    public static class PLChatSection implements ChatSection {
        public Notification disabled = Notification.chat("<dark_gray>» <red>Czat został wyłączony przez <gold>{NICK}<red>!");
        public Notification enabled = Notification.chat("<dark_gray>» <green>Czat został włączony przez <white>{NICK}<green>!");
        public Notification cleared = Notification.chat("<dark_gray>» <gold>Czat został wyczyszczony przez <red>{NICK}<gold>!");
        public Notification alreadyDisabled = Notification.chat("<dark_red>Błąd: <red>Czat jest juz wyłączony!");
        public Notification alreadyEnabled = Notification.chat("<dark_red>Błąd: <red>Czat jest juz włączony!");
        public Notification slowModeSet = Notification.chat("<dark_gray>» <green>Slowmode został ustawiony na {SLOWMODE}");
        public Notification slowMode = Notification.chat("<dark_gray>» <red>Następną wiadomość możesz wysłać za: <gold>{TIME}<red>!");
        public Notification disabledChatInfo = Notification.chat("<dark_gray>» <red>Czat jest aktualnie wyłączony!");
        public Notification noCommand = Notification.chat("<dark_gray>» <red>Komenda <yellow>{COMMAND} <red>nie istnieje!");
    }

    @Getter
    @Contextual
    public static class PLTpaSection implements TpaSection {
        public Notification tpaSelfMessage = Notification.chat("<dark_red>Błąd: <red>Nie możesz siebie teleportować!");
        public Notification tpaAlreadySentMessage = Notification.chat("<dark_red>Błąd: <red>Wysłałeś już prose o teleportacje!");
        public Notification tpaSentMessage = Notification.chat("<dark_gray>» <green>Wysłałeś prośbę o teleportacje do gracza: <gray>{PLAYER}<green>!");

        public List<Notification> tpaReceivedMessage = List.of(
            Notification.chat("<dark_gray>» <green>Otrzymałeś prośbę o teleportacje od gracza: <gray>{PLAYER}<green>!"), 
            Notification.chat("<dark_gray>» <gold>/tpaccept {PLAYER} <green>aby zaakceptować!"), 
            Notification.chat("<dark_gray>» <gold>/tpdeny {PLAYER} <green>aby odrzucić!")
        );

        public Notification tpaDenyNoRequestMessage = Notification.chat("<dark_red>Błąd: <red>Nie masz prośby o teleportacje od tego gracza!");
        public Notification tpaDenyDoneMessage = Notification.chat("<dark_gray>» <red>Odrzuciłeś próśb o teleportacje od gracza: <gray>{PLAYER}<red>!");
        public Notification tpaDenyReceivedMessage = Notification.chat("<dark_gray>» <red>Gracz: {PLAYER} odrzucił twoją prośbę o teleportacje!");
        public Notification tpaDenyAllDenied = Notification.chat("<dark_gray>» <red>Wszystkie prośby o teleportacje zostały odrzucone!");

        public Notification tpaAcceptMessage = Notification.chat("<dark_gray>» <green>Zaakceptowałeś teleportacje od gracza: <gray>{PLAYER}<green>!");
        public Notification tpaAcceptNoRequestMessage = Notification.chat("<dark_red>Błąd: <red>Ten gracz nie wysłał ci prośby o teleportacje!");
        public Notification tpaAcceptReceivedMessage = Notification.chat("<dark_gray>» <green>Gracz: <gray>{PLAYER} <green>zaakceptował twoją prośbę o teleportacje!");
        public Notification tpaAcceptAllAccepted = Notification.chat("<dark_gray>» <green>Wszystkie prośby o teleportacje zostały zaakceptowane!");
    }

    @Getter
    @Contextual
    public static class PLWarpSection implements WarpSection {
        public Notification availableList = Notification.chat("<dark_gray>» Lista warpów: {WARPS}");
        public Notification notExist = Notification.chat("<dark_gray>» <red>Nie odnaleziono takiego warpa!");
        public Notification create = Notification.chat("<dark_gray>» <gray>Stworzono warpa {NAME}!");
        public Notification remove = Notification.chat("<dark_gray>» <gray>Usunięto warpa {NAME}!");
    }

    @Getter
    @Contextual
    public static class PLHomeSection implements HomeSection {
        public Notification notExist = Notification.chat("<dark_gray>» <red>Nie ma takiego domu!");
        public Notification create = Notification.chat("<dark_gray>» <gray>Stworzono home {HOME}!");
        public Notification delete = Notification.chat("<dark_gray>» <gray>Usunięto home {HOME}!");
    }

    @Getter
    @Contextual
    public static class PLPrivateMessageSection implements PrivateMessageSection {
        public Notification noReply = Notification.chat("<dark_gray>» <red>Nie masz komu odpowiedzieć");
        public Notification privateMessageYouToTarget = Notification.chat("<dark_gray>[<gray>Ty -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");
        public Notification privateMessageTargetToYou = Notification.chat("<dark_gray>[<gray>{SENDER} -> <white>Ty<dark_gray>]<gray>: <white>{MESSAGE}");

        public Notification socialSpyMessage = Notification.chat("<dark_gray>[<red>ss<dark_gray>] <dark_gray>[<gray>{SENDER} -> <white>{TARGET}<dark_gray>]<gray>: <white>{MESSAGE}");
        public Notification socialSpyEnable = Notification.chat("<dark_gray>» <green>SocialSpy został włączony!");
        public Notification socialSpyDisable = Notification.chat("<dark_gray>» <red>SocialSpy został wyłączony!");

        public Notification ignorePlayer = Notification.chat("<dark_gray>» <gray>Zignorowano gracza <red>{PLAYER}<gray>!");
        public Notification unIgnorePlayer = Notification.chat("<dark_gray>» <gray>Odignorowano gracza <green>{PLAYER}<gray>!");
    }

    @Getter
    @Contextual
    public static class PLAfkSection implements AfkSection {
        public Notification afkOn = Notification.chat("<dark_gray>» <gray>{PLAYER} jest AFK!");
        public Notification afkOff = Notification.chat("<dark_gray>» <gray>{PLAYER} nie jest już AFK!");
    }

    @Getter
    @Contextual
    public static class PLEventMessageSection implements EventMessagesSection {
        public List<Notification> deathMessage = List.of(Notification.chat("<dark_gray>» <gray>{PLAYER} <red>zginął!"));

        public List<Notification> joinMessage = List.of(
            Notification.chat("<dark_gray>» <gray>{PLAYER} <green>dołączył do serwera!"),
            Notification.chat("<dark_gray>» <gray>Witaj na serwerze <green>{PLAYER}<gray>!")
        );

        public List<Notification> firstJoinMessage = List.of(
            Notification.chat("<dark_gray>» <gray>{PLAYER} <green>dołączył do serwera po raz pierwszy!"),
            Notification.chat("<dark_gray>» <gray>{PLAYER} <green>zawitał u nas po raz pierwszy!")
        );

        public List<Notification> quitMessage = List.of(
            Notification.chat("<dark_gray>» <gray>{PLAYER} <red>wylogował się z serwera!"),
            Notification.chat("<dark_gray>» <gray>{PLAYER} <red>opuścił serwer!")
        );

        public Notification welcomeTitle = Notification.chat("<yellow>EternalCode.pl");

        public Notification welcomeSubtitle = Notification.chat("<yellow>Witaj ponownie na serwerze!");
    }

    @Getter
    @Contextual
    public static class PLOtherMessages implements OtherMessages {
        public String alertMessagePrefix = "<red><bold>OGŁOSZENIE: <gray>{BROADCAST}";

        public Notification inventoryClearMessage = Notification.chat("<dark_gray>» <green>Wyczyszczono ekwipunek!");
        public Notification inventoryClearMessageBy = Notification.chat("<dark_gray>» <green>Wyczyszczono ekwipunek gracza {PLAYER}");

        public String disposalTitle = "<white><bold>Kosz";

        public Notification feedMessage = Notification.chat("<dark_gray>» <green>Zostałeś najedzony!");
        public Notification feedMessageBy = Notification.chat("<dark_gray>» <green>Najadłeś gracza {PLAYER}");

        public Notification healMessage = Notification.chat("<dark_gray>» <green>Zostałeś uleczony!");
        public Notification healMessageBy = Notification.chat("<dark_gray>» <green>Uleczyłeś gracza {PLAYER}");

        public Notification repairMessage = Notification.chat("<dark_gray>» <green>Naprawiono!");

        public Notification skullMessage = Notification.chat("<dark_gray>» <green>Otrzymałeś głowę gracza {PLAYER}");

        public Notification killSelf = Notification.chat("<dark_gray>» <red>Popełniłeś samobójstwo!");
        public Notification killedMessage = Notification.chat("<dark_gray>» <red>Zabito gracza {PLAYER}");

        public Notification speedBetweenZeroAndTen = Notification.chat("<dark_red>Błąd: <red>Ustaw speed w przedziale 0-10!");
        public Notification speedSet = Notification.chat("<dark_gray>» <green>Ustawiono speed na {SPEED}");
        public Notification speedSetBy = Notification.chat("<dark_gray>» <red>Ustawiono {PLAYER} speeda na {SPEED}");

        public Notification godMessage = Notification.chat("<dark_gray>» <green>God został {STATE}");
        public Notification godSetMessage = Notification.chat("<dark_gray>» <green>God dla gracza <white>{PLAYER} <green>został {STATE}");

        public Notification flyMessage = Notification.chat("<dark_gray>» <green>Latanie zostało {STATE}");
        public Notification flySetMessage = Notification.chat("<dark_gray>» <green>Latanie dla gracza <white>{PLAYER} <green>zostało {STATE}");

        public Notification giveReceived = Notification.chat("<dark_gray>» <green>Otrzymałeś <gold>{ITEM}");
        public Notification giveGiven = Notification.chat("<dark_gray>» <green>Gracz <white>{PLAYER} <green>otrzymał: <gold>{ITEM}");

        public Notification spawnSet = Notification.chat("<dark_gray>» <green>Ustawiono spawn!");
        public Notification spawnNoSet = Notification.chat("<dark_red>Błąd: <red>Spawn nie jest ustawiony!");
        public Notification spawnTeleportedBy = Notification.chat("<dark_gray>» <green>Zostałeś przeteleportowany na spawn przez {PLAYER}!");
        public Notification spawnTeleportedOther = Notification.chat("<dark_gray>» <green>Gracz <white>{PLAYER} <green>został przeteleportowany na spawn!");

        public Notification gameModeNotCorrect = Notification.chat("<dark_red>Błąd: <red>Nie prawidłowy typ!");
        public Notification gameModeMessage = Notification.chat("<dark_gray>» <green>Ustawiono tryb gry na: {GAMEMODE}");
        public Notification gameModeSetMessage = Notification.chat("<dark_gray>» <green>Ustawiono tryb gry graczowi <white>{PLAYER} <green>na: <white>{GAMEMODE}");

        public Notification pingMessage = Notification.chat("<dark_gray>» <green>Twój ping: <white>{PING}ms");
        public Notification pingOtherMessage = Notification.chat("<dark_gray>» <green>Gracz <white>{PLAYER} <green>ma: <white>{PING}ms");

        public Notification onlineMessage = Notification.chat("<dark_gray>» <gold>Na serwerze jest: <white>{ONLINE} <gold>graczy online!");

        public Notification listMessage = Notification.chat("<dark_gray>» <gold>Na serwerze jest: <dark_gray>(<gray>{ONLINE}<dark_gray>)<gray>: <white>{PLAYERS}");

        public Notification itemChangeNameMessage = Notification.chat("<dark_gray>» <gray>Nowa nazwa itemu: <red>{ITEM_NAME}");
        public Notification itemClearNameMessage = Notification.chat("<dark_gray>» <gray>Wyczyszczono nazwę itemu!");

        public Notification itemChangeLoreMessage = Notification.chat("<dark_gray>» <gray>Nowa linia lore: <red>{ITEM_LORE}");
        public Notification itemClearLoreMessage = Notification.chat("<dark_gray>» <gray>Wyczyszczono linie lore!");

        public Notification itemFlagRemovedMessage = Notification.chat("<dark_gray>» <gray>Usunięto flagę: <red>{ITEM_FLAG}");
        public Notification itemFlagAddedMessage = Notification.chat("<dark_gray>» <gray>Dodano flagę: <red>{ITEM_FLAG}");
        public Notification itemFlagClearedMessage = Notification.chat("<dark_gray>» <gray>Wyczyszczono flagi!");

        public Notification enchantedMessage = Notification.chat("<dark_gray>» <gold>Item w ręce został zaklęty!");

        public Notification languageChanged = Notification.chat("<dark_gray>» <gold>Zmieniono język na <red>Polski<gold>!");

        public List<String> whoisCommand = List.of("<dark_gray>» <gray>Gracz: <white>{PLAYER}",
            "<dark_gray>» <gray>UUID: <white>{UUID}",
            "<dark_gray>» <gray>IP: <white>{IP}",
            "<dark_gray>» <gray>Szybkość chodzenia: <white>{WALK-SPEED}",
            "<dark_gray>» <gray>Szybkość latania: <white>{SPEED}",
            "<dark_gray>» <gray>Opóźnienie: <white>{PING}ms",
            "<dark_gray>» <gray>Poziom: <white>{LEVEL}",
            "<dark_gray>» <gray>Zdrowie: <white>{HEALTH}",
            "<dark_gray>» <gray>Poziom najedzenia: <white>{FOOD}"
        );
    }

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "lang" + File.separator + "pl_messages.yml");
    }

    @Override
    public Language getLanguage() {
        return Language.PL;
    }
}
