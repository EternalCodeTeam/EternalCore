package com.eternalcode.core.feature.give.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLGiveMessages extends OkaeriConfig implements GiveMessages {
    Notice itemGivenByAdmin = Notice.chat("<green>► <white>Otrzymałeś: <green>{ITEM}");
    Notice itemGivenToTargetPlayer = Notice.chat("<green>► <white>Gracz <green>{PLAYER} <white>otrzymał: <green>{ITEM}");
    Notice noSpace = Notice.chat("<red>✘ <dark_red>Brak miejsca w ekwipunku!");
    Notice itemNotFound = Notice.chat("<red>✘ <dark_red>Podany przedmiot nie istnieje!");
}
