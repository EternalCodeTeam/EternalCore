package com.eternalcode.core.feature.powertool.messages;

import com.eternalcode.multification.notice.Notice;

public interface PowertoolMessages {

    Notice commandAssigned();
    Notice commandRemoved();
    Notice commandNotAssigned();
    Notice commandCannotBeEmpty();
    Notice noItemInMainHand();

}
