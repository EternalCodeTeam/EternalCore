package com.eternalcode.core.feature.punishment;

import com.eternalcode.core.feature.punishment.gui.PunishmentGuiConfig;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

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

    @Comment("# Label shown instead of an expiration date for permanent punishments")
    public String permanentLabel = "Eternal";

    @Comment("# Reason used when a staff member does not provide one (e.g. /kick Player)")
    public String defaultReason = "None";

    @Comment({ " ", "# Screen shown to a player when they get banned. {OPERATOR}, {REASON}, {EXPIRES} available." })
    public List<String> banKickScreen = List.of(
        "<red>✘ <dark_red><bold>You have been banned!",
        " ",
        "<red>► <white>Reason: <red>{REASON}",
        "<red>► <white>Who: <red>{OPERATOR}",
        "<red>► <white>Expires: <red>{EXPIRES}"
    );

    @Comment({ " ", "# Screen shown to a player when they get IP banned. {OPERATOR}, {REASON}, {EXPIRES} available." })
    public List<String> banIpKickScreen = List.of(
        "<red>✘ <dark_red><bold>You have been banned! (IP)",
        " ",
        "<red>► <white>Reason: <red>{REASON}",
        "<red>► <white>Who: <red>{OPERATOR}",
        "<red>► <white>Expires: <red>{EXPIRES}"
    );

    @Comment({ " ", "# Screen shown to a player when they get kicked. {OPERATOR}, {REASON} available." })
    public List<String> kickScreen = List.of(
        "<red>✘ <dark_red><bold>You have been kicked!",
        " ",
        "<red>► <white>Reason: <red>{REASON}",
        "<red>► <white>Who: <red>{OPERATOR}"
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

    @Comment({ " ", "# Punishment history GUI" })
    public PunishmentGuiConfig gui = new PunishmentGuiConfig();
}
