package com.eternalcode.core.feature.joinquitmessage.messages;

import com.eternalcode.multification.notice.Notice;

import java.util.List;

public interface JoinQuitMessages {

    List<Notice> joinMessage();
    List<Notice> quitMessage();
    List<Notice> firstJoinMessage();

}
