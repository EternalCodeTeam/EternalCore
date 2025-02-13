package com.eternalcode.core.feature.itemedit.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
@Contextual
public class PLItemEditMessages implements ItemEditMessages {

    @Description("# {ITEM_NAME} - Nowa nazwa przedmiotu")
    public Notice itemChangeNameMessage = Notice.chat("<green>► <white>Nowa nazwa przedmiotu: <green>{ITEM_NAME}");

    @Description(" ")
    public Notice itemClearNameMessage = Notice.chat("<green>► <white>Wyczyszczono nazwę przedmiotu!");

    @Description({" ", "# {ITEM_LORE} - Nowa linia opisu"})
    public Notice itemChangeLoreMessage = Notice.chat("<green>► <white>Zmieniono linię opisu na: <green>{ITEM_LORE}");

    @Description(" ")
    public Notice itemClearLoreMessage = Notice.chat("<green>► <white>Wyczyszczono wszystkie linie opisu!");

    @Description({" ", "# {LINE} - Numer linii usuniętej"})
    public Notice itemLoreLineRemoved = Notice.chat("<green>► <white>Usunięto linię opisu (numer): <green>{LINE}");

    @Description({" ", "# {ITEM_FLAG} - Nazwa flagi"})
    public Notice itemFlagRemovedMessage = Notice.chat("<green>► <white>Usunięto flagę przedmiotu: <green>{ITEM_FLAG}");
    public Notice itemFlagAddedMessage = Notice.chat("<green>► <white>Dodano flagę przedmiotu: <green>{ITEM_FLAG}");

    @Description(" ")
    public Notice itemFlagClearedMessage = Notice.chat("<green>► <white>Wyczyszczono wszystkie flagi przedmiotu!");

    @Description(" ")
    public Notice noLore = Notice.chat("<red>✖ <white>Ten przedmiot nie ma opisu!");

    @Description(" ")
    public Notice invalidLoreLine = Notice.chat("<red>✖ <white>Nieprawidłowy numer linii opisu!");
}
