package com.eternalcode.core.translation;

import com.eternalcode.core.feature.adminchat.messages.AdminChatMessages;
import com.eternalcode.core.feature.afk.messages.AfkMessages;
import com.eternalcode.core.feature.automessage.messages.AutoMessageMessages;
import com.eternalcode.core.feature.broadcast.messages.BroadcastMessages;
import com.eternalcode.core.feature.burn.messages.BurnMessages;
import com.eternalcode.core.feature.butcher.messages.ButcherMessages;
import com.eternalcode.core.feature.chat.messages.ChatMessages;
import com.eternalcode.core.feature.clear.messages.ClearMessages;
import com.eternalcode.core.feature.container.messages.ContainerMessages;
import com.eternalcode.core.feature.deathmessage.messages.DeathMessages;
import com.eternalcode.core.feature.disposal.messages.DisposalMessages;
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
import com.eternalcode.core.feature.teleport.messages.TeleportMessages;
import com.eternalcode.core.feature.teleportoffline.TeleportOfflineMessages;
import com.eternalcode.core.feature.teleportrandomplayer.messages.TeleportToRandomPlayerMessages;
import com.eternalcode.core.feature.teleportrequest.messages.TeleportRequestMessages;
import com.eternalcode.core.feature.time.messages.TimeAndWeatherMessages;
import com.eternalcode.core.feature.vanish.messages.VanishMessages;
import com.eternalcode.core.feature.warp.messages.WarpMessages;
import com.eternalcode.core.feature.whois.WhoIsMessages;
import com.eternalcode.core.litecommand.argument.messages.ArgumentMessages;

public interface Translation {

    interface Format {
        String enable();
        String disable();
    }

    Language getLanguage();

    Format format();

    ArgumentMessages argument();

    ChatMessages chat();

    AdminChatMessages adminChat();

    MsgMessages msg();

    BroadcastMessages broadcast();

    AutoMessageMessages autoMessage();

    JoinMessage join();

    QuitMessage quit();

    MotdMessages motd();

    DeathMessages deathMessage();

    AfkMessages afk();

    IgnoreMessages ignore();

    TeleportMessages teleport();

    TeleportRequestMessages tpa();

    TeleportToRandomPlayerMessages teleportToRandomPlayer();

    RandomTeleportMessages randomTeleport();

    TeleportOfflineMessages teleportToOfflinePlayer();

    HomeMessages home();

    WarpMessages warp();

    SpawnMessages spawn();

    NearMessages near();

    HealMessages heal();

    FeedMessages feed();

    FlyMessages fly();

    SpeedMessages speed();

    GodModeMessages godmode();

    FreezeMessages freeze();

    VanishMessages vanish();

    GameModeMessages gamemode();

    TimeAndWeatherMessages timeAndWeather();

    PingMessages ping();

    OnlineMessages online();

    WhoIsMessages whois();

    SeenMessages seen();

    PlaytimeMessages playtime();

    GiveMessages give();

    ClearMessages clear();

    DisposalMessages disposal();

    ContainerMessages container();

    SetSlotMessages setSlot();

    ItemEditMessages itemEdit();

    EnchantMessages enchant();

    RepairMessages repair();

    SkullMessages skull();

    SignEditorMessages signEditor();

    PowertoolMessages powertool();

    KillMessages kill();

    BurnMessages burn();

    ButcherMessages butcher();

    JailMessages jailSection();

    SudoMessages sudo();

    HelpOpSection helpOp();

    ElderGuardianMessages elderGuardian();

    DemoScreenMessages demoScreen();

    EndScreenMessages endScreen();
}
