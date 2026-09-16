package com.eternalcode.core.feature.mobignore.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLMobIgnoreMessages extends OkaeriConfig implements MobIgnoreMessages {
    Notice mobIgnore = Notice.chat("<color:#9d6eef>► <white>Potwory będą Cię ignorować!");
    Notice noMobIgnore = Notice.chat("<color:#9d6eef>► <white>Potwory nie będą Cię więcej ignorować!");

    @Comment({ " ", "# Placeholder: {PLAYER} - Wybrany komendą gracz" })
    Notice mobIgnoreTarget = Notice.chat("<color:#9d6eef>► <white>Potwory będą ignorować gracza: <green>{PLAYER}<white> - ochrona została włączona!");
    Notice noMobIgnoreTarget = Notice.chat("<color:#9d6eef>► <white>Potwory nie będą ignorować gracza: <green>{PLAYER}<white> - ochrona została wyłączona!");

}
