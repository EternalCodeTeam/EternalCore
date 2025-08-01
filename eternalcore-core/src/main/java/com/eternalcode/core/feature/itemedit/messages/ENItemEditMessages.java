package com.eternalcode.core.feature.itemedit.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENItemEditMessages extends OkaeriConfig implements ItemEditMessages {

    @Comment("# {ITEM_NAME} - New item name")
    public Notice itemChangeNameMessage = Notice.chat("<green>► <white>Name changed to: <green>{ITEM_NAME}");

    @Comment(" ")
    public Notice itemClearNameMessage = Notice.chat("<green>► <white>Item name cleared!");

    @Comment({" ", "# {ITEM_LORE} - New item lore"})
    public Notice itemChangeLoreMessage = Notice.chat("<green>► <white>Lore changed to: <green>{ITEM_LORE}");

    @Comment(" ")
    public Notice itemClearLoreMessage = Notice.chat("<green>► <white>Item lore cleared!");

    @Comment({" ", "# {LINE} - Line number removed"})
    public Notice itemLoreLineRemoved = Notice.chat("<green>► <white>Removed lore line: <green>{LINE}");

    @Comment({" ", "# {ITEM_FLAG} - Flag name"})
    public Notice itemFlagRemovedMessage = Notice.chat("<green>► <white>Removed item flag: <green>{ITEM_FLAG}");
    public Notice itemFlagAddedMessage = Notice.chat("<green>► <white>Added item flag: <green>{ITEM_FLAG}");

    @Comment(" ")
    public Notice itemFlagClearedMessage = Notice.chat("<green>► <white>All item flags cleared!");

    @Comment(" ")
    public Notice noLore = Notice.chat("<red>✖ <white>This item has no lore!");

    @Comment(" ")
    public Notice invalidLoreLine = Notice.chat("<red>✖ <white>Invalid lore line number!");
}
