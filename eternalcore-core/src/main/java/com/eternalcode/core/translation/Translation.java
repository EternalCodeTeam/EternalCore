package com.eternalcode.core.translation;

import com.eternalcode.core.feature.adminchat.messages.AdminChatMessages;
import com.eternalcode.core.feature.afk.messages.AfkMessages;
import com.eternalcode.core.feature.automessage.messages.AutoMessageMessages;
import com.eternalcode.core.feature.broadcast.messages.BroadcastMessages;
import com.eternalcode.core.feature.burn.messages.BurnMessages;
import com.eternalcode.core.feature.butcher.messages.ButcherMessages;
import com.eternalcode.core.feature.clear.messages.ClearMessages;
import com.eternalcode.core.feature.container.messages.ContainerMessages;
import com.eternalcode.core.feature.deathmessage.messages.DeathMessages;
import com.eternalcode.core.feature.enchant.messages.EnchantMessages;
import com.eternalcode.core.feature.feed.messages.FeedMessages;
import com.eternalcode.core.feature.fly.messages.FlyMessages;
import com.eternalcode.core.feature.freeze.messages.FreezeMessages;
import com.eternalcode.core.feature.fun.demoscreen.messages.DemoScreenMessages;
import com.eternalcode.core.feature.fun.elderguardian.messages.ElderGuardianMessages;
import com.eternalcode.core.feature.fun.endscreen.messages.EndScreenMessages;
import com.eternalcode.core.feature.gamemode.messages.GameModeMessages;
import com.eternalcode.core.feature.give.messages.GiveMessages;
import com.eternalcode.core.feature.godmode.messages.GodModeMessages;
import com.eternalcode.core.feature.heal.messages.HealMessages;
import com.eternalcode.core.feature.helpop.messages.HelpOpSection;
import com.eternalcode.core.feature.home.messages.HomeMessages;
import com.eternalcode.core.feature.ignore.messages.IgnoreMessages;
import com.eternalcode.core.feature.itemedit.messages.ItemEditMessages;
import com.eternalcode.core.feature.jail.messages.JailMessages;
import com.eternalcode.core.feature.joinmessage.messages.JoinMessage;
import com.eternalcode.core.feature.kill.messages.KillMessages;
import com.eternalcode.core.feature.motd.messages.MotdMessages;
import com.eternalcode.core.feature.msg.messages.MsgMessages;
import com.eternalcode.core.feature.near.messages.NearMessages;
import com.eternalcode.core.feature.onlineplayers.messages.OnlineMessages;
import com.eternalcode.core.feature.ping.PingMessages;
import com.eternalcode.core.feature.playtime.messages.PlaytimeMessages;
import com.eternalcode.core.feature.powertool.messages.PowertoolMessages;
import com.eternalcode.core.feature.quitmessage.messages.QuitMessage;
import com.eternalcode.core.feature.randomteleport.messages.RandomTeleportMessages;
import com.eternalcode.core.feature.repair.messages.RepairMessages;
import com.eternalcode.core.feature.seen.messages.SeenMessages;
import com.eternalcode.core.feature.setslot.messages.SetSlotMessages;
import com.eternalcode.core.feature.signeditor.messages.SignEditorMessages;
import com.eternalcode.core.feature.skull.messages.SkullMessages;
import com.eternalcode.core.feature.spawn.messages.SpawnMessages;
import com.eternalcode.core.feature.speed.messages.SpeedMessages;
import com.eternalcode.core.feature.sudo.messages.SudoMessages;
import com.eternalcode.core.feature.teleport.messages.TeleportOfflineMessages;
import com.eternalcode.core.feature.teleportrandomplayer.messages.TeleportToRandomPlayerMessages;
import com.eternalcode.core.feature.teleportrequest.messages.TeleportRequestMessages;
import com.eternalcode.core.feature.time.messages.TimeAndWeatherMessages;
import com.eternalcode.core.feature.vanish.messages.VanishMessages;
import com.eternalcode.core.feature.warp.messages.WarpMessages;
import com.eternalcode.core.feature.whois.WhoIsMessages;
import com.eternalcode.core.litecommand.argument.messages.ArgumentMessages;
import com.eternalcode.multification.notice.Notice;
import java.util.List;

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
    }
    interface InventorySection {
        String disposalTitle();
    }

    // clear section
    ClearMessages clear();

    // feed section
    FeedMessages feed();

    // heal section
    HealMessages heal();

    // kill section
    KillMessages kill();

    // speed section
    SpeedMessages speed();

    // godmode section
    GodModeMessages godmode();

    // fly section
    FlyMessages fly();

    // ping section
    PingMessages ping();

    // gamemode section
    GameModeMessages gamemode();

    // online section
    OnlineMessages online();

    // whois section
    WhoIsMessages whois();

    // butcher section
    ButcherMessages butcher();

    // give section
    GiveMessages give();

    // skull section
    SkullMessages skull();

    RepairMessages repair();

    EnchantMessages enchant();
    ElderGuardianMessages elderGuardian();
    DemoScreenMessages demoScreen();
    EndScreenMessages endScreen();

    JoinMessage join();
    QuitMessage quit();
    Language getLanguage();
    // argument section
    ArgumentMessages argument();
    // format section
    Format format();
    // HelpOp Section
    HelpOpSection helpOp();
    // AdminChat Section
    AdminChatMessages adminChat();
    // Ignore Section
    IgnoreMessages ignore();
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
    // death message section
    DeathMessages deathMessage();
    // inventory section
    InventorySection inventory();
    //Seen section
    SeenMessages seen();
    // sign editor section
    SignEditorMessages signEditor();
    // spawn section
    SpawnMessages spawn();
    // set slot section
    SetSlotMessages setSlot();
    // itemedit
    ItemEditMessages itemEdit();
    // time and weather
    TimeAndWeatherMessages timeAndWeather();
    // container section
    ContainerMessages container();
    // auto message section
    AutoMessageMessages autoMessage();
    // jail section
    JailMessages jailSection();
    // burn section
    BurnMessages burn();
    // vanish section
    VanishMessages vanish();
    // near section
    NearMessages near();
    // motd section
    MotdMessages motd();
    // offlineplayer section
    TeleportOfflineMessages teleportToOfflinePlayer();
    // playtime section
    PlaytimeMessages playtime();
    // freeze section
    FreezeMessages freeze();
    // powertool section
    PowertoolMessages powertool();
}
