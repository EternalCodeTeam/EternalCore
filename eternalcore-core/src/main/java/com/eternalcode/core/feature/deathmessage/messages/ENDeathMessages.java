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
public class ENDeathMessages extends OkaeriConfig implements DeathMessages {

    @Comment({
        "# {PLAYER} - Killed player",
        "# {KILLER} - Killer (only for PvP deaths)"
    })
    public List<Notice> deathMessage = List.of(
        Notice.chat("<white>☠ <dark_red>{PLAYER} <red>died!"),
        Notice.chat("<white>☠ <dark_red>{PLAYER} <red>was killed by <dark_red>{KILLER}!")
    );

    @Comment({
        "# Messages shown when a player dies from specific damage causes",
        "# {PLAYER} - Killed player",
        "# {CAUSE} - Death cause (e.g., FALL, VOID)",
        "# List of DamageCauses: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/entity/EntityDamageEvent.DamageCause.html"
    })
    public Map<EntityDamageEvent.DamageCause, List<Notice>> deathMessageByDamageCause = Map.of(
        EntityDamageEvent.DamageCause.VOID, Collections.singletonList(
            BukkitNotice.builder()
                .actionBar("<white>☠ <dark_red>{PLAYER} <red>fell into the void!")
                .sound(Sound.BLOCK_PORTAL_TRAVEL)
                .build()
        ),
        EntityDamageEvent.DamageCause.FALL, Arrays.asList(
            BukkitNotice.builder()
                .sound(Sound.BLOCK_ANVIL_LAND)
                .chat("<white>☠ <dark_red>{PLAYER} <red>fell from a high place!")
                .build(),
            BukkitNotice.builder()
                .sound(Sound.BLOCK_ANVIL_LAND)
                .chat("<white>☠ <dark_red>{PLAYER} <red>fell off a deadly cliff!")
                .build()
        ),

        EntityDamageEvent.DamageCause.FIRE, Arrays.asList(
            BukkitNotice.builder()
                .sound(Sound.ITEM_FIRECHARGE_USE)
                .chat("<white>☠ <dark_red>{PLAYER} <red>burned to death!")
                .build(),
            BukkitNotice.builder()
                .sound(Sound.ITEM_FIRECHARGE_USE)
                .chat("<white>☠ <dark_red>{PLAYER} <red>was consumed by flames!")
                .build()
        ),

        EntityDamageEvent.DamageCause.FIRE_TICK, Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.ITEM_FIRECHARGE_USE)
                .chat("<white>☠ <dark_red>{PLAYER} <red>burned out in flames...")
                .build()
        ),

        EntityDamageEvent.DamageCause.LAVA, Arrays.asList(
            BukkitNotice.builder()
                .sound(Sound.BLOCK_LAVA_POP)
                .chat("<white>☠ <dark_red>{PLAYER} <red>fell into lava!")
                .build(),
            BukkitNotice.builder()
                .sound(Sound.BLOCK_LAVA_EXTINGUISH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>disappeared in a sea of lava!")
                .build()
        ),

        EntityDamageEvent.DamageCause.DROWNING, Arrays.asList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_DROWNED_DEATH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>drowned!")
                .build(),
            BukkitNotice.builder()
                .sound(Sound.ENTITY_DROWNED_DEATH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>died under the waves!")
                .build()
        ),

        EntityDamageEvent.DamageCause.POISON, Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_SPIDER_DEATH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>died from poison!")
                .build()
        ),

        EntityDamageEvent.DamageCause.MAGIC, Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_WITCH_DEATH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>died from dark magic!")
                .build()
        )
    );

    @Comment("# {PLAYER} - Player who died from an unknown cause")
    public List<Notice> unknownDeathCause = List.of(
        Notice.chat("<white>☠ <dark_red>{PLAYER} <red>died under mysterious circumstances!")
    );

    @Comment({
        "# Enhanced death messages by entity type",
        "# {PLAYER} - Killed player",
        "# {KILLER} - Entity that killed the player"
    })
    public Map<String, List<Notice>> deathMessageByEntity = Map.of(
        "ZOMBIE", Arrays.asList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_ZOMBIE_DEATH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>was devoured by a zombie!")
                .build(),
            BukkitNotice.builder()
                .sound(Sound.ENTITY_ZOMBIE_DEATH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>couldn't survive the zombie attack!")
                .build()
        ),
        "SKELETON", Arrays.asList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_SKELETON_DEATH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>was pierced by a skeleton's arrow!")
                .build(),
            BukkitNotice.builder()
                .sound(Sound.ENTITY_SKELETON_DEATH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>fell victim to a skeleton archer!")
                .build()
        ),
        "SPIDER", Arrays.asList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_SPIDER_DEATH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>was bitten by a spider!")
                .build(),
            BukkitNotice.builder()
                .sound(Sound.ENTITY_SPIDER_DEATH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>got caught in a spider's web!")
                .build()
        ),
        "CREEPER", Arrays.asList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_GENERIC_EXPLODE)
                .chat("<white>☠ <dark_red>{PLAYER} <red>was blown up by a creeper!")
                .build(),
            BukkitNotice.builder()
                .sound(Sound.ENTITY_GENERIC_EXPLODE)
                .chat("<white>☠ <dark_red>{PLAYER} <red>couldn't escape the explosion!")
                .build()
        ),
        "ENDERMAN", Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_ENDERMAN_DEATH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>was teleported into nothingness by an Enderman!")
                .build()
        ),
        "WITCH", Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_WITCH_DEATH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>was cursed by a witch!")
                .build()
        ),

        "CACTUS", Arrays.asList(
            BukkitNotice.builder()
                .sound(Sound.BLOCK_GRASS_BREAK)
                .chat("<white>☠ <dark_red>{PLAYER} <red>pricked themselves on a cactus!")
                .build(),
            BukkitNotice.builder()
                .sound(Sound.BLOCK_GRASS_BREAK)
                .chat("<white>☠ <dark_red>{PLAYER} <red>was pierced by cactus spines!")
                .build()
        ),
        "MAGMA_BLOCK", Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.BLOCK_LAVA_POP)
                .chat("<white>☠ <dark_red>{PLAYER} <red>was burned by a magma block!")
                .build()
        ),
        "SWEET_BERRY_BUSH", Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.BLOCK_SWEET_BERRY_BUSH_BREAK)
                .chat("<white>☠ <dark_red>{PLAYER} <red>was scratched to death by a berry bush!")
                .build()
        ),
        "WITHER_SKULL", Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_WITHER_DEATH)
                .chat("<white>☠ <dark_red>{PLAYER} <red>was annihilated by a wither skull!")
                .build()
        )
    );

    @Comment({
        "# PvP death messages by weapon type",
        "# {PLAYER} - Killed player",
        "# {KILLER} - Killer player",
        "# {WEAPON} - Weapon used"
    })
    public Map<String, List<Notice>> deathMessageByWeapon = Map.of(
        "BOW", Arrays.asList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_ARROW_HIT_PLAYER)
                .chat("<white>☠ <dark_red>{PLAYER} <red>was shot by <dark_red>{KILLER} <red>with a bow!")
                .build(),
            BukkitNotice.builder()
                .sound(Sound.ENTITY_ARROW_HIT_PLAYER)
                .chat("<white>☠ <dark_red>{PLAYER} <red>fell to <dark_red>{KILLER}<red>'s arrow!")
                .build()
        ),
        "CROSSBOW", Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.ITEM_CROSSBOW_HIT)
                .chat("<white>☠ <dark_red>{PLAYER} <red>was pierced by <dark_red>{KILLER}<red>'s crossbow bolt!")
                .build()
        ),
        "TRIDENT", Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.ITEM_TRIDENT_HIT)
                .chat("<white>☠ <dark_red>{PLAYER} <red>was impaled by <dark_red>{KILLER}<red>'s trident!")
                .build()
        ),
        "DIAMOND_SWORD", Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_PLAYER_ATTACK_CRIT)
                .chat("<white>☠ <dark_red>{PLAYER} <red>was sliced by <dark_red>{KILLER}<red>'s diamond sword!")
                .build()
        ),
        "NETHERITE_SWORD", Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_PLAYER_ATTACK_CRIT)
                .chat("<white>☠ <dark_red>{PLAYER} <red>was annihilated by <dark_red>{KILLER}<red>'s netherite sword!")
                .build()
        ),
        "PRIMED_TNT", Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_GENERIC_EXPLODE)
                .chat("<white>☠ <dark_red>{PLAYER} <red>was blown up by <dark_red>{KILLER} <red>using TNT!")
                .build()
        ),
        "HAND", Collections.singletonList(
            BukkitNotice.builder()
                .sound(Sound.ENTITY_PLAYER_ATTACK_WEAK)
                .chat("<white>☠ <dark_red>{PLAYER} <red>was beaten to death by <dark_red>{KILLER}<red>'s bare hands!")
                .build()
        )
    );
}
