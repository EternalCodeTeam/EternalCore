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
public class PLDeathMessages extends OkaeriConfig implements DeathMessages {

    public String unarmedWeaponName = "Pięści";

    public List<Notice> deathMessageByEntity = Arrays.asList(
        Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zabity przez <yellow>{MOB}<red>!"),
        Notice.chat("<white>☠ <dark_red>{PLAYER} <red>zginął z rąk <yellow>{MOB}<red>!"),
        Notice.chat("<white>☠ <dark_red>{PLAYER} <red>nie przeżył spotkania z <yellow>{MOB}<red>!")
    );

    public List<Notice> deathMessage = Arrays.asList(
        Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zabity przez <dark_red>{KILLER}<red>!"),
        Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został pokonany przez <dark_red>{KILLER} <red>przy użyciu <yellow>{WEAPON}<red>!"),
        Notice.chat("<white>☠ <dark_red>{KILLER} <red>wyeliminował <dark_red>{PLAYER}<red>!"),
        Notice.chat("<white>☠ <dark_red>{PLAYER} <red>zginął w walce z <dark_red>{KILLER}<red>!")
    );

    public List<Notice> unknownDeathCause = Arrays.asList(
        Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zabity przez niezidentyfikowany obiekt!"),
        Notice.chat("<white>☠ <dark_red>{PLAYER} <red>zginął z nieznanych powodów!"),
        Notice.chat("<white>☠ <dark_red>{PLAYER} <red>tajemniczo zmarł!")
    );

    public Map<EntityDamageEvent.DamageCause, List<Notice>> deathMessageByDamageCause = new HashMap<>() {{
        put(EntityDamageEvent.DamageCause.VOID, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>wypadł w otchłań!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>zniknął w pustce!")
        ));

        put(EntityDamageEvent.DamageCause.FALL, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>spadł z wysokości!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>spadł z zabójczego klifu!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>uderzył o ziemię zbyt mocno!")
        ));

        put(EntityDamageEvent.DamageCause.FIRE, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>spłonął żywcem!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>stanął w płomieniach!")
        ));

        put(EntityDamageEvent.DamageCause.FIRE_TICK, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>spłonął na popiół!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>nie zdołał ugasić płomieni!")
        ));

        put(EntityDamageEvent.DamageCause.LAVA, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>próbował pływać w lawie!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>roztopił się w lawie!")
        ));

        put(EntityDamageEvent.DamageCause.DROWNING, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>utonął!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>zapomniał wynurzyć się!")
        ));

        put(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został wysadzony w powietrze!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>eksplodował!")
        ));

        put(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został wysadzony w powietrze!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>wpadł w eksplozję!")
        ));

        put(EntityDamageEvent.DamageCause.LIGHTNING, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został rażony piorunem!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został porażony prądem!")
        ));

        put(EntityDamageEvent.DamageCause.SUFFOCATION, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>udusił się w ścianie!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zmiażdżony!")
        ));

        put(EntityDamageEvent.DamageCause.STARVATION, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>umarł z głodu!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>zapomniał zjeść!")
        ));

        put(EntityDamageEvent.DamageCause.POISON, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>zmarł od trucizny!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>uległ zatruciu!")
        ));

        put(EntityDamageEvent.DamageCause.MAGIC, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zabity przez magię!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>zginął od magicznych sił!")
        ));

        put(EntityDamageEvent.DamageCause.WITHER, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>uschł na wiór!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zabity przez efekt więdnięcia!")
        ));

        put(EntityDamageEvent.DamageCause.FALLING_BLOCK, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zmiażdżony przez spadający blok!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>nie spojrzał do góry!")
        ));

        put(EntityDamageEvent.DamageCause.THORNS, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został ukłuty na śmierć!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>zginął od kolców!")
        ));

        put(EntityDamageEvent.DamageCause.DRAGON_BREATH, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został usmażony oddechem smoka!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>wdychał oddech smoka!")
        ));

        put(EntityDamageEvent.DamageCause.FLY_INTO_WALL, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>doświadczył energii kinetycznej!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>wleciał w ścianę!")
        ));

        put(EntityDamageEvent.DamageCause.HOT_FLOOR, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>chodził po rozżarzonych węglach!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>odkrył, że podłoga to lawa!")
        ));

        put(EntityDamageEvent.DamageCause.CRAMMING, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zmiażdżony!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>zginął od stłoczenia!")
        ));

        put(EntityDamageEvent.DamageCause.DRYOUT, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>wysechł!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>potrzebował wody!")
        ));

        put(EntityDamageEvent.DamageCause.FREEZE, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>zamarznął na śmierć!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>zamienił się w lodową rzeźbę!")
        ));
    }};

    public Map<EntityType, List<Notice>> deathMessageByMobType = new HashMap<>() {{
        put(EntityType.ZOMBIE, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zjedzony przez zombie!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>stał się pożywieniem zombie!")
        ));

        put(EntityType.SKELETON, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zastrzelony przez szkieleta!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zamieniony w poduszkę od igieł przez szkieleta!")
        ));

        put(EntityType.CREEPER, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został wysadzony w powietrze przez creepera!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>nie usłyszał syczenia!")
        ));

        put(EntityType.SPIDER, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zabity przez pająka!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>przegrał z ośmionogim przeciwnikiem!")
        ));

        put(EntityType.ENDERMAN, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zabity przez Endermana!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>nie powinien był na niego patrzeć!")
        ));

        put(EntityType.BLAZE, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został spalony przez Blaze'a!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został spalony na popiół przez Blaze'a!")
        ));

        put(EntityType.WITHER_SKELETON, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zabity przez Wither Skeletona!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>uschł od ataku Wither Skeletona!")
        ));

        put(EntityType.WITCH, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zabity przez wiedźmę!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>wypił zły eliksir!")
        ));

        put(EntityType.ENDER_DRAGON, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zabity przez Smoka Kresu!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>stanął twarzą w twarz z potęgą smoka!")
        ));

        put(EntityType.WITHER, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zniszczony przez Withera!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>uschł na wiór!")
        ));

        put(EntityType.PHANTOM, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zabity przez Phantoma!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>powinien był pójść spać!")
        ));

        put(EntityType.DROWNED, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został utopiony przez Topielca!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>nie zdołał odpłynąć wystarczająco szybko!")
        ));

        put(EntityType.PILLAGER, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zastrzelony przez Grabieżcę!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został napadnięty przez Grabieżcę!")
        ));

        put(EntityType.RAVAGER, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zdeptany przez Spustoszyciel!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>nie mógł uciec przed Spustoszycielem!")
        ));

        put(EntityType.VEX, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zabity przez Vecka!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zaatakowany przez rój Vecków!")
        ));

        put(EntityType.VINDICATOR, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został posiekany przez Mściciela!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>stanął twarzą w twarz z toporem Mściciela!")
        ));

        put(EntityType.EVOKER, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zabity przez Przywoływacza!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został przeklęty przez Przywoływacza!")
        ));

        put(EntityType.HOGLIN, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został nadziany przez Hoglina!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zdeptany przez Hoglina!")
        ));

        put(EntityType.PIGLIN_BRUTE, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zabity przez Piglin Bruta!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>rozgniewał Piglin Bruta!")
        ));

        put(EntityType.ZOGLIN, Arrays.asList(
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zabity przez Zoglina!"),
            Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został poszarpany przez Zoglina!")
        ));
    }};
}
