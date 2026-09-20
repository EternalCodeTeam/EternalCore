package com.eternalcode.core.feature.punishment;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import org.bukkit.Material;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PunishmentConfig extends OkaeriConfig implements PunishmentSettings {

    @Comment("# Should muted players be blocked from writing text on signs?")
    public boolean blockUsingSignOnMute = true;

    @Comment("# Should muted players be blocked from using messaging commands (e.g. /msg, /tell)?")
    public boolean blockCommandsOnMute = true;

    @Comment({ " ", "# Commands (without leading slash, without arguments) blocked while muted. Aliases and plugin-prefixed forms (e.g. \"eternalcore:msg\") are matched by their base label." })
    public List<String> blockedMuteCommands = List.of(
        "msg",
        "tell",
        "w",
        "whisper",
        "r",
        "reply",
        "mail"
    );

    @Comment("# Should a message be sent when a banned player tries to join the server?")
    public boolean messageWhenBanned = true;

    @Comment("# Message delay when a player is obsessively trying to log in")
    public Duration messageWhenBannedCooldown = Duration.ofSeconds(60);

    @Comment("# Checking length of reason?")
    public boolean reasonLegthEnabled = true;

    @Comment("# Minimum length of a punishment reason")
    public int minReasonLength = 3;

    @Comment("# Maximum length of a punishment reason")
    public int maxReasonLength = 255;

    @Comment("# Label shown instead of an expiration date for permanent punishments")
    public String permanentLabel = "N/A";

    @Comment({ " ", "# Screen shown to a player when they get banned. {OPERATOR}, {REASON}, {EXPIRES} available." })
    public List<String> banKickScreen = List.of(
        "<red><bold>You have been banned!!",
        " ",
        "<white>Reason: <gray>{REASON}",
        "<white>Who: <gray>{OPERATOR}",
        "<white>Expires: <gray>{EXPIRES}"
    );

    @Comment({ " ", "# Screen shown to a player when they get banned. {OPERATOR}, {REASON}, {EXPIRES} available." })
    public List<String> banIpKickScreen = List.of(
        "<red><bold>You have been banned! (IP)",
        " ",
        "<white>Reason: <gray>{REASON}",
        "<white>Who: <gray>{OPERATOR}",
        "<white>Expires: <gray>{EXPIRES}"
    );

    @Comment({ " ", "# Screen shown to a player when they get kicked. {OPERATOR}, {REASON} available." })
    public List<String> kickScreen = List.of(
        "<red><bold>You have been kicked!",
        " ",
        "<white>Reason: <gray>{REASON}",
        "<white>Who: <gray>{OPERATOR}"
    );

    @Comment({ " ", "# Automatic punishment applied when a player reaches a given number of warns." })
    @Comment({ " ", "# Format: \"ACTION:DURATION\" - ACTION is KICK, MUTE or BAN. DURATION is e.g. 1d, 7d, or \"permanent\"." })
    public Map<Integer, String> warnEscalations = Map.of(
        3, "MUTE:1d",
        5, "MUTE:7d",
        10, "BAN:permanent"
    );

    @Comment({ " ", "# Reason set on the automatic punishment applied by warnEscalations. {COUNT} available." })
    public String warnEscalationReason = "Automatic punishment - reached {COUNT} warns";
}
