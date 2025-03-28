package com.eternalcode.core.feature.itemedit.messages;

import com.eternalcode.multification.notice.Notice;

public interface ItemEditMessages {
    // Item name
    Notice itemChangeNameMessage();
    Notice itemClearNameMessage();

    // Item lore
    Notice itemChangeLoreMessage();
    Notice itemClearLoreMessage();
    Notice itemLoreLineRemoved();

    // Item flags
    Notice itemFlagRemovedMessage();
    Notice itemFlagAddedMessage();
    Notice itemFlagClearedMessage();

    // Validation
    Notice noLore();
    Notice invalidLoreLine();
}
