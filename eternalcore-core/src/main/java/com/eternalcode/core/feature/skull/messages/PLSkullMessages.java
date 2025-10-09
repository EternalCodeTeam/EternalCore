package com.eternalcode.core.feature.skull.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLSkullMessages extends OkaeriConfig implements SkullMessages {

    @Comment({" ", "# {SKULL} - Nazwa gracza do którego należy głowa"})
    public Notice skullMessage = Notice.chat("<green>► <white>Otrzymałeś głowę gracza: {SKULL}");
}
