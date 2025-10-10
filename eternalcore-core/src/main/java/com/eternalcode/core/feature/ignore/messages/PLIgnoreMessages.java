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
    public Notice playerIgnored = Notice.chat("<green>► <white>Zignorowano gracza <red>{PLAYER}<white>!");

    public Notice allPlayersIgnored = Notice.chat("<red>► <dark_red>Zignorowano wszystkich graczy!");

    public Notice cannotIgnoreSelf = Notice.chat("<red>► <dark_red>Nie możesz zignorować samego siebie!");

    @Comment("# {PLAYER} - Nazwa gracza")
    public Notice playerAlreadyIgnored = Notice.chat("<red>► <dark_red>Gracz <red>{PLAYER} jest już zignorowany!");

    @Comment("# {PLAYER} - Nazwa gracza")
    public Notice playerUnignored = Notice.chat("<red>► <dark_red>Od ignorowano gracza <red>{PLAYER}<dark_red>!");

    public Notice allPlayersUnignored = Notice.chat("<red>► <dark_red>Od ignorowano wszystkich graczy!");

    public Notice cannotUnignoreSelf = Notice.chat("<red>► <dark_red>Nie możesz od ignorować samego siebie!");

    @Comment("# {PLAYER} - Nazwa gracza")
    public Notice playerNotIgnored = Notice.chat(
        "<red>► <dark_red>Gracz <red>{PLAYER} <dark_red>nie jest przez Ciebie zignorowany. Nie możesz go od ignorować!");
}
