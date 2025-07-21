package com.eternalcode.core.feature.troll.elderguardian.messages;

import com.eternalcode.multification.notice.Notice;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.dzikoysk.cdn.entity.Contextual;

@Getter
@Accessors(fluent = true)
@Contextual
public class PLElderGuardianMessages implements ElderGuardianMessages {

    public Notice elderGuardianShown = Notice.chat("<green>► <white>Pokazano Elder Guardian'a graczowi <green>{PLAYER}<white>!");
    public Notice elderGuardianShownSilently = Notice.chat("<green>► <white>Pokazano Elder Guardian'a graczowi <green>{PLAYER}<white> po cichu!");
}
