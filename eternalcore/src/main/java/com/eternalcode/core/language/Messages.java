package com.eternalcode.core.language;

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
    PrivateMessage privateMessage();

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
        String actionBarMessage();
        String cancel();
        String teleported();
        String teleporting();
        String haveTeleport();
        String successfullyTeleported();
        String successfullyTeleportedPlayer();
        String tposMessage();
        String tposByMessage();
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

    interface PrivateMessage {
        String noReply();
        String sendFormat();
        String receiveFormat();
        String socialSpyFormat();
        String socialSpyEnable();
        String socialSpyDisable();
    }

    interface OtherMessages {
        String alertMessagePrefix();
        String clearMessage();
        String clearByMessage();
        String disposalTitle();
        String foodMessage();
        String foodOtherMessage();
        String healMessage();
        String healedMessage();
        String nullHatMessage();
        String repairMessage();
        String skullMessage();
        String killSelf();
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
        String nameMessage();
        String enchantedMessage();
        List<String> whoisCommand();
        String languageChanged();
    }
}

