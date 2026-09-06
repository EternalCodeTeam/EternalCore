package com.eternalcode.core.feature.helpop.messages;

import com.eternalcode.multification.notice.Notice;

public interface HelpOpSection {
    Notice format();
    Notice send();
    Notice helpOpDelay();
    Notice adminReply();
    Notice adminReplySend();
    Notice playerNotSentHelpOp();
}
