package com.eternalcode.core.feature.kit.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLKitMessages extends OkaeriConfig implements KitMessages {

    @Comment({ " ", "# Sekcja odpowiedzialna za liste i sprawdzanie kitow" })
    Notice kitNoneAvailable = Notice.chat("<red>✘ <dark_red>Nie masz dostępu do żadnych kitów!");

    @Comment({ " ", "# Nagłówek dla listy dostępnych kitów" })
    Notice kitAvailableListHeader = Notice.chat("<color:#9d6eef>► <white>Dostępne kity:");

    @Comment({ " ", "# Wpis dla każdego dostępnego kita (Wspiera MiniMessage click/hover)", "# {KIT} - Nazwa kita" })
    Notice kitAvailableListEntry = Notice.chat("  <color:#8c8c8c>• <color:#9d6eef><click:run_command:'/kit {KIT}'><hover:show_text:'<white>Kliknij, aby odebrać <color:#9d6eef>{KIT}<white>!'>{KIT}</hover></click>");

    @Comment({ " ", "# Sekcja odpowiedzialna za odbieranie kitow" })
    @Comment({ " ", "# {KIT} - Nazwa kita", "# {PERMISSION} - Wymagana permisja" })
    Notice kitNoPermission = Notice.chat("<red>✘ <dark_red>Nie posiadasz uprawnień (<red>{PERMISSION}<dark_red>), aby użyć kita <red>{KIT}<dark_red>!");

    @Comment({ " ", "# {KIT} - Nazwa kita" })
    Notice kitCooldownActive = Notice.chat("<red>✘ <dark_red>Nie możesz jeszcze odebrać kita <red>{KIT}<dark_red>! Musisz odczekać cooldown.");

    @Comment({ " ", "# {KIT} - Nazwa kita" })
    Notice kitReceived = Notice.chat("<color:#9d6eef>► <white>Pomyślnie odebrałeś kita <color:#9d6eef>{KIT}<white>!");

    @Comment({ " ", "# {KIT} - Nazwa kita" })
    Notice kitReceiveFailed = Notice.chat("<red>✘ <dark_red>Wystąpił błąd podczas odbierania kita <red>{KIT}<dark_red>!");

    @Comment({ " ", "# Sekcja odpowiedzialna za nadawanie kitow innym graczom" })
    @Comment({ " ", "# {KIT} - Nazwa kita" })
    Notice kitGivenToTargetPlayer = Notice.chat("<color:#9d6eef>► <white>Pomyślnie nadałeś kita <color:#9d6eef>{KIT} <white>graczowi!");

    @Comment({ " ", "# {KIT} - Nazwa kita" })
    Notice kitGiveToTargetPlayerFailed = Notice.chat("<red>✘ <dark_red>Nie udało się nadać kita <red>{KIT} <dark_red>graczowi!");

    @Comment({ " ", "# Sekcja odpowiedzialna za zarzadzanie i tworzenie kitow" })
    @Comment({ " ", "# {NAME} - Nazwa nowego kita" })
    Notice kitEmptyCreated = Notice.chat("<color:#9d6eef>► <white>Pomyślnie utworzono pusty kit o nazwie <color:#9d6eef>{NAME}<white>!");

    Notice kitNameEmpty = Notice.chat("<red>✘ <dark_red>Nowa nazwa kita nie może być pusta!");

    Notice kitNameUnchanged = Notice.chat("<red>✘ <dark_red>Nowa nazwa kita nie może być taka sama jak obecna!");

    @Comment({ " ", "# {OLD_NAME} - Stara nazwa kita", "# {NEW_NAME} - Nowa nazwa kita" })
    Notice kitRenamed = Notice.chat("<color:#9d6eef>► <white>Kit <color:#9d6eef>{OLD_NAME} <white>został zmieniony na <color:#9d6eef>{NEW_NAME}<white>!");

    @Comment({ " ", "# {NAME} - Nazwa nowego kita" })
    Notice kitCreatedFromInventory = Notice.chat("<color:#9d6eef>► <white>Pomyślnie utworzono kit <color:#9d6eef>{NAME} <white>z twojego ekwipunku!");

    @Comment({ " ", "# {NAME} - Nazwa kita" })
    Notice kitCreateFailed = Notice.chat("<red>✘ <dark_red>Nie udało się utworzyć! Kit o nazwie <red>{NAME} <dark_red>już istnieje!");

    @Comment({ " ", "# {NAME} - Nazwa usunietego kita" })
    Notice kitDeleted = Notice.chat("<color:#9d6eef>► <white>Kit <color:#9d6eef>{NAME} <white>został pomyślnie usunięty!");

    @Comment({ " ", "# Sekcja odpowiedzialna za GUI do edycji kitow" })
    Notice kitEditGuiOpened = Notice.chat("<color:#9d6eef>► <white>Otwieranie edytora kita...");

    @Comment({ " ", "# {KIT} - Nazwa zapisywanego kita" })
    Notice kitSaved = Notice.chat("<color:#9d6eef>► <white>Pomyślnie zapisano ułożenie ekwipunku dla kita <color:#9d6eef>{KIT}<white>!");
}