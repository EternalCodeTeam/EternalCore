package com.eternalcode.core.feature.deathmessage.messages;

import com.eternalcode.multification.notice.Notice;
import java.util.List;
import java.util.Map;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;

public interface DeathMessages {

    String unarmedWeaponName();

    List<Notice> deathMessageByEntity();
    List<Notice> deathMessage();
    List<Notice> unknownDeathCause();

    Map<EntityDamageEvent.DamageCause, List<Notice>> deathMessageByDamageCause();
    Map<EntityType, List<Notice>> deathMessageByMobType();

}
