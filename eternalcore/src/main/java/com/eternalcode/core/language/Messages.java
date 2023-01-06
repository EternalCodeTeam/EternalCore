package com.eternalcode.core.language;

import com.eternalcode.core.chat.notification.Notification;

import java.util.List;

public interface Messages {

    Language getLanguage();

    // argument section
    ArgumentSection argument();

    interface ArgumentSection {
        Notification permissionMessage();
        Notification usageMessage();
        Notification usageMessageHead();
        Notification usageMessageEntry();
        Notification offlinePlayer();
        Notification onlyPlayer();
        Notification numberBiggerThanOrEqualZero();
        Notification noItem();
        Notification noArgument();
        Notification noDamaged();
        Notification noDamagedItems();
        Notification noEnchantment();
        Notification noValidEnchantmentLevel();
    }

    // format section
    Format format();

    interface Format {
        String enable();
        String disable();
    }

    // HelpOp Section
    HelpOpSection helpOp();

    interface HelpOpSection {
        Notification format();
        Notification send();
        Notification coolDown();
    }

    // AdminChat Section
    AdminChatSection adminChat();

    interface AdminChatSection {
        Notification format();
    }

    // Teleport Section
    TeleportSection teleport();

    interface TeleportSection {
        // teleport
        Notification teleportedToPlayer();
        Notification teleportedPlayerToPlayer();

        // Task
        Notification teleportTimerFormat();
        Notification teleported();
        Notification teleporting();
        Notification teleportTaskCanceled();
        Notification teleportTaskAlreadyExist();

        // Coordinates XYZ
        Notification teleportedToCoordinates();
        Notification teleportedSpecifiedPlayerToCoordinates();

        // Back
        Notification teleportedToLastLocation();
        Notification teleportedSpecifiedPlayerLastLocation();
        Notification lastLocationNoExist();
    }

    // Chat Section
    ChatSection chat();

    interface ChatSection {
        Notification disabled();
        Notification enabled();
        Notification cleared();
        Notification alreadyDisabled();
        Notification alreadyEnabled();
        Notification slowModeSet();
        Notification slowMode();
        Notification disabledChatInfo();
        Notification noCommand();
        String alertMessageFormat();
    }

    // Warp Section
    WarpSection warp();

    interface WarpSection {
        Notification warpAlreadyExists();
        Notification notExist();
        Notification create();
        Notification remove();
    }

    // Home section
    HomeSection home();

    interface HomeSection {
        Notification notExist();
        Notification create();
        Notification delete();
        Notification limit();
        Notification overrideHomeLocation();
    }

    // tpa section
    TpaSection tpa();

    interface TpaSection {
        Notification tpaSelfMessage();
        Notification tpaAlreadySentMessage();
        Notification tpaSentMessage();
        List<Notification> tpaReceivedMessage();

        Notification tpaDenyNoRequestMessage();
        Notification tpaDenyDoneMessage();
        Notification tpaDenyReceivedMessage();
        Notification tpaDenyAllDenied();

        Notification tpaAcceptMessage();
        Notification tpaAcceptNoRequestMessage();
        Notification tpaAcceptReceivedMessage();
        Notification tpaAcceptAllAccepted();
    }

    // private section
    PrivateChatSection privateChat();

    interface PrivateChatSection {
        Notification noReply();
        Notification privateMessageYouToTarget();
        Notification privateMessageTargetToYou();

        Notification socialSpyMessage();
        Notification socialSpyEnable();
        Notification socialSpyDisable();

        Notification ignorePlayer();
        Notification unIgnorePlayer();
        Notification alreadyIgnorePlayer();
        Notification notIgnorePlayer();
        Notification cantIgnoreYourself();
        Notification cantUnIgnoreYourself();

    }

    // afk section
    AfkSection afk();

    interface AfkSection {
        Notification afkOn();
        Notification afkOff();
    }

    // event section
    EventSection event();

    interface EventSection {
        List<Notification> deathMessage();
        List<Notification> joinMessage();
        List<Notification> quitMessage();
        List<Notification> firstJoinMessage();

        Notification welcomeTitle();
        Notification welcomeSubtitle();
    }

    // inventory section
    InventorySection inventory();

    interface InventorySection {
        Notification inventoryClearMessage();
        Notification inventoryClearMessageBy();
        Notification cantOpenYourInventory();
        String disposalTitle();
    }

    // player section
    PlayerSection player();

    interface PlayerSection {
        // feed
        Notification feedMessage();
        Notification feedMessageBy();

        // heal
        Notification healMessage();
        Notification healMessageBy();

        // kill
        Notification killedMessage();

        // speed
        Notification speedBetweenZeroAndTen();
        Notification speedSet();
        Notification speedSetBy();

        // godmode
        Notification godMessage();
        Notification godSetMessage();

        // fly
        Notification flyMessage();
        Notification flySetMessage();

        // ping
        Notification pingMessage();
        Notification pingOtherMessage();

        // gamemode
        Notification gameModeNotCorrect();
        Notification gameModeMessage();
        Notification gameModeSetMessage();

        // online
        Notification onlineMessage();
        Notification listMessage();

        // whois
        List<String> whoisCommand();
    }

    // spawn section
    SpawnSection spawn();

    interface SpawnSection {
        // spawn
        Notification spawnSet();
        Notification spawnNoSet();

        Notification spawnTeleportedBy();
        Notification spawnTeleportedOther();
    }

    // item section
    ItemSection item();

    interface ItemSection {
        // item name & lore
        Notification itemClearNameMessage();
        Notification itemClearLoreMessage();

        Notification itemChangeNameMessage();
        Notification itemChangeLoreMessage();

        // item flags
        Notification itemFlagRemovedMessage();
        Notification itemFlagAddedMessage();
        Notification itemFlagClearedMessage();

        // give
        Notification giveReceived();
        Notification giveGiven();

        // others
        Notification repairMessage();
        Notification skullMessage();
        Notification enchantedMessage();
    }

    // time and weather
    TimeAndWeatherSection timeAndWeather();

    interface TimeAndWeatherSection {
        Notification timeSetDay();
        Notification timeSetNight();

        Notification timeSet();
        Notification timeAdd();

        Notification weatherSetRain();
        Notification weatherSetSun();
        Notification weatherSetThunder();
    }


    // language section
    LanguageSection language();

    interface LanguageSection {
        Notification languageChanged();
    }
}
