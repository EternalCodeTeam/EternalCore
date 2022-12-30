package com.eternalcode.core.language;

import com.eternalcode.core.chat.notification.Notification;

import java.util.List;

public interface Messages {

    Language getLanguage();

    ArgumentSection argument();
    Format format();
    HelpOpSection helpOp();
    AdminChatSection adminChat();
    ChatSection chat();
    TeleportSection teleport();
    TpaSection tpa();
    EventMessagesSection eventMessages();
    WarpSection warp();
    HomeSection home();
    PrivateMessageSection privateMessage();
    AfkSection afk();
    InventoryMessagesSection inventory();
    PlayerMessagesSection player();
    SpawnMessagesSection spawn();
    ItemMessagesSection item();

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

    interface Format {
        String enable();
        String disable();
    }

    interface HelpOpSection {
        Notification format();
        Notification send();
        Notification coolDown();
    }

    interface AdminChatSection {
        Notification format();
    }

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

    interface WarpSection {
        Notification warpAlreadyExists();
        Notification notExist();
        Notification create();
        Notification remove();
    }

    interface HomeSection {
        Notification notExist();
        Notification create();
        Notification delete();
        Notification limit();
        Notification overrideHomeLocation();
    }

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

    interface PrivateMessageSection {
        Notification noReply();
        Notification privateMessageYouToTarget();
        Notification privateMessageTargetToYou();

        Notification socialSpyMessage();
        Notification socialSpyEnable();
        Notification socialSpyDisable();

        Notification ignorePlayer();
        Notification unIgnorePlayer();
    }

    interface AfkSection {
        Notification afkOn();
        Notification afkOff();
    }

    interface EventMessagesSection {
        List<Notification> deathMessage();
        List<Notification> joinMessage();
        List<Notification> quitMessage();
        List<Notification> firstJoinMessage();

        Notification welcomeTitle();
        Notification welcomeSubtitle();
    }

    interface InventoryMessagesSection {
        Notification inventoryClearMessage();
        Notification inventoryClearMessageBy();
        Notification cantOpenYourInventory();
        String disposalTitle();
    }

    interface PlayerMessagesSection {
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

        // language
        Notification languageChanged();

        // whois
        List<String> whoisCommand();
    }

    interface SpawnMessagesSection {
        // spawn
        Notification spawnSet();
        Notification spawnNoSet();

        Notification spawnTeleportedBy();
        Notification spawnTeleportedOther();
    }

    interface ItemMessagesSection {
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
}

