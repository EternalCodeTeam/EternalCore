package com.eternalcode.core.feature.punishment;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.time.Duration;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PunishmentConfig extends OkaeriConfig implements PunishmentSettings {

    @Comment("# Default ban duration used when no duration is specified in the command")
    public Duration defaultBanDuration = Duration.ofDays(3);

    @Comment("# Default mute duration used when no duration is specified in the command")
    public Duration defaultMuteDuration = Duration.ofHours(1);

    @Comment("# Minimum length of a punishment reason")
    public int minReasonLength = 3;

    @Comment("# Maximum length of a punishment reason")
    public int maxReasonLength = 255;

    @Comment("# Label shown instead of an expiration date for permanent punishments")
    public String permanentLabel = "N/A";

    @Comment({ " ", "# Screen shown to a player when they get banned. {OPERATOR}, {REASON}, {EXPIRES} available." })
    public List<String> banKickScreen = List.of(
        "<red><bold>Zostałeś zbanowany!",
        " ",
        "<white>Powód: <gray>{REASON}",
        "<white>Kto: <gray>{OPERATOR}",
        "<white>Wygasa: <gray>{EXPIRES}"
    );

    @Comment({ " ", "# Screen shown to a player when they get banned. {OPERATOR}, {REASON}, {EXPIRES} available." })
    public List<String> banIpKickScreen = List.of(
        "<red><bold>Zostałeś zbanowany na IP!",
        " ",
        "<white>Powód: <gray>{REASON}",
        "<white>Kto: <gray>{OPERATOR}",
        "<white>Wygasa: <gray>{EXPIRES}"
    );

    @Comment({ " ", "# Screen shown to a player when they get kicked. {OPERATOR}, {REASON} available." })
    public List<String> kickScreen = List.of(
        "<red><bold>Zostałeś wyrzucony z serwera!",
        " ",
        "<white>Powód: <gray>{REASON}",
        "<white>Kto: <gray>{OPERATOR}"
    );
}
