package com.eternalcode.core.feature.punishment;

import com.eternalcode.core.feature.punishment.gui.PunishmentGuiSettings;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public interface PunishmentSettings {

    boolean blockUsingSignOnMute();

    boolean blockCommandsOnMute();

    List<String> blockedMuteCommands();

    boolean messageWhenBanned();

    Duration messageWhenBannedCooldown();

    boolean reasonLegthEnabled();

    int minReasonLength();

    int maxReasonLength();

    String permanentLabel();

    List<String> banKickScreen();

    List<String> banIpKickScreen();

    List<String> kickScreen();

    Map<Integer, String> warnEscalations();

    String warnEscalationReason();

    PunishmentGuiSettings gui();
}
