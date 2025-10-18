package com.eternalcode.core.litecommand.argument.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLArgumentMessages extends OkaeriConfig implements ArgumentMessages {
    @Comment("# {PERMISSIONS} - Wyświetla wymagane uprawnienia")
    Notice permissionMessage =
        Notice.chat("<red>✘ <dark_red>Nie masz uprawnień do tej komendy! <red>({PERMISSIONS})");

    @Comment({" ", "# {USAGE} - Wyświetla poprawne użycie komendy"})
    Notice usageMessage = Notice.chat("<gold>✘ <white>Poprawne użycie: <gold>{USAGE}");
    Notice usageMessageHead = Notice.chat("<gold>✘ <white>Poprawne użycie:");
    Notice usageMessageEntry = Notice.chat("<gold>✘ <white>{USAGE}");

    @Comment(" ")
    Notice missingPlayerName = Notice.chat("<red>✘ <dark_red>Musisz podać nazwę gracza!");
    Notice offlinePlayer = Notice.chat("<red>✘ <dark_red>Ten gracz jest obecnie offline!");
    Notice onlyPlayer = Notice.chat("<red>✘ <dark_red>Ta komenda jest dostępna tylko dla graczy!");
    Notice numberBiggerThanZero = Notice.chat("<red>✘ <dark_red>Liczba musi być większa od 0!");
    Notice numberBiggerThanOrEqualZero = Notice.chat("<red>✘ <dark_red>Liczba musi być równa lub większa od 0!");
    Notice noItem = Notice.chat("<red>✘ <dark_red>Musisz trzymać przedmiot w dłoni!");
    Notice noMaterial = Notice.chat("<red>✘ <dark_red>Taki materiał nie istnieje!");
    Notice noArgument = Notice.chat("<red>✘ <dark_red>Taki argument nie istnieje!");
    Notice noDamaged = Notice.chat("<red>✘ <dark_red>Ten przedmiot nie może być naprawiony!");
    Notice noDamagedItems = Notice.chat("<red>✘ <dark_red>Musisz posiadać uszkodzone przedmioty!");
    Notice noEnchantment = Notice.chat("<red>✘ <dark_red>Takie zaklęcie nie istnieje!");
    Notice noValidEnchantmentLevel = Notice.chat("<red>✘ <dark_red>Ten poziom zaklęcia nie jest wspierany!");
    Notice worldDoesntExist = Notice.chat("<red>✘ <dark_red>Świat <dark_red>{WORLD} <red>nie istnieje!");
    Notice incorrectNumberOfChunks = Notice.chat("<red>✘ <dark_red>Niepoprawna liczba chunków!");
    Notice incorrectLocation = Notice.chat("<red>✘ <dark_red>Niepoprawna lokalizacja! <red>({LOCATION})");

}
