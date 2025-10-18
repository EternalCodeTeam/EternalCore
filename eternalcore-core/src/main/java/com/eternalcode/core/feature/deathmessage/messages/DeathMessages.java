package com.eternalcode.core.feature.deathmessage.messages;

import com.eternalcode.multification.notice.Notice;
import java.util.List;
import java.util.Map;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;

public interface DeathMessages {

    String unarmedWeaponName();

    List<Notice> playerKilledByEntity();
    List<Notice> playerKilledByOtherPlayer();
    List<Notice> playerDiedByUnknownCause();

    Map<EntityDamageEvent.DamageCause, List<Notice>> playerDiedByDamageCause();
    Map<EntityType, List<Notice>> playerKilledByMobType();

}
