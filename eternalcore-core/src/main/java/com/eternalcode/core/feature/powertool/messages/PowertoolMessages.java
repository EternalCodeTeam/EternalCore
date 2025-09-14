package com.eternalcode.core.feature.powertool.messages;

import com.eternalcode.multification.notice.Notice;

public interface PowertoolMessages {

    Notice assigned();
    Notice removed();
    Notice notAssigned();
    Notice emptyCommand();
    Notice noItemInMainHand();
    Notice invalidCommand();
    Notice executionFailed();
}
