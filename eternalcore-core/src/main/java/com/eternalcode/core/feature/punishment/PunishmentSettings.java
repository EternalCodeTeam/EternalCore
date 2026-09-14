package com.eternalcode.core.feature.punishment;

import java.time.Duration;
import java.util.List;

public interface PunishmentSettings {

    Duration defaultBanDuration();

    Duration defaultMuteDuration();

    int minReasonLength();

    int maxReasonLength();

    String permanentLabel();

    List<String> banKickScreen();

    List<String> kickScreen();
}
