package com.eternalcode.core.feature.kit.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLKitMessages extends OkaeriConfig implements KitMessages {

    @Comment({
        "# {KIT} - nazwa wyświetlana kitu, {KIT_NAME} - nazwa kitu, {COOLDOWN} - pozostały czas,",
        "# {PLAYTIME} - brakujący czas gry, {WORLDS} - wymagane światy, {GAME_MODES} - wymagane tryby gry, {AMOUNT} - liczba przedmiotów"
    })
    Notice notFound = Notice.chat("<red>✘ <dark_red>Kit <red>{KIT_NAME} <dark_red>nie istnieje!");
    Notice noKits = Notice.chat("<red>✘ <dark_red>Nie ma żadnych dostępnych kitów!");
    Notice claimed = Notice.chat("<color:#9d6eef>► <white>Odebrano kit <color:#9d6eef>{KIT}<white>!");
    Notice claimedToBuffer = Notice.chat(
        "<color:#9d6eef>► <white>Odebrano kit <color:#9d6eef>{KIT}<white>, ale <color:#9d6eef>{AMOUNT} <white>przedmiotów nie zmieściło się w ekwipunku.",
        "<hover:show_text:'<color:#9d6eef>Odebrać zapisane przedmioty?</color:#9d6eef>'><click:suggest_command:'/kit buffer'><dark_gray>» <gold>/kit buffer <color:#9d6eef>by je odebrać! <gray>(Kliknij)</gray></click></hover>"
    );
    Notice noPermission = Notice.chat("<red>✘ <dark_red>Nie masz dostępu do kitu <red>{KIT}<dark_red>!");
    Notice onCooldown = Notice.chat("<red>✘ <dark_red>Kit <red>{KIT} <dark_red>będzie dostępny za <red>{COOLDOWN}<dark_red>!");
    Notice alreadyClaimed = Notice.chat("<red>✘ <dark_red>Kit <red>{KIT} <dark_red>można odebrać tylko raz i został już odebrany!");
    Notice wrongWorld = Notice.chat("<red>✘ <dark_red>Kit <red>{KIT} <dark_red>można odebrać tylko w światach: <red>{WORLDS}<dark_red>!");
    Notice wrongGameMode = Notice.chat("<red>✘ <dark_red>Kit <red>{KIT} <dark_red>można odebrać tylko w trybach gry: <red>{GAME_MODES}<dark_red>!");
    Notice notEnoughPlaytime = Notice.chat("<red>✘ <dark_red>Do odebrania kitu <red>{KIT} <dark_red>brakuje ci <red>{PLAYTIME} <dark_red>czasu gry!");
    Notice inventoryFull = Notice.chat("<red>✘ <dark_red>Zrób miejsce w ekwipunku, aby odebrać kit <red>{KIT}<dark_red>!");
    Notice claimCancelled = Notice.chat("<red>✘ <dark_red>Nie udało się odebrać kitu <red>{KIT}<dark_red>!");
    Notice dataNotLoaded = Notice.chat("<red>✘ <dark_red>Twoje dane kitów jeszcze się wczytują, spróbuj za chwilę!");

    @Comment("# {TYPE} - typ, {STATUS} - status, {ITEMS} - liczba przedmiotów, {CLAIMS} - liczba odebrań, {DESCRIPTION} - opis")
    Notice info = Notice.chat(
        "<color:#9d6eef>► <white>Kit <color:#9d6eef>{KIT} <dark_gray>(<gray>{KIT_NAME}<dark_gray>)",
        "{DESCRIPTION}",
        "<dark_gray>» <gray>Typ: <color:#9d6eef>{TYPE}",
        "<dark_gray>» <gray>Cooldown: <color:#9d6eef>{COOLDOWN}",
        "<dark_gray>» <gray>Przedmioty: <color:#9d6eef>{ITEMS}",
        "<dark_gray>» <gray>Światy: <color:#9d6eef>{WORLDS} <dark_gray>| <gray>Tryby gry: <color:#9d6eef>{GAME_MODES}",
        "<dark_gray>» <gray>Wymagany czas gry: <color:#9d6eef>{PLAYTIME}",
        "<dark_gray>» <gray>Odebrano razy: <color:#9d6eef>{CLAIMS}",
        "<dark_gray>» <gray>Status: {STATUS}"
    );

    @Comment({ " ", "# Schowek na przedmioty, które nie zmieściły się w ekwipunku" })
    Notice bufferEmpty = Notice.chat("<red>✘ <dark_red>Nie masz żadnych zapisanych przedmiotów z kitów!");
    Notice bufferClaimed = Notice.chat("<color:#9d6eef>► <white>Odebrano <color:#9d6eef>{AMOUNT} <white>przedmiotów ze schowka kitów, w schowku zostało: <color:#9d6eef>{REMAINING}<white>.");
    Notice bufferWaiting = Notice.chat(
        "<color:#9d6eef>► <white>W schowku kitów czeka na ciebie <color:#9d6eef>{AMOUNT} <white>przedmiotów!",
        "<hover:show_text:'<color:#9d6eef>Odebrać zapisane przedmioty?</color:#9d6eef>'><click:suggest_command:'/kit buffer'><dark_gray>» <gold>/kit buffer <color:#9d6eef>by je odebrać! <gray>(Kliknij)</gray></click></hover>"
    );

    @Comment({ " ", "# Administracja kitami, {PLAYER} - gracz" })
    Notice given = Notice.chat("<color:#9d6eef>► <white>Dano kit <color:#9d6eef>{KIT} <white>graczowi <color:#9d6eef>{PLAYER}<white>!");
    Notice received = Notice.chat("<color:#9d6eef>► <white>Otrzymano kit <color:#9d6eef>{KIT}<white>!");
    Notice usageReset = Notice.chat("<color:#9d6eef>► <white>Zresetowano cooldown kitu <color:#9d6eef>{KIT} <white>dla <color:#9d6eef>{PLAYER}<white>!");
    Notice invalidName = Notice.chat("<red>✘ <dark_red>Niepoprawna nazwa kitu <red>{KIT_NAME}<dark_red>! Dozwolone: a-z, 0-9, _ i - (max 32 znaki)");
    Notice reservedName = Notice.chat("<red>✘ <dark_red>Nazwa <red>{KIT_NAME} <dark_red>jest zarezerwowana dla komend /kit!");
    Notice alreadyExists = Notice.chat("<red>✘ <dark_red>Kit <red>{KIT_NAME} <dark_red>już istnieje!");
    Notice created = Notice.chat(
        "<color:#9d6eef>► <white>Stworzono kit <color:#9d6eef>{KIT_NAME} <white>(uprawnienie: <color:#9d6eef>{PERMISSION}<white>)!",
        "<hover:show_text:'<color:#9d6eef>Ustawić przedmioty kitu?</color:#9d6eef>'><click:suggest_command:'/kitadmin items {KIT_NAME}'><dark_gray>» <gold>/kitadmin items {KIT_NAME} <color:#9d6eef>by ustawić przedmioty! <gray>(Kliknij)</gray></click></hover>"
    );
    Notice deleted = Notice.chat("<color:#9d6eef>► <white>Usunięto kit <color:#9d6eef>{KIT_NAME}<white>!");
    Notice itemsEditorOpened = Notice.chat("<color:#9d6eef>► <white>Włóż przedmioty i zamknij ekwipunek, aby zapisać kit <color:#9d6eef>{KIT_NAME}<white>.");
    Notice itemsSaved = Notice.chat("<color:#9d6eef>► <white>Zapisano <color:#9d6eef>{AMOUNT} <white>przedmiotów w kicie <color:#9d6eef>{KIT_NAME}<white>!");
    Notice permissionChanged = Notice.chat("<color:#9d6eef>► <white>Uprawnienie kitu <color:#9d6eef>{KIT_NAME} <white>to teraz <color:#9d6eef>{PERMISSION}<white>!");
    Notice cooldownChanged = Notice.chat("<color:#9d6eef>► <white>Cooldown kitu <color:#9d6eef>{KIT_NAME} <white>to teraz <color:#9d6eef>{COOLDOWN}<white>!");
    Notice typeChanged = Notice.chat("<color:#9d6eef>► <white>Typ kitu <color:#9d6eef>{KIT_NAME} <white>to teraz <color:#9d6eef>{TYPE}<white>!");
    Notice emptyHand = Notice.chat("<red>✘ <dark_red>Musisz trzymać przedmiot w ręce!");
    Notice iconChanged = Notice.chat("<color:#9d6eef>► <white>Zmieniono ikonę kitu <color:#9d6eef>{KIT_NAME}<white>!");
    Notice invalidSlot = Notice.chat("<red>✘ <dark_red>Slot musi być z zakresu <red>0-{MAX}<dark_red>!");
    Notice slotChanged = Notice.chat("<color:#9d6eef>► <white>Kit <color:#9d6eef>{KIT_NAME} <white>jest teraz na slocie <color:#9d6eef>{SLOT}<white>!");
    Notice displayNameChanged = Notice.chat("<color:#9d6eef>► <white>Nazwa wyświetlana kitu <color:#9d6eef>{KIT_NAME} <white>to teraz <reset>{KIT}<white>!");
    Notice commandAdded = Notice.chat("<color:#9d6eef>► <white>Dodano komendę <color:#9d6eef>/{COMMAND} <white>do kitu <color:#9d6eef>{KIT_NAME}<white>!");
    Notice commandsCleared = Notice.chat("<color:#9d6eef>► <white>Wyczyszczono komendy kitu <color:#9d6eef>{KIT_NAME}<white>!");

    @Comment("# {COMMANDS} - lista komend sformatowana wg commands-list-entry, {INDEX} - numer komendy")
    Notice commandsList = Notice.chat(
        "<color:#9d6eef>► <white>Komendy wykonywane po odebraniu kitu <color:#9d6eef>{KIT_NAME}<white>:",
        "{COMMANDS}"
    );
    String commandsListEntry = "<dark_gray>{INDEX}. <gray>/{COMMAND}";
    Notice commandsEmpty = Notice.chat("<red>✘ <dark_red>Kit <red>{KIT_NAME} <dark_red>nie ma żadnych komend!");
    Notice commandRemoved = Notice.chat("<color:#9d6eef>► <white>Usunięto komendę <color:#9d6eef>/{COMMAND} <white>z kitu <color:#9d6eef>{KIT_NAME}<white>!");
    Notice invalidCommandNumber = Notice.chat("<red>✘ <dark_red>Numer komendy musi być z zakresu <red>1-{MAX}<dark_red>! Sprawdź: <red>/kitadmin commands list {KIT_NAME}");

    Notice descriptionLineAdded = Notice.chat("<color:#9d6eef>► <white>Dodano linię opisu <reset>{LINE} <white>do kitu <color:#9d6eef>{KIT_NAME}<white>!");
    Notice descriptionCleared = Notice.chat("<color:#9d6eef>► <white>Wyczyszczono opis kitu <color:#9d6eef>{KIT_NAME}<white>!");

    @Comment("# {LINES} - linie opisu sformatowane wg description-list-entry, {INDEX} - numer linii, {LINE} - linia")
    Notice descriptionList = Notice.chat(
        "<color:#9d6eef>► <white>Opis kitu <color:#9d6eef>{KIT_NAME}<white>:",
        "{LINES}"
    );
    String descriptionListEntry = "<dark_gray>{INDEX}. <reset>{LINE}";
    Notice descriptionEmpty = Notice.chat("<red>✘ <dark_red>Kit <red>{KIT_NAME} <dark_red>nie ma opisu!");
    Notice descriptionLineRemoved = Notice.chat("<color:#9d6eef>► <white>Usunięto linię opisu <reset>{LINE} <white>z kitu <color:#9d6eef>{KIT_NAME}<white>!");
    Notice invalidDescriptionLineNumber = Notice.chat("<red>✘ <dark_red>Numer linii musi być z zakresu <red>1-{MAX}<dark_red>! Sprawdź: <red>/kitadmin description list {KIT_NAME}");

    @Comment({ " ", "# Teksty GUI /kit" })
    public PLKitGuiMessages gui = new PLKitGuiMessages();

    @Getter
    @Accessors(fluent = true)
    public static class PLKitGuiMessages extends OkaeriConfig implements KitGuiMessages {

        @Comment("# {KIT} - nazwa wyświetlana kitu")
        String title = "<dark_gray>» <color:#9d6eef>Kity";
        String previewTitle = "<dark_gray>» <color:#9d6eef>Kit <white>{KIT}";
        String editorTitle = "<dark_gray>» <color:#9d6eef>Edycja kitu: <white>{KIT}";

        @Comment({ "# Linie dodawane pod opisem kitu", "# {TYPE} - typ, {KIT_COOLDOWN} - cooldown kitu, {CLAIMS} - liczba odebrań, {STATUS} - status" })
        List<String> kitLore = List.of(
            "",
            "<gray>Typ: <color:#9d6eef>{TYPE}",
            "<gray>Cooldown: <color:#9d6eef>{KIT_COOLDOWN}",
            "{STATUS}",
            ""
        );
        String claimHint = "<dark_gray>» <gray>LPM <white>aby odebrać, <gray>PPM <white>aby podejrzeć";
        String previewHint = "<dark_gray>» <gray>PPM <white>aby podejrzeć";

        String claimButtonName = "<color:#9d6eef>► <white>Odbierz kit";
        List<String> claimButtonLore = List.of("<dark_gray>» <gray>Kliknij, aby odebrać ten kit");
        String backButtonName = "<dark_gray>« <white>Powrót";
        List<String> backButtonLore = List.of("<dark_gray>» <gray>Kliknij, aby wrócić do listy kitów");

        String typeRecurring = "Cykliczny";
        String typeOneTime = "Jednorazowy";
        String typeFirstJoin = "Startowy";
        String none = "brak";
        String any = "dowolne";

        @Comment("# {COOLDOWN} - pozostały czas, {PLAYTIME} - brakujący czas gry")
        String statusAvailable = "<color:#9d6eef>► <white>Dostępny do odebrania!";
        String statusOnCooldown = "<red>✘ <dark_red>Dostępny za: <red>{COOLDOWN}";
        String statusAlreadyClaimed = "<red>✘ <dark_red>Już odebrany";
        String statusNoPermission = "<red>✘ <dark_red>Nie masz dostępu do tego kitu!";
        String statusWrongWorld = "<red>✘ <dark_red>Niedostępny w tym świecie";
        String statusWrongGameMode = "<red>✘ <dark_red>Niedostępny w tym trybie gry";
        String statusNotEnoughPlaytime = "<red>✘ <dark_red>Brakuje ci <red>{PLAYTIME} <dark_red>czasu gry";
        String statusInventoryFull = "<red>✘ <dark_red>Brak miejsca w ekwipunku";
        String statusDataNotLoaded = "<red>✘ <dark_red>Wczytywanie danych...";
    }
}
