package com.eternalcode.core.litecommand.argument.messages;

import com.eternalcode.multification.notice.Notice;

public interface ArgumentMessages {
    Notice missingPlayerName();
    Notice permissionMessage();
    Notice usageMessage();
    Notice usageMessageHead();
    Notice usageMessageEntry();
    Notice offlinePlayer();
    Notice onlyPlayer();
    Notice numberBiggerThanZero();
    Notice numberBiggerThanOrEqualZero();
    Notice stackNumberIncorrect();
    Notice noItem();
    Notice noMaterial();
    Notice noArgument();
    Notice worldDoesntExist();
    Notice incorrectLocation();

    Notice missingPlayer();
}
