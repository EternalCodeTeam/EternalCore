package com.eternalcode.core.translation;

import com.eternalcode.core.feature.warp.config.WarpInventoryItem;
import com.eternalcode.core.feature.language.Language;
import com.eternalcode.core.notice.Notice;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface Translation {

    Language getLanguage();

    // argument section
    ArgumentSection argument();

    interface ArgumentSection {
        Notice permissionMessage();
        Notice usageMessage();
        Notice usageMessageHead();
        Notice usageMessageEntry();
        Notice offlinePlayer();
        Notice onlyPlayer();
        Notice numberBiggerThanOrEqualZero();
        Notice noItem();
        Notice noArgument();
        Notice noDamaged();
        Notice noDamagedItems();
        Notice noEnchantment();
        Notice noValidEnchantmentLevel();
        Notice invalidTimeFormat();
        Notice worldDoesntExist();
        Notice youMustGiveWorldName();
        Notice incorrectNumberOfChunks();
        Notice incorrectLocation();
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
        Notice format();
        Notice send();
        Notice helpOpDelay();
    }

    // AdminChat Section
    AdminChatSection adminChat();

    interface AdminChatSection {
        Notice format();
    }

    // Teleport Section
    TeleportSection teleport();

    interface TeleportSection {
        // teleport
        Notice teleportedToPlayer();
        Notice teleportedPlayerToPlayer();
        Notice teleportedToHighestBlock();

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

        // teleport to random player command
        Notice randomPlayerNotFound();
        Notice teleportedToRandomPlayer();
    }

    // Random Teleport Section
    RandomTeleportSection randomTeleport();

    interface RandomTeleportSection {
        Notice randomTeleportStarted();
        Notice randomTeleportFailed();
        Notice teleportedToRandomLocation();
        Notice teleportedSpecifiedPlayerToRandomLocation();
    }

    // Chat Section
    ChatSection chat();

    interface ChatSection {
        Notice disabled();
        Notice enabled();
        Notice cleared();
        Notice alreadyDisabled();
        Notice alreadyEnabled();
        Notice slowModeSet();
        Notice slowMode();
        Notice disabledChatInfo();
        Notice commandNotFound();
        String alertMessageFormat();
        Notice tellrawInfo();
        Notice tellrawAllInfo();
    }

    // Warp Section
    WarpSection warp();

    interface WarpSection {
        Notice warpAlreadyExists();
        Notice notExist();
        Notice create();
        Notice remove();
        Notice available();

        WarpInventorySection warpInventory();

        interface WarpInventorySection {

            String title();
            int rows();

            Map<String, WarpInventoryItem> items();

            BorderSection border();

            interface BorderSection {
                boolean enabled();

                Material material();

                FillType fillType();

                String name();

                List<String> lore();

                enum FillType {
                    TOP, BOTTOM, BORDER, ALL
                }
            }
        }
    }

    // Home section
    HomeSection home();

    interface HomeSection {
        Notice enterName();
        Notice notExist();
        Notice create();
        Notice delete();
        Notice limit();
        Notice overrideHomeLocation();
        String noHomesOwned();
    }

    // tpa section
    TpaSection tpa();

    interface TpaSection {
        Notice tpaSelfMessage();
        Notice tpaAlreadySentMessage();
        Notice tpaSentMessage();
        Notice tpaReceivedMessage();

        Notice tpaDenyNoRequestMessage();
        Notice tpaDenyDoneMessage();
        Notice tpaDenyReceivedMessage();
        Notice tpaDenyAllDenied();

        Notice tpaAcceptMessage();
        Notice tpaAcceptNoRequestMessage();
        Notice tpaAcceptReceivedMessage();
        Notice tpaAcceptAllAccepted();
    }

    // private section
    PrivateChatSection privateChat();

    interface PrivateChatSection {
        Notice noReply();
        Notice privateMessageYouToTarget();
        Notice privateMessageTargetToYou();

        Notice socialSpyMessage();
        Notice socialSpyEnable();
        Notice socialSpyDisable();

        Notice ignorePlayer();
        Notice ignoreAll();
        Notice unIgnorePlayer();
        Notice unIgnoreAll();
        Notice alreadyIgnorePlayer();
        Notice notIgnorePlayer();
        Notice cantIgnoreYourself();
        Notice cantUnIgnoreYourself();
    }

    // afk section
    AfkSection afk();

    interface AfkSection {
        Notice afkOn();
        Notice afkOff();
        Notice afkDelay();
    }

    // event section
    EventSection event();

    interface EventSection {
        List<Notice> deathMessage();
        List<Notice> unknownDeathCause();
        List<Notice> joinMessage();
        List<Notice> quitMessage();
        List<Notice> firstJoinMessage();

        Map<EntityDamageEvent.DamageCause, List<Notice>> deathMessageByDamageCause();

        Notice welcome();
    }

    // inventory section
    InventorySection inventory();

    interface InventorySection {
        Notice inventoryClearMessage();
        Notice inventoryClearMessageBy();
        Notice cantOpenYourInventory();
        String disposalTitle();
    }

    // player section
    PlayerSection player();

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
        Notice godMessage();
        Notice godSetMessage();

        // fly
        Notice flyMessage();
        Notice flySetMessage();

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

    // spawn section
    SpawnSection spawn();

    interface SpawnSection {
        // spawn
        Notice spawnSet();
        Notice spawnNoSet();

        Notice spawnTeleportedBy();
        Notice spawnTeleportedOther();
    }

    // item section
    ItemSection item();

    interface ItemSection {
        // item name & lore
        Notice itemClearNameMessage();
        Notice itemClearLoreMessage();

        Notice itemChangeNameMessage();
        Notice itemChangeLoreMessage();

        // item flags
        Notice itemFlagRemovedMessage();
        Notice itemFlagAddedMessage();
        Notice itemFlagClearedMessage();

        // give
        Notice giveReceived();
        Notice giveGiven();

        Notice giveNotItem();

        // others
        Notice repairMessage();
        Notice repairAllMessage();
        Notice skullMessage();
        Notice enchantedMessage();
    }

    // time and weather
    TimeAndWeatherSection timeAndWeather();

    interface TimeAndWeatherSection {
        Notice timeSetDay();
        Notice timeSetNight();

        Notice timeSet();
        Notice timeAdd();

        Notice weatherSetRain();
        Notice weatherSetSun();
        Notice weatherSetThunder();
    }

    // language section
    LanguageSection language();

    interface LanguageSection {
        Notice languageChanged();
    }

    // container section
    ContainerSection container();

    interface ContainerSection {
        Notice genericContainerOpened();
        Notice genericContainerOpenedBy();
        Notice genericContainerOpenedFor();
    }

    AutoMessageSection autoMessage();

    interface AutoMessageSection {
        Collection<Notice> messages();

        Notice enabled();
        Notice disabled();
    }

}
