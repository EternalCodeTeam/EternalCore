package com.eternalcode.core.feature.kit.messages;

import com.eternalcode.multification.notice.Notice;

public interface KitMessages {

    Notice notFound();
    Notice noKits();
    Notice noPermission();
    Notice onCooldown();
    Notice claimed();
    Notice claimCancelled();

    Notice invalidName();
    Notice alreadyExists();
    Notice created();
    Notice deleted();
    Notice itemsEditorOpened();
    Notice itemsSaved();
    Notice permissionChanged();
    Notice cooldownChanged();
    Notice emptyHand();
    Notice iconChanged();
    Notice invalidSlot();
    Notice slotChanged();
    Notice displayNameChanged();
    Notice commandAdded();
    Notice commandsCleared();
    Notice cooldownReset();
    Notice databaseError();

}
