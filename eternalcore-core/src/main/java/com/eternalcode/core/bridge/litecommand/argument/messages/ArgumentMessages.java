package com.eternalcode.core.bridge.litecommand.argument.messages;

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
    Notice noItem();
    Notice noArgument();
    Notice noDamaged();
    Notice noDamagedItems();
    Notice noEnchantment();
    Notice noValidEnchantmentLevel();
    Notice invalidTimeFormat();
    Notice worldDoesntExist();
    Notice incorrectNumberOfChunks();
    Notice incorrectLocation();
    Notice noValidEntityScope();
}
