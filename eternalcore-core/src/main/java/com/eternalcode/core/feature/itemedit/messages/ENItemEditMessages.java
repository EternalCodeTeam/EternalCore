package com.eternalcode.core.feature.itemedit.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

@Getter
@Accessors(fluent = true)
@Contextual
public class ENItemEditMessages implements ItemEditMessages {

    @Description("# {ITEM_NAME} - New item name")
    public Notice itemChangeNameMessage = Notice.chat("<green>► <white>Name changed to: <green>{ITEM_NAME}");

    @Description(" ")
    public Notice itemClearNameMessage = Notice.chat("<green>► <white>Item name cleared!");

    @Description({" ", "# {ITEM_LORE} - New item lore"})
    public Notice itemChangeLoreMessage = Notice.chat("<green>► <white>Lore changed to: <green>{ITEM_LORE}");

    @Description(" ")
    public Notice itemClearLoreMessage = Notice.chat("<green>► <white>Item lore cleared!");

    @Description({" ", "# {LINE} - Line number removed"})
    public Notice itemLoreLineRemoved = Notice.chat("<green>► <white>Removed lore line: <green>{LINE}");

    @Description({" ", "# {ITEM_FLAG} - Flag name"})
    public Notice itemFlagRemovedMessage = Notice.chat("<green>► <white>Removed item flag: <green>{ITEM_FLAG}");
    public Notice itemFlagAddedMessage = Notice.chat("<green>► <white>Added item flag: <green>{ITEM_FLAG}");

    @Description(" ")
    public Notice itemFlagClearedMessage = Notice.chat("<green>► <white>All item flags cleared!");

    @Description(" ")
    public Notice noLore = Notice.chat("<red>✖ <white>This item has no lore!");

    @Description(" ")
    public Notice invalidLoreLine = Notice.chat("<red>✖ <white>Invalid lore line number!");
}
