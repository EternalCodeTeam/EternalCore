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
    public Notice permissionMessage =
        Notice.chat("<red>✘ <dark_red>Nie masz uprawnień do tej komendy! <red>({PERMISSIONS})");

    @Comment({" ", "# {USAGE} - Wyświetla poprawne użycie komendy"})
    public Notice usageMessage = Notice.chat("<gold>✘ <white>Poprawne użycie: <gold>{USAGE}");
    public Notice usageMessageHead = Notice.chat("<gold>✘ <white>Poprawne użycie:");
    public Notice usageMessageEntry = Notice.chat("<gold>✘ <white>{USAGE}");

    @Comment(" ")
    public Notice missingPlayerName = Notice.chat("<red>✘ <dark_red>Musisz podać nazwę gracza!");
    public Notice offlinePlayer = Notice.chat("<red>✘ <dark_red>Ten gracz jest obecnie offline!");
    public Notice onlyPlayer = Notice.chat("<red>✘ <dark_red>Ta komenda jest dostępna tylko dla graczy!");
    public Notice numberBiggerThanZero = Notice.chat("<red>✘ <dark_red>Liczba musi być większa od 0!");
    public Notice numberBiggerThanOrEqualZero = Notice.chat("<red>✘ <dark_red>Liczba musi być równa lub większa od 0!");
    public Notice noItem = Notice.chat("<red>✘ <dark_red>Musisz trzymać przedmiot w dłoni!");
    public Notice noMaterial = Notice.chat("<red>✘ <dark_red>Taki materiał nie istnieje!");
    public Notice noArgument = Notice.chat("<red>✘ <dark_red>Taki argument nie istnieje!");
    public Notice noDamaged = Notice.chat("<red>✘ <dark_red>Ten przedmiot nie może być naprawiony!");
    public Notice noDamagedItems = Notice.chat("<red>✘ <dark_red>Musisz posiadać uszkodzone przedmioty!");
    public Notice noEnchantment = Notice.chat("<red>✘ <dark_red>Takie zaklęcie nie istnieje!");
    public Notice noValidEnchantmentLevel = Notice.chat("<red>✘ <dark_red>Ten poziom zaklęcia nie jest wspierany!");
    public Notice invalidTimeFormat = Notice.chat("<red>✘ <dark_red>Nieprawidłowy format czasu!");
    public Notice worldDoesntExist = Notice.chat("<red>✘ <dark_red>Świat <dark_red>{WORLD} <red>nie istnieje!");
    public Notice incorrectNumberOfChunks = Notice.chat("<red>✘ <dark_red>Niepoprawna liczba chunków!");
    public Notice incorrectLocation = Notice.chat("<red>✘ <dark_red>Niepoprawna lokalizacja! <red>({LOCATION})");
    //@TODO: Check if google translate is correct
    public Notice noValidEntityScope = Notice.chat("<red>✘ <dark_red>Nie podano prawidłowego zakresu entity! Użyj sugerowanej opcji.");

}
