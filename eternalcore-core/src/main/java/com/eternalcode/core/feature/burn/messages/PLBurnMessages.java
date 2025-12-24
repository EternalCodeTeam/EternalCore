package com.eternalcode.core.feature.burn.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLBurnMessages extends OkaeriConfig implements BurnMessages {

    @Comment("# Dostępne placeholdery: {PLAYER} - podpalony gracz, {TICKS} - liczba ticków podpalenia")
    Notice burnedSelf = Notice.chat("<color:#9d6eef>► <white>Zostałeś podpalony na <color:#9d6eef>{TICKS}<white> ticków!");

    @Comment(" ")
    Notice burnedOther = Notice.chat("<color:#9d6eef>► {PLAYER} <white>został podpalony na <color:#9d6eef>{TICKS}<white> ticków!");
}
