package com.eternalcode.core.language;

import com.eternalcode.core.chat.notification.Notification;
import com.eternalcode.core.configuration.ConfigWithResource;

import java.util.List;

public interface Messages extends ConfigWithResource {

    Language getLanguage();

    ArgumentSection argument();
    Format format();
    HelpOpSection helpOp();
    AdminChatSection adminChat();
    ChatSection chat();
    TeleportSection teleport();
    TpaSection tpa();
    OtherMessages other();
    WarpSection warp();
    HomeSection home();
    PrivateMessageSection privateMessage();
    AfkSection afk();

    interface ArgumentSection {
        String permissionMessage();
        String usageMessage();
        String offlinePlayer();
        String onlyPlayer();
        String notNumber();
        String numberBiggerThanOrEqualZero();
        String noItem();
        String noMaterial();
        String noArgument();
        String noDamaged();
        String noDamagedItems();
        String noEnchantment();
        String noValidEnchantmentLevel();
    }

    interface Format {
        String enable();
        String disable();
    }

    interface HelpOpSection {
        String format();
        String send();
        String coolDown();
    }

    interface AdminChatSection {
        String format();
    }

    interface TeleportSection {
        // teleport
        String teleportedToPlayer();
        String teleportedPlayerToPlayer();

        // Task
        Notification teleportTimerFormat();
        String teleported();
        String teleporting();
        String teleportTaskCanceled();
        String teleportTaskAlreadyExist();

        // Coordinates XYZ
        String teleportedToCoordinates();
        String teleportedSpecifiedPlayerToCoordinates();

        // Back
        String teleportedToLastLocation();
        String teleportedSpecifiedPlayerLastLocation();
        String lastLocationNoExist();
    }

    interface ChatSection {
        String disabled();
        String enabled();
        String cleared();
        String alreadyDisabled();
        String alreadyEnabled();
        String slowModeSet();
        String slowMode();
        String disabledChatInfo();
        String noCommand();
    }

    interface WarpSection {
        String availableList();
        String notExist();
        String create();
        String remove();
        String noPermission();
        String disabled();
    }

    interface HomeSection {
        String notExist();
        String create();
        String delete();
    }

    interface TpaSection {
        String tpaSelfMessage();
        String tpaAlreadySentMessage();
        String tpaSentMessage();
        String tpaReceivedMessage();

        String tpaDenyNoRequestMessage();
        String tpaDenyNoRequestMessageAll();
        String tpaDenyDoneMessage();
        String tpaDenyReceivedMessage();
        String tpaDenyAllDenied();

        String tpaAcceptMessage();
        String tpaAcceptNoRequestMessage();
        String tpaAcceptNoRequestMessageAll();
        String tpaAcceptReceivedMessage();
        String tpaAcceptAllAccepted();
    }

    interface PrivateMessageSection {
        String noReply();
        String privateMessageYouToTarget();
        String privateMessageTargetToYou();

        String socialSpyMessage();
        String socialSpyEnable();
        String socialSpyDisable();

        String ignorePlayer();
        String unIgnorePlayer();
    }

    interface AfkSection {
        String afkOn();
        String afkOff();
    }

    interface OtherMessages {
        String alertMessagePrefix();
        String clearMessage(); // ?
        String clearByMessage(); // ?
        String disposalTitle();
        String foodMessage();
        String foodOtherMessage();
        String healMessage();
        String healedMessage();
        String nullHatMessage();
        String repairMessage();
        String skullMessage();
        String killedMessage();
        String speedBetweenZeroAndTen();
        String speedSet();
        String speedSetBy();
        String godMessage();
        String godSetMessage();
        String flyMessage();
        String flySetMessage();
        String giveReceived();
        String giveGiven();
        String spawnSet();
        String spawnNoSet();
        String spawnTeleportedBy();
        String spawnTeleportedOther();
        String gameModeNotCorrect();
        String gameModeMessage();
        String gameModeSetMessage();
        String pingMessage();
        String pingOtherMessage();
        String onlineMessage();
        String listMessage();

        String itemChangeNameMessage();
        String itemClearNameMessage();
        String itemChangeLoreMessage();
        String itemClearLoreMessage();
        String itemFlagRemovedMessage();
        String itemFlagAddedMessage();
        String itemFlagClearedMessage();

        String enchantedMessage();
        List<String> whoisCommand();
        String languageChanged();

    }
}

