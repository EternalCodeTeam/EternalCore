package com.eternalcode.core.feature.automessage.messages;

import com.eternalcode.multification.notice.Notice;
import java.util.Collection;

public interface AutoMessageMessages {
    Collection<Notice> messages();

    Notice enabled();
    Notice disabled();
}
