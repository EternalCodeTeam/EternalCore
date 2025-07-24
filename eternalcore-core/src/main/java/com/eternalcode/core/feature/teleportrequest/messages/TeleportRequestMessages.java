package com.eternalcode.core.feature.teleportrequest.messages;

import com.eternalcode.multification.notice.Notice;

public interface TeleportRequestMessages {
    Notice tpaSelfMessage();
    Notice tpaAlreadySentMessage();
    Notice tpaSentMessage();
    Notice tpaReceivedMessage();
    Notice tpaTargetIgnoresYou();

    Notice tpaHereSent();
    Notice tpaHereReceived();

    Notice tpaDenyNoRequestMessage();
    Notice tpaDenyDoneMessage();
    Notice tpaDenyReceivedMessage();
    Notice tpaDenyAllDenied();

    Notice tpaAcceptMessage();
    Notice tpaAcceptNoRequestMessage();
    Notice tpaAcceptReceivedMessage();
    Notice tpaAcceptAllAccepted();
}
