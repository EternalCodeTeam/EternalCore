package com.eternalcode.core.feature.punishment;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public interface PunishmentSettings {

    Duration defaultBanDuration();

    Duration defaultMuteDuration();

    boolean messageWhenBanned();

    Duration messageWhenBannedCooldown();

    boolean reasonLegthEnabled();

    int minReasonLength();

    int maxReasonLength();

    String permanentLabel();

    int historyPageSize();

    Map<Integer, String> warnEscalations();

    List<String> banKickScreen();

    List<String> banIpKickScreen();

    List<String> kickScreen();
}
