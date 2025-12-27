package com.eternalcode.core.feature.give.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLGiveMessages extends OkaeriConfig implements GiveMessages {
    Notice itemGivenByAdmin = Notice.chat("<color:#9d6eef>► <white>Otrzymałeś: <color:#9d6eef>{ITEM}");
    Notice itemGivenToTargetPlayer = Notice.chat("<color:#9d6eef>► <white>Gracz <color:#9d6eef>{PLAYER} <white>otrzymał: <color:#9d6eef>{ITEM}");
    Notice noSpace = Notice.chat("<red>✘ <dark_red>Brak miejsca w ekwipunku!");
    Notice itemNotFound = Notice.chat("<red>✘ <dark_red>Podany przedmiot nie istnieje!");
}
