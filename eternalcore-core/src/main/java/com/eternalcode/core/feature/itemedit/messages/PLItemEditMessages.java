package com.eternalcode.core.feature.itemedit.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLItemEditMessages extends OkaeriConfig implements ItemEditMessages {

    @Comment("# {ITEM_NAME} - Nowa nazwa przedmiotu")
    Notice itemChangeNameMessage = Notice.chat("<green>► <white>Nowa nazwa przedmiotu: <green>{ITEM_NAME}");

    @Comment(" ")
    Notice itemClearNameMessage = Notice.chat("<green>► <white>Wyczyszczono nazwę przedmiotu!");

    @Comment({" ", "# {ITEM_LORE} - Nowa linia opisu"})
    Notice itemChangeLoreMessage = Notice.chat("<green>► <white>Zmieniono linię opisu na: <green>{ITEM_LORE}");

    @Comment(" ")
    Notice itemClearLoreMessage = Notice.chat("<green>► <white>Wyczyszczono wszystkie linie opisu!");

    @Comment({" ", "# {LINE} - Numer linii usuniętej"})
    Notice itemLoreLineRemoved = Notice.chat("<green>► <white>Usunięto linię opisu (numer): <green>{LINE}");

    @Comment({" ", "# {ITEM_FLAG} - Nazwa flagi"})
    Notice itemFlagRemovedMessage = Notice.chat("<green>► <white>Usunięto flagę przedmiotu: <green>{ITEM_FLAG}");
    Notice itemFlagAddedMessage = Notice.chat("<green>► <white>Dodano flagę przedmiotu: <green>{ITEM_FLAG}");

    @Comment(" ")
    Notice itemFlagClearedMessage = Notice.chat("<green>► <white>Wyczyszczono wszystkie flagi przedmiotu!");

    @Comment(" ")
    Notice noLore = Notice.chat("<red>✖ <white>Ten przedmiot nie ma opisu!");

    @Comment(" ")
    Notice invalidLoreLine = Notice.chat("<red>✖ <white>Nieprawidłowy numer linii opisu!");
}
