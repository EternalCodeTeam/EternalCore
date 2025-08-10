package com.eternalcode.core.feature.deathmessage.messages;

import com.eternalcode.multification.bukkit.notice.BukkitNotice;
import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.Sound;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@Accessors(fluent = true)
public class PLDeathMessages extends OkaeriConfig implements DeathMessages {

    @Comment({
        "# {PLAYER} - Gracz, który zginął",
        "# {KILLER} - Gracz, który zabił (tylko w przypadku PvP)"
    })
    public List<Notice> deathMessage = List.of(
        Notice.actionbar("<white>☠ <dark_red>{PLAYER} <red>zginął przez {KILLER}!"),
        Notice.actionbar("<white>☠ <dark_red>{PLAYER} <red>zginął tragicznie podczas ciężkiej walki!")
    );

    @Comment("# {PLAYER} - Gracz, który zginął z nieznanej przyczyny")
    public List<Notice> unknownDeathCause = List.of(
        Notice.chat("<white>☠ <dark_red>{PLAYER} <red>został zabity przez niezidentyfikowany obiekt!")
    );

    @Comment({
        "# Wiadomości wyświetlane gdy gracz ginie od konkretnego typu obrażeń",
        "# {PLAYER} - Gracz, który zginął",
        "# {CAUSE} - Przyczyna śmierci (np. UPADEK, VOID)",
        "# List of DamageCauses: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/entity/EntityDamageEvent.DamageCause.html"
    })
    public Map<EntityDamageEvent.DamageCause, List<Notice>> deathMessageByDamageCause = Map.of(
        EntityDamageEvent.DamageCause.VOID, Collections.singletonList(
            BukkitNotice.builder()
                .actionBar("<white>☠ <dark_red>{PLAYER} <red>wypadł w otchłań!")
                .sound(Sound.BLOCK_PORTAL_TRAVEL)
                .build()
        ),
        EntityDamageEvent.DamageCause.FALL, Arrays.asList(
            BukkitNotice.builder()
                .sound(Sound.BLOCK_ANVIL_LAND)
                .chat("<white>☠ <dark_red>{PLAYER} <red>spadł z wysokości!")
                .build(),
            BukkitNotice.builder()
                .sound(Sound.BLOCK_ANVIL_LAND)
                .chat("<white>☠ <dark_red>{PLAYER} <red>spadł z zabójczego klifu!")
                .build()
        ),

        EntityDamageEvent.DamageCause.FIRE, Arrays.asList(
            BukkitNotice.builder()
                .sound(Sound.ITEM_FIRECHARGE_USE)
                .chat("<white>☠ <dark_red>{PLAYER} <red>spłonął żywcem!")
                .build(),
            BukkitNotice.builder()
                .sound(Sound.ITEM_FIRECHARGE_USE)
                .chat("<white>☠ <dark_red>{PLAYER} <red>został pochłonięty przez płomienie!")
                .build()
        ),

        EntityDamageEvent.DamageCause.FIRE_TICK, Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.ITEM_FIRECHARGE_USE)
                .chat("<white>☠ <dark_red>{PLAYER} <red>dogasał w płomieniach...")
                .build()
        ),

        EntityDamageEvent.DamageCause.LAVA, Arrays.asList(
            BukkitNotice.builder()
                .sound(Sound.BLOCK_LAVA_POP)
                .chat("<white>☠ <dark_red>{PLAYER} <red>wpadł do lawy!")
                .build(),
            BukkitNotice.builder()
                .sound(Sound.BLOCK_LAVA_EXTINGUISH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>zniknął w morzu lawy!")
                .build()
        ),

        EntityDamageEvent.DamageCause.DROWNING, Arrays.asList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_DROWNED_DEATH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>utonął!")
                .build(),
            BukkitNotice.builder()
                .sound(Sound.ENTITY_DROWNED_DEATH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>zginął pod falami!")
                .build()
        ),

        EntityDamageEvent.DamageCause.POISON, Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_SPIDER_DEATH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>zmarł od trucizny!")
                .build()
        ),

        EntityDamageEvent.DamageCause.MAGIC, Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_WITCH_DEATH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>zginął od czarnej magii!")
                .build()
        )
    );

    @Comment({
        "# Rozszerzone wiadomości śmierci według typu entity",
        "# {PLAYER} - Zabity gracz",
        "# {KILLER} - Entity które zabiło gracza"
    })
    public Map<String, List<Notice>> deathMessageByEntity = Map.of(
        "ZOMBIE", Arrays.asList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_ZOMBIE_DEATH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>został pożarty przez zombie!")
                .build(),
            BukkitNotice.builder()
                .sound(Sound.ENTITY_ZOMBIE_DEATH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>nie przetrwał ataku zombie!")
                .build()
        ),
        "SKELETON", Arrays.asList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_SKELETON_DEATH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>został przeszyty strzałą szkieleta!")
                .build(),
            BukkitNotice.builder()
                .sound(Sound.ENTITY_SKELETON_DEATH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>padł ofiarą szkieleta łucznika!")
                .build()
        ),
        "SPIDER", Arrays.asList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_SPIDER_DEATH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>został ugryziony przez pająka!")
                .build(),
            BukkitNotice.builder()
                .sound(Sound.ENTITY_SPIDER_DEATH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>wpadł w pajęczą sieć!")
                .build()
        ),
        "CREEPER", Arrays.asList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_GENERIC_EXPLODE)
                .chat("<white>☠ <dark_red>{PLAYER} <red>został wysadzony przez creepera!")
                .build(),
            BukkitNotice.builder()
                .sound(Sound.ENTITY_GENERIC_EXPLODE)
                .chat("<white>☠ <dark_red>{PLAYER} <red>nie zdążył uciec przed eksplozją!")
                .build()
        ),
        "ENDERMAN", Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_ENDERMAN_DEATH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>został teleportowany w nicość przez Endermana!")
                .build()
        ),

        "CACTUS", Arrays.asList(
            BukkitNotice.builder()
                .sound(Sound.BLOCK_GRASS_BREAK)
                .chat("<white>☠ <dark_red>{PLAYER} <red>ukłuł się na kaktusie!")
                .build(),
            BukkitNotice.builder()
                .sound(Sound.BLOCK_GRASS_BREAK)
                .chat("<white>☠ <dark_red>{PLAYER} <red>został przebity przez kolce kaktusa!")
                .build()
        ),
        "MAGMA_BLOCK", Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.BLOCK_LAVA_POP)
                .chat("<white>☠ <dark_red>{PLAYER} <red>został spalony przez magma block!")
                .build()
        ),
        "SWEET_BERRY_BUSH", Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.BLOCK_SWEET_BERRY_BUSH_BREAK)
                .chat("<white>☠ <dark_red>{PLAYER} <red>został zadrapany na śmierć przez krzak jagód!")
                .build()
        ),
        "PRIMED_TNT", Arrays.asList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_GENERIC_EXPLODE)
                .chat("<white>☠ <dark_red>{PLAYER} <red>wysadził sie w powietrze!")
                .build(),
            BukkitNotice.builder()
                .sound(Sound.ENTITY_GENERIC_EXPLODE)
                .chat("<white>☠ <red>Piroman <dark_red>{PLAYER} <red>nie przeżył!")
                .build()
        )
    );

    @Comment({
        "# Wiadomości śmierci PvP według typu broni",
        "# {PLAYER} - Zabity gracz",
        "# {KILLER} - Zabójca",
        "# {WEAPON} - Używana broń"
    })
    public Map<String, List<Notice>> deathMessageByWeapon = Map.of(
        "BOW", Arrays.asList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_ARROW_HIT_PLAYER)
                .chat("<white>☠ <dark_red>{PLAYER} <red>został zastrzelony przez <dark_red>{KILLER} <red>z łuku!")
                .build(),
            BukkitNotice.builder()
                .sound(Sound.ENTITY_ARROW_HIT_PLAYER)
                .chat("<white>☠ <dark_red>{PLAYER} <red>padł od strzały gracza <dark_red>{KILLER}!")
                .build()
        ),
        "CROSSBOW", Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.ITEM_CROSSBOW_HIT)
                .chat("<white>☠ <dark_red>{PLAYER} <red>został przebity bełtem <dark_red>{KILLER}!")
                .build()
        ),
        "TRIDENT", Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.ITEM_TRIDENT_HIT)
                .chat("<white>☠ <dark_red>{PLAYER} <red>został przebity trójzębem przez <dark_red>{KILLER}!")
                .build()
        ),
        "DIAMOND_SWORD", Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_PLAYER_ATTACK_CRIT)
                .chat("<white>☠ <dark_red>{PLAYER} <red>został pokrojony diamentowym mieczem przez <dark_red>{KILLER}!")
                .build()
        ),
        "NETHERITE_SWORD", Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_PLAYER_ATTACK_CRIT)
                .chat("<white>☠ <dark_red>{PLAYER} <red>został unicestwiony netheritowym mieczem gracza <dark_red>{KILLER}!")
                .build()
        ),
        "HAND", Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_PLAYER_ATTACK_WEAK)
                .chat("<white>☠ <dark_red>{PLAYER} <red>został zmasakrowany gołymi rękami przez <dark_red>{KILLER}!")
                .build()
        )
    );
}
