package com.eternalcode.core.feature.onlineplayers.messages;

import com.eternalcode.multification.notice.Notice;
import java.util.List;

public interface OnlineMessages {
    Notice count();
    Notice list();
    List<String> full();
}
