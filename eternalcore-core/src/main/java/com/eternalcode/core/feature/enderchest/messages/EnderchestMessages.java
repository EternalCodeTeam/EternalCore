package com.eternalcode.core.feature.enderchest.messages;

import com.eternalcode.multification.notice.Notice;

public interface EnderchestMessages {

    Notice openedEnderchest();

    Notice customEnderchestDisabled();

    Notice enderchestsBlocked();

    Notice openedEnderchestPage();

    Notice enderchestPageUnavailable();

    Notice enderchestPageSwitchDelay();

    Notice openedTargetPlayerEnderchest();

    Notice playerEnderchestEmpty();

    Notice enderchestInUse();
}
