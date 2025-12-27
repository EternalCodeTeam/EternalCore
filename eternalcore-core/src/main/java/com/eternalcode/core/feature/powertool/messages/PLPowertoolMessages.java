package com.eternalcode.core.feature.powertool.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLPowertoolMessages extends OkaeriConfig implements PowertoolMessages {

    @Comment({" ", "# Dostępne placeholdery:",
              "# {COMMAND} - Komenda przypisana do narzędzia",
              "# {ITEM} - Przedmiot będący power tool"}
    )
    Notice assigned = Notice.chat("<color:#9d6eef>► <white>Przypisano komendę <color:#9d6eef>/{COMMAND}<white> do narzędzia "
        + "<color:#9d6eef>{ITEM}.");
    Notice removed = Notice.chat("<color:#9d6eef>► <white>Usunięto komendę z narzędzia <color:#9d6eef>{ITEM}.");
    Notice notAssigned = Notice.chat("<red>✘ <dark_red>Ten przedmiot nie jest power toolem. Użyj /powertool "
        + "<command>, aby przypisać do niego komendę.");
    Notice noItemInMainHand =
        Notice.chat("<red>✘ <dark_red>Musisz trzymać przedmiot w głównej ręce, aby przypisać do niego komendę.");
    Notice emptyCommand = Notice.chat("<red>✘ <dark_red>Komenda nie może być pusta! Podaj poprawną komendę, "
        + "aby przypisać ją do power toola.");
    Notice invalidCommand = Notice.chat("<red>✘ <dark_red>Podana komenda jest nieprawidłowa! Upewnij się, że "
        + "komenda istnieje.");
    Notice executionFailed =
        Notice.chat("<red>✘ <dark_red>Wystąpił błąd podczas wykonywania komendy {COMMAND} przypisanej do power toola.");
}
