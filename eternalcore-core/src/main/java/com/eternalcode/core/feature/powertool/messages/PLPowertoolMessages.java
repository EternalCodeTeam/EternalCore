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
    public Notice commandAssigned = Notice.chat("<green>► <white>Przypisano komendę <green>/{COMMAND}<white> do narzędzia <green>{ITEM}.");
    public Notice commandRemoved = Notice.chat("<green>► <white>Usunięto komendę z narzędzia <green>{ITEM}.");
    public Notice commandNotAssigned = Notice.chat("<red>✘ <dark_red>Ten przedmiot nie jest power toolem. Użyj /pt <command>, aby przypisać do niego komendę.");

    @Comment(" ")
    public Notice noItemInMainHand = Notice.chat("<red>✘ <dark_red>Musisz trzymać przedmiot w głównej ręce, aby przypisać do niego komendę.");
    public Notice commandCannotBeEmpty = Notice.chat("<red>✘ <dark_red>Komenda nie może być pusta! Podaj poprawną komendę, aby przypisać ją do power toola.");
}
