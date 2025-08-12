package com.eternalcode.core.translation;

import com.eternalcode.core.litecommand.argument.messages.ArgumentMessages;
import com.eternalcode.core.configuration.contextual.ConfigItem;
import com.eternalcode.core.feature.adminchat.messages.AdminChatMessages;
import com.eternalcode.core.feature.afk.messages.AfkMessages;
import com.eternalcode.core.feature.automessage.messages.AutoMessageMessages;
import com.eternalcode.core.feature.broadcast.messages.BroadcastMessages;
import com.eternalcode.core.feature.burn.messages.BurnMessages;
import com.eternalcode.core.feature.fun.demoscreen.messages.DemoScreenMessages;
import com.eternalcode.core.feature.fun.elderguardian.messages.ElderGuardianMessages;
import com.eternalcode.core.feature.fun.endscreen.messages.EndScreenMessages;
import com.eternalcode.core.feature.helpop.messages.HelpOpSection;
import com.eternalcode.core.feature.home.messages.HomeMessages;
import com.eternalcode.core.feature.itemedit.messages.ItemEditMessages;
import com.eternalcode.core.feature.jail.messages.JailMessages;
import com.eternalcode.core.feature.motd.messages.MotdMessages;
import com.eternalcode.core.feature.msg.messages.MsgMessages;
import com.eternalcode.core.feature.near.messages.NearMessages;
import com.eternalcode.core.feature.randomteleport.messages.RandomTeleportMessages;
import com.eternalcode.core.feature.seen.messages.SeenMessages;
import com.eternalcode.core.feature.setslot.messages.SetSlotMessages;
import com.eternalcode.core.feature.signeditor.messages.SignEditorMessages;
import com.eternalcode.core.feature.spawn.messages.SpawnMessages;
import com.eternalcode.core.feature.sudo.messages.SudoMessages;
import com.eternalcode.core.feature.teleportrandomplayer.messages.TeleportToRandomPlayerMessages;
import com.eternalcode.core.feature.teleportrequest.messages.TeleportRequestMessages;
import com.eternalcode.core.feature.time.messages.TimeAndWeatherMessages;
import com.eternalcode.core.feature.vanish.messages.VanishMessages;
import com.eternalcode.core.feature.warp.messages.WarpMessages;
import com.eternalcode.multification.notice.Notice;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;
import java.util.Map;

public interface Translation {

    interface Format {
        String enable();
        String disable();
    }

    interface TeleportSection {
        // teleport
        Notice teleportedToPlayer();
        Notice teleportedPlayerToPlayer();
        Notice teleportedToHighestBlock();
        Notice teleportedAllToPlayer();

        // Task
        Notice teleportTimerFormat();
        Notice teleported();
        Notice teleporting();
        Notice teleportTaskCanceled();
        Notice teleportTaskAlreadyExist();

        // Coordinates XYZ
        Notice teleportedToCoordinates();
        Notice teleportedSpecifiedPlayerToCoordinates();

        // Back
        Notice teleportedToLastLocation();
        Notice teleportedSpecifiedPlayerLastLocation();
        Notice lastLocationNoExist();
    }

    interface ChatSection {
        Notice disabled();
        Notice enabled();
        Notice cleared();
        Notice alreadyDisabled();
        Notice alreadyEnabled();
        Notice slowModeSet();
        Notice slowModeOff();
        Notice slowMode();
        Notice disabledChatInfo();
        Notice commandNotFound();
        Notice tellrawInfo();
        Notice tellrawAllInfo();
        Notice tellrawSaved();
        Notice tellrawNoSaved();
        Notice tellrawMultipleSent();
        Notice tellrawCleared();
    }

    interface EventSection {
        List<Notice> deathMessage();
        List<Notice> unknownDeathCause();
        List<Notice> joinMessage();
        List<Notice> quitMessage();
        List<Notice> firstJoinMessage();

        Map<EntityDamageEvent.DamageCause, List<Notice>> deathMessageByDamageCause();
    }

    interface InventorySection {
        Notice inventoryClearMessage();
        Notice inventoryClearMessageBy();
        String disposalTitle();
    }

    interface PlayerSection {
        // feed
        Notice feedMessage();
        Notice feedMessageBy();

        // heal
        Notice healMessage();
        Notice healMessageBy();

        // kill
        Notice killedMessage();

        // speed
        Notice speedBetweenZeroAndTen();
        Notice speedTypeNotCorrect();

        Notice speedWalkSet();
        Notice speedFlySet();

        Notice speedWalkSetBy();
        Notice speedFlySetBy();

        // godmode
        Notice godEnable();
        Notice godDisable();
        Notice godSetEnable();
        Notice godSetDisable();

        // fly
        Notice flyEnable();
        Notice flyDisable();
        Notice flySetEnable();
        Notice flySetDisable();

        // ping
        Notice pingMessage();
        Notice pingOtherMessage();

        // gamemode
        Notice gameModeNotCorrect();
        Notice gameModeMessage();
        Notice gameModeSetMessage();

        // online
        Notice onlinePlayersCountMessage();
        Notice onlinePlayersMessage();

        // slot-bypass
        List<String> fullServerSlots();

        // whois
        List<String> whoisCommand();

        // butcher
        Notice butcherCommand();
        Notice safeChunksMessage();
    }

    interface ItemSection {
        // give
        Notice giveReceived();
        Notice giveGiven();
        Notice giveNoSpace();
        Notice giveNotItem();

        // others
        Notice repairMessage();
        Notice repairAllMessage();
        Notice repairDelayMessage();
        Notice skullMessage();
        Notice enchantedMessage();
        Notice enchantedMessageFor();
        Notice enchantedMessageBy();
    }

    interface LanguageSection {
        Notice languageChanged();

        List<ConfigItem> decorationItems();
    }

    interface ContainerSection {
        Notice genericContainerOpened();
        Notice genericContainerOpenedBy();
        Notice genericContainerOpenedFor();
    }

    ElderGuardianMessages elderGuardian();
    DemoScreenMessages demoScreen();
    EndScreenMessages endScreen();

    Language getLanguage();
    // argument section
    ArgumentMessages argument();
    // format section
    Format format();
    // HelpOp Section
    HelpOpSection helpOp();
    // AdminChat Section
    AdminChatMessages adminChat();
    // sudo
    SudoMessages sudo();
    // Teleport Section
    TeleportSection teleport();
    // teleport to random player section.
    TeleportToRandomPlayerMessages teleportToRandomPlayer();
    // Random Teleport Section
    RandomTeleportMessages randomTeleport();
    // Chat Section
    ChatSection chat();
    // Broadcast Section
    BroadcastMessages broadcast();
    // Warp Section
    WarpMessages warp();
    // Home section
    HomeMessages home();
    // tpa section
    TeleportRequestMessages tpa();
    // private section
    MsgMessages msg();
    // afk section
    AfkMessages afk();
    // event section
    EventSection event();
    // inventory section
    InventorySection inventory();
    // player section
    PlayerSection player();
    //Seen section
    SeenMessages seen();
    // sign editor section
    SignEditorMessages signEditor();
    // spawn section
    SpawnMessages spawn();
    // set slot section
    SetSlotMessages setSlot();
    // item section
    ItemSection item();
    // itemedit
    ItemEditMessages itemEdit();
    // time and weather
    TimeAndWeatherMessages timeAndWeather();
    // language section
    LanguageSection language();
    // container section
    ContainerSection container();
    // auto message section
    AutoMessageMessages autoMessage();
    // jail section
    JailMessages jailSection();
    // burn section
    BurnMessages burn();
    // vanish section
    VanishMessages vanish();
    // near section
    NearMessages near()
    // motd section
    MotdMessages motd();
}
