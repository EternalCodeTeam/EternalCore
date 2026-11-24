package com.eternalcode.core.feature.kit.messages;

import com.eternalcode.multification.notice.Notice;

public interface KitMessages {

    Notice notFound();
    Notice noKits();
    Notice claimed();
    Notice claimedToBuffer();
    Notice noPermission();
    Notice onCooldown();
    Notice alreadyClaimed();
    Notice wrongWorld();
    Notice wrongGameMode();
    Notice notEnoughPlaytime();
    Notice inventoryFull();
    Notice claimCancelled();
    Notice dataNotLoaded();
    Notice info();

    Notice bufferEmpty();
    Notice bufferClaimed();
    Notice bufferWaiting();

    Notice given();
    Notice received();
    Notice usageReset();

    Notice invalidName();
    Notice reservedName();
    Notice alreadyExists();
    Notice created();
    Notice deleted();
    Notice itemsEditorOpened();
    Notice itemsSaved();
    Notice permissionChanged();
    Notice cooldownChanged();
    Notice typeChanged();
    Notice emptyHand();
    Notice iconChanged();
    Notice invalidSlot();
    Notice slotChanged();
    Notice displayNameChanged();
    Notice commandAdded();
    Notice commandsCleared();
    Notice commandsList();
    String commandsListEntry();
    Notice commandsEmpty();
    Notice commandRemoved();
    Notice invalidCommandNumber();
    Notice descriptionLineAdded();
    Notice descriptionCleared();
    Notice descriptionList();
    String descriptionListEntry();
    Notice descriptionEmpty();
    Notice descriptionLineRemoved();
    Notice invalidDescriptionLineNumber();

    KitGuiMessages gui();

}
