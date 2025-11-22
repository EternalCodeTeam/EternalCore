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
    Notice itemChangeNameMessage = Notice.chat("<color:#9d6eef>► <white>Nowa nazwa przedmiotu: <color:#9d6eef>{ITEM_NAME}");

    @Comment(" ")
    Notice itemClearNameMessage = Notice.chat("<color:#9d6eef>► <white>Wyczyszczono nazwę przedmiotu!");

    @Comment({" ", "# {ITEM_LORE} - Nowa linia opisu"})
    Notice itemChangeLoreMessage = Notice.chat("<color:#9d6eef>► <white>Zmieniono linię opisu na: <color:#9d6eef>{ITEM_LORE}");

    @Comment(" ")
    Notice itemClearLoreMessage = Notice.chat("<color:#9d6eef>► <white>Wyczyszczono wszystkie linie opisu!");

    @Comment({" ", "# {LINE} - Numer linii usuniętej"})
    Notice itemLoreLineRemoved = Notice.chat("<color:#9d6eef>► <white>Usunięto linię opisu (numer): <color:#9d6eef>{LINE}");

    @Comment({" ", "# {ITEM_FLAG} - Nazwa flagi"})
    Notice itemFlagRemovedMessage = Notice.chat("<color:#9d6eef>► <white>Usunięto flagę przedmiotu: <color:#9d6eef>{ITEM_FLAG}");
    Notice itemFlagAddedMessage = Notice.chat("<color:#9d6eef>► <white>Dodano flagę przedmiotu: <color:#9d6eef>{ITEM_FLAG}");

    @Comment(" ")
    Notice itemFlagClearedMessage = Notice.chat("<color:#9d6eef>► <white>Wyczyszczono wszystkie flagi przedmiotu!");

    @Comment(" ")
    Notice noLore = Notice.chat("<red>✖ <white>Ten przedmiot nie ma opisu!");

    @Comment(" ")
    Notice invalidLoreLine = Notice.chat("<red>✖ <white>Nieprawidłowy numer linii opisu!");
}
