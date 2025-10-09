package com.eternalcode.core.feature.give.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLGiveMessages extends OkaeriConfig implements GiveMessages {

    @Comment({" ", "# {ITEM} - Nazwa otrzymanego itemu"})
    public Notice giveReceived = Notice.chat("<green>► <white>Otrzymałeś: <green>{ITEM}");

    @Comment({" ", "# {PLAYER} - Osoba której został przydzielony przedmiot, {ITEM} - Nazwa otrzymanego przedmiotu"})
    public Notice giveGiven = Notice.chat("<green>► <white>Gracz <green>{PLAYER} <white>otrzymał: <green>{ITEM}");

    public Notice giveNoSpace = Notice.chat("<red>✘ <dark_red>Brak miejsca w ekwipunku!");

    @Comment(" ")
    public Notice giveNotItem = Notice.chat("<red>✘ <dark_red>Podany przedmiot nie istnieje!");
}
