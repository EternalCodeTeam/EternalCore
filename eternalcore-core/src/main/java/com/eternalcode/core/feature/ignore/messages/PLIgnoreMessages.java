package com.eternalcode.core.feature.ignore.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLIgnoreMessages extends OkaeriConfig implements IgnoreMessages {
    
    @Comment("# {PLAYER} - Nazwa gracza")
    public Notice ignorePlayer = Notice.chat("<green>► <white>Zignorowano gracza <red>{PLAYER}<white>!");

    public Notice ignoreAll = Notice.chat("<red>► <dark_red>Zignorowano wszystkich graczy!");
    public Notice cantIgnoreYourself = Notice.chat("<red>► <dark_red>Nie możesz zignorować samego siebie!");

    @Comment("# {PLAYER} - Nazwa gracza")
    public Notice alreadyIgnorePlayer = Notice.chat("<red>► <dark_red>Gracz <red>{PLAYER} jest już zignorowany!");

    @Comment("# {PLAYER} - Nazwa gracza")
    public Notice unIgnorePlayer = Notice.chat("<red>► <dark_red>Od ignorowano gracza <red>{PLAYER}<dark_red>!");

    public Notice unIgnoreAll = Notice.chat("<red>► <dark_red>Od ignorowano wszystkich graczy!");
    public Notice cantUnIgnoreYourself = Notice.chat("<red>► <dark_red>Nie możesz od ignorować samego siebie!");

    @Comment("# {PLAYER} - Nazwa gracza")
    public Notice notIgnorePlayer = Notice.chat(
        "<red>► <dark_red>Gracz <red>{PLAYER} <dark_red>nie jest przez Ciebie zignorowany. Nie możesz go od ignorować!");
}
