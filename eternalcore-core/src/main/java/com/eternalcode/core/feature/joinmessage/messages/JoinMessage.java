package com.eternalcode.core.feature.joinmessage.messages;

import com.eternalcode.multification.notice.Notice;
import java.util.List;

public interface JoinMessage {

    List<Notice> joinMessages();

    List<Notice> firstJoinMessages();
}
