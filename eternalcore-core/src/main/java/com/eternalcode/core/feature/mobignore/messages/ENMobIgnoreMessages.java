package com.eternalcode.core.feature.mobignore.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENMobIgnoreMessages extends OkaeriConfig implements MobIgnoreMessages {
    Notice mobIgnore = Notice.chat("<color:#9d6eef>► <white>Monsters will no longer target you!");
    Notice noMobIgnore = Notice.chat("<color:#9d6eef>► <white>Monsters will target you!");

    @Comment({ " ", "# Placeholder: {PLAYER} - Targeted player name" })
    Notice mobIgnoreTarget = Notice.chat("<color:#9d6eef>► <white>Monsters will ignore <green>{PLAYER}<white> - protection has been turned on!");
    Notice noMobIgnoreTarget = Notice.chat("<color:#9d6eef>► <white>Monsters will target <green>{PLAYER}<white> - protection has been turned off!");

}
