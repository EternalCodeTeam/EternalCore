package com.eternalcode.core.feature.kit.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLKitMessages extends OkaeriConfig implements KitMessages {

    @Comment("# {KIT} - nazwa kitu (displayName), {KIT_NAME} - nazwa kitu, {COOLDOWN} - pozostały czas")
    Notice notFound = Notice.chat("<red>✘ <dark_red>Kit <red>{KIT_NAME} <dark_red>nie istnieje!");
    Notice noKits = Notice.chat("<red>✘ <dark_red>Nie ma żadnych kitów!");
    Notice noPermission = Notice.chat("<red>✘ <dark_red>Nie masz dostępu do kitu <red>{KIT_NAME}<dark_red>!");
    Notice onCooldown = Notice.chat("<red>✘ <dark_red>Kit <red>{KIT} <dark_red>będzie dostępny za <red>{COOLDOWN}<dark_red>!");
    Notice claimed = Notice.chat("<color:#9d6eef>► <white>Odebrano kit <color:#9d6eef>{KIT}<white>!");
    Notice claimCancelled = Notice.chat("<red>✘ <dark_red>Nie udało się odebrać kitu <red>{KIT}<dark_red>!");

    @Comment({ " ", "# Administracja kitami" })
    Notice invalidName = Notice.chat("<red>✘ <dark_red>Niepoprawna nazwa kitu <red>{KIT}<dark_red>! Dozwolone: a-z, 0-9, _ i - (max 32 znaki)");
    Notice alreadyExists = Notice.chat("<red>✘ <dark_red>Kit <red>{KIT_NAME} <dark_red>już istnieje!");
    Notice created = Notice.chat(
        "<color:#9d6eef>► <white>Stworzono kit <color:#9d6eef>{KIT} <white>(uprawnienie: <color:#9d6eef>{PERMISSION}<white>)!",
        "<hover:show_text:'<color:#9d6eef>Ustawić przedmioty kitu?</color:#9d6eef>'><click:suggest_command:'/kitadmin items {KIT}'><dark_gray>» <gold>/kitadmin items {KIT} <color:#9d6eef>by ustawić przedmioty! <gray>(Kliknij)</gray></click></hover>"
    );
    Notice deleted = Notice.chat("<color:#9d6eef>► <white>Usunięto kit <color:#9d6eef>{KIT}<white>!");
    Notice itemsEditorOpened = Notice.chat("<color:#9d6eef>► <white>Włóż przedmioty i zamknij ekwipunek, aby zapisać kit <color:#9d6eef>{KIT}<white>.");
    Notice itemsSaved = Notice.chat("<color:#9d6eef>► <white>Zapisano <color:#9d6eef>{AMOUNT} <white>przedmiotów w kicie <color:#9d6eef>{KIT}<white>!");
    Notice permissionChanged = Notice.chat("<color:#9d6eef>► <white>Uprawnienie kitu <color:#9d6eef>{KIT} <white>to teraz <color:#9d6eef>{PERMISSION}<white>!");
    Notice cooldownChanged = Notice.chat("<color:#9d6eef>► <white>Cooldown kitu <color:#9d6eef>{KIT} <white>to teraz <color:#9d6eef>{COOLDOWN}<white>!");
    Notice emptyHand = Notice.chat("<red>✘ <dark_red>Musisz trzymać przedmiot w ręce!");
    Notice iconChanged = Notice.chat("<color:#9d6eef>► <white>Zmieniono ikonę kitu <color:#9d6eef>{KIT}<white>!");
    Notice invalidSlot = Notice.chat("<red>✘ <dark_red>Slot musi być z zakresu <red>0-{MAX}<dark_red>!");
    Notice slotChanged = Notice.chat("<color:#9d6eef>► <white>Kit <color:#9d6eef>{KIT} <white>jest teraz na slocie <color:#9d6eef>{SLOT}<white>!");
    Notice displayNameChanged = Notice.chat("<color:#9d6eef>► <white>Nazwa wyświetlana kitu <color:#9d6eef>{KIT} <white>to teraz <reset>{NAME}<white>!");
    Notice commandAdded = Notice.chat("<color:#9d6eef>► <white>Dodano komendę <color:#9d6eef>/{COMMAND} <white>do kitu <color:#9d6eef>{KIT}<white>!");
    Notice commandsCleared = Notice.chat("<color:#9d6eef>► <white>Wyczyszczono komendy kitu <color:#9d6eef>{KIT}<white>!");
    Notice cooldownReset = Notice.chat("<color:#9d6eef>► <white>Zresetowano cooldown kitu <color:#9d6eef>{KIT} <white>dla <color:#9d6eef>{PLAYER}<white>!");
    Notice databaseError = Notice.chat("<red>✘ <dark_red>Wystąpił błąd bazy danych, sprawdź konsolę!");
}
