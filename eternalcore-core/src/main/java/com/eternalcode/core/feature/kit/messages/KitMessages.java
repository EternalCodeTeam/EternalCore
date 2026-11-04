package com.eternalcode.core.feature.kit.messages;

import com.eternalcode.multification.notice.Notice;

public interface KitMessages {
    Notice kitNoneAvailable();

    Notice kitAvailableListHeader();

    Notice kitAvailableListEntry();

    Notice kitNoPermission();

    Notice kitCooldownActive();

    Notice kitReceived();

    Notice kitReceiveFailed();

    Notice kitGivenToTargetPlayer();

    Notice kitGiveToTargetPlayerFailed();

    Notice kitEmptyCreated();

    Notice kitNameEmpty();

    Notice kitNameUnchanged();

    Notice kitRenamed();

    Notice kitCreatedFromInventory();

    Notice kitCreateFailed();

    Notice kitDeleted();

    Notice kitEditGuiOpened();

    Notice kitSaved();
}
