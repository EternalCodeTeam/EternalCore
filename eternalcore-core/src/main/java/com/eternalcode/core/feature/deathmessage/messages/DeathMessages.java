package com.eternalcode.core.feature.deathmessage.messages;

import com.eternalcode.multification.notice.Notice;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;
import java.util.Map;

public interface DeathMessages {

    List<Notice> deathMessage();
    List<Notice> unknownDeathCause();

    Map<EntityDamageEvent.DamageCause, List<Notice>> deathMessageByDamageCause();
    Map<String, List<Notice>> deathMessageByEntity();
    Map<String, List<Notice>> deathMessageByWeapon();

}
