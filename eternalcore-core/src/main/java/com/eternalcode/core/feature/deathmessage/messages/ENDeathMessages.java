package com.eternalcode.core.feature.deathmessage.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import lombok.experimental.Accessors;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;

@Getter
@Accessors(fluent = true)
public class ENDeathMessages extends OkaeriConfig implements DeathMessages {

    public String unarmedWeaponName = "Fists";

    public List<Notice> playerKilledByEntity = Arrays.asList(
        Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was killed by <yellow>{MOB}<red>!"),
        Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was slain by <yellow>{MOB}<red>!"),
        Notice.chat("<white>☠ <dark_red>{PLAYER} <red>didn't survive the encounter with <yellow>{MOB}<red>!")
    );

    public List<Notice> playerKilledByOtherPlayer = Arrays.asList(
        Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was killed by <dark_red>{KILLER}<red>!"),
        Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was slain by <dark_red>{KILLER} <red>using <yellow>{WEAPON}<red>!"),
        Notice.chat("<white>☠ <dark_red>{KILLER} <red>eliminated <dark_red>{PLAYER}<red>!"),
        Notice.chat("<white>☠ <dark_red>{PLAYER} <red>died in combat with <dark_red>{KILLER}<red>!")
    );

    public List<Notice> playerDiedByUnknownCause = Arrays.asList(
        Notice.chat("<white>☠ <dark_red>{PLAYER} <red>died under mysterious circumstances!"),
        Notice.chat("<white>☠ <dark_red>{PLAYER} <red>died for unknown reasons!"),
        Notice.chat("<white>☠ <dark_red>{PLAYER} <red>mysteriously perished!")
    );

    public Map<EntityDamageEvent.DamageCause, List<Notice>> playerDiedByDamageCause = new HashMap<>() {{
        put(EntityDamageEvent.DamageCause.VOID, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>fell into the void!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>descended into nothingness!")
        ));

        put(EntityDamageEvent.DamageCause.FALL, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>fell from a high place!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>fell off a deadly cliff!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>hit the ground too hard!")
        ));

        put(EntityDamageEvent.DamageCause.FIRE, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>burned to death!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>went up in flames!")
        ));

        put(EntityDamageEvent.DamageCause.FIRE_TICK, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>burned to a crisp!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>couldn't extinguish the flames!")
        ));

        put(EntityDamageEvent.DamageCause.LAVA, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>tried to swim in lava!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>melted in lava!")
        ));

        put(EntityDamageEvent.DamageCause.DROWNING, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>drowned!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>forgot to come up for air!")
        ));

        put(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was blown up!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>exploded!")
        ));

        put(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was blown up by an explosion!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>got caught in an explosion!")
        ));

        put(EntityDamageEvent.DamageCause.LIGHTNING, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was struck by lightning!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was electrocuted!")
        ));

        put(EntityDamageEvent.DamageCause.SUFFOCATION, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>suffocated in a wall!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was squished!")
        ));

        put(EntityDamageEvent.DamageCause.STARVATION, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>starved to death!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>forgot to eat!")
        ));

        put(EntityDamageEvent.DamageCause.POISON, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>died from poison!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>succumbed to poison!")
        ));

        put(EntityDamageEvent.DamageCause.MAGIC, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was killed by magic!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>died from magical forces!")
        ));

        put(EntityDamageEvent.DamageCause.WITHER, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>withered away!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was killed by wither effect!")
        ));

        put(EntityDamageEvent.DamageCause.FALLING_BLOCK, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was crushed by a falling block!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>didn't look up!")
        ));

        put(EntityDamageEvent.DamageCause.THORNS, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was pricked to death!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>died from thorns!")
        ));

        put(EntityDamageEvent.DamageCause.DRAGON_BREATH, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was roasted by dragon breath!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>inhaled dragon breath!")
        ));

        put(EntityDamageEvent.DamageCause.FLY_INTO_WALL, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>experienced kinetic energy!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>flew into a wall!")
        ));

        put(EntityDamageEvent.DamageCause.HOT_FLOOR, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>walked on hot coals!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>discovered the floor was lava!")
        ));

        put(EntityDamageEvent.DamageCause.CRAMMING, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was squished!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>died from cramming!")
        ));

        put(EntityDamageEvent.DamageCause.DRYOUT, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>dried out!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>needed water!")
        ));

        put(EntityDamageEvent.DamageCause.FREEZE, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>froze to death!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>turned into an ice sculpture!")
        ));
    }};

    public Map<EntityType, List<Notice>> playerKilledByMobType = new HashMap<>() {{
        put(EntityType.ZOMBIE, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was eaten by a zombie!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>became zombie food!")
        ));

        put(EntityType.SKELETON, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was shot by a skeleton!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was turned into a pincushion by a skeleton!")
        ));

        put(EntityType.CREEPER, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was blown up by a creeper!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>didn't hear the hiss!")
        ));

        put(EntityType.SPIDER, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was killed by a spider!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>lost to an eight-legged enemy!")
        ));

        put(EntityType.ENDERMAN, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was killed by an Enderman!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>shouldn't have looked at it!")
        ));

        put(EntityType.BLAZE, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was burned by a Blaze!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was incinerated by a Blaze!")
        ));

        put(EntityType.WITHER_SKELETON, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was slain by a Wither Skeleton!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>withered away from a Wither Skeleton!")
        ));

        put(EntityType.WITCH, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was killed by a witch!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>drank the wrong potion!")
        ));

        put(EntityType.ENDER_DRAGON, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was slain by the Ender Dragon!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>faced the might of the dragon!")
        ));

        put(EntityType.WITHER, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was destroyed by the Wither!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>withered away!")
        ));

        put(EntityType.PHANTOM, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was killed by a Phantom!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>should have slept!")
        ));

        put(EntityType.DROWNED, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was drowned by a Drowned!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>couldn't swim away fast enough!")
        ));

        put(EntityType.PILLAGER, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was shot by a Pillager!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was raided by a Pillager!")
        ));

        put(EntityType.RAVAGER, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was trampled by a Ravager!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>couldn't escape the Ravager!")
        ));

        put(EntityType.VEX, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was killed by a Vex!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was swarmed by Vexes!")
        ));

        put(EntityType.VINDICATOR, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was chopped by a Vindicator!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>faced the axe of a Vindicator!")
        ));

        put(EntityType.EVOKER, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was killed by an Evoker!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was cursed by an Evoker!")
        ));

        put(EntityType.HOGLIN, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was gored by a Hoglin!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was trampled by a Hoglin!")
        ));

        put(EntityType.PIGLIN_BRUTE, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was slain by a Piglin Brute!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>angered a Piglin Brute!")
        ));

        put(EntityType.ZOGLIN, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was killed by a Zoglin!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was mauled by a Zoglin!")
        ));
    }};
}
