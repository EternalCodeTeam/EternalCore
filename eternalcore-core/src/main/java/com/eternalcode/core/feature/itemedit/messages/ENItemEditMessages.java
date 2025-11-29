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
    Notice itemChangeNameMessage = Notice.chat("<color:#9d6eef>► <white>Name changed to: <color:#9d6eef>{ITEM_NAME}");

    @Comment(" ")
    Notice itemClearNameMessage = Notice.chat("<color:#9d6eef>► <white>Item name cleared!");

    @Comment({ " ", "# {ITEM_LORE} - New item lore" })
    Notice itemChangeLoreMessage = Notice.chat("<color:#9d6eef>► <white>Lore changed to: <color:#9d6eef>{ITEM_LORE}");

    @Comment(" ")
    Notice itemClearLoreMessage = Notice.chat("<color:#9d6eef>► <white>Item lore cleared!");

    @Comment({ " ", "# {LINE} - Line number removed" })
    Notice itemLoreLineRemoved = Notice.chat("<color:#9d6eef>► <white>Removed lore line: <color:#9d6eef>{LINE}");

    @Comment({ " ", "# {ITEM_FLAG} - Flag name" })
    Notice itemFlagRemovedMessage = Notice
            .chat("<color:#9d6eef>► <white>Removed item flag: <color:#9d6eef>{ITEM_FLAG}");
    Notice itemFlagAddedMessage = Notice.chat("<color:#9d6eef>► <white>Added item flag: <color:#9d6eef>{ITEM_FLAG}");

    @Comment(" ")
    Notice itemFlagClearedMessage = Notice.chat("<color:#9d6eef>► <white>All item flags cleared!");

    @Comment(" ")
    Notice noLore = Notice.chat("<red>✘ <dark_red>This item has no lore!");

    @Comment(" ")
    Notice invalidLoreLine = Notice.chat("<red>✘ <dark_red>Invalid lore line number!");
}
