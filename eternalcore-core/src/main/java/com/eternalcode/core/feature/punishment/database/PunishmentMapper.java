package com.eternalcode.core.feature.punishment.database;

import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.Revocation;
import com.eternalcode.core.feature.punishment.ban.Ban;
import com.eternalcode.core.feature.punishment.PunishmentKind;
import com.eternalcode.core.feature.punishment.ipban.IpBan;
import com.eternalcode.core.feature.punishment.kick.Kick;
import com.eternalcode.core.feature.punishment.mute.Mute;
import com.eternalcode.core.feature.punishment.warn.Warn;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.ip.EncryptedValue;
import com.eternalcode.core.ip.IpCryptoService;
import java.util.Base64;

/**
 * Translates table rows (with their JSON details) to domain objects and back.
 */
@Service
public class PunishmentMapper {

    private static final String IP_CIPHERTEXT_KEY = "ipCiphertext";
    private static final String IP_IV_KEY = "ipIv";
    private static final String IP_HASH_KEY = "ipHash";

    private static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder();
    private static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();

    private final IpCryptoService ipCryptoService;

    @Inject
    public PunishmentMapper(IpCryptoService ipCryptoService) {
        this.ipCryptoService = ipCryptoService;
    }

    public PunishmentTable banToRow(Ban ban) {
        return PunishmentTable.of(PunishmentKind.BAN, ban, PunishmentDetails.empty());
    }

    public Ban rowToBan(PunishmentTable row, Revocation revocation) {
        this.requireKind(row, PunishmentKind.BAN);

        return new Ban(row.id(), row.target(), row.operator(), row.reason(), row.createdAt(), row.expiresAt(), revocation);
    }

    public PunishmentTable muteToRow(Mute mute) {
        return PunishmentTable.of(PunishmentKind.MUTE, mute, PunishmentDetails.empty());
    }

    public Mute rowToMute(PunishmentTable row, Revocation revocation) {
        this.requireKind(row, PunishmentKind.MUTE);

        return new Mute(row.id(), row.target(), row.operator(), row.reason(), row.createdAt(), row.expiresAt(), revocation);
    }

    public PunishmentTable warnToRow(Warn warn) {
        return PunishmentTable.of(PunishmentKind.WARN, warn, PunishmentDetails.empty());
    }

    public Warn rowToWarn(PunishmentTable row, Revocation revocation) {
        this.requireKind(row, PunishmentKind.WARN);

        return new Warn(row.id(), row.target(), row.operator(), row.reason(), row.createdAt(), row.expiresAt(), revocation);
    }

    public PunishmentTable kickToRow(Kick kick) {
        PunishmentKind kind = kick.massKick() ? PunishmentKind.KICK_ALL : PunishmentKind.KICK;

        return PunishmentTable.of(kind, kick, PunishmentDetails.empty());
    }

    public Kick rowToKick(PunishmentTable row) {
        boolean massKick = row.kind() == PunishmentKind.KICK_ALL;

        if (!massKick && row.kind() != PunishmentKind.KICK) {
            throw new IllegalStateException("Punishment " + row.id() + " is " + row.kind() + ", not a kick");
        }

        return new Kick(row.id(), row.target(), row.operator(), row.reason(), row.createdAt(), massKick);
    }

    public PunishmentTable ipBanToRow(IpBan ipBan) {
        EncryptedValue encryptedIp = this.ipCryptoService.encrypt(ipBan.ip());

        PunishmentDetails details = PunishmentDetails.empty()
            .with(IP_CIPHERTEXT_KEY, BASE64_ENCODER.encodeToString(encryptedIp.ciphertext()))
            .with(IP_IV_KEY, BASE64_ENCODER.encodeToString(encryptedIp.iv()))
            .with(IP_HASH_KEY, this.ipCryptoService.hash(ipBan.ip()));

        return PunishmentTable.of(PunishmentKind.IP_BAN, ipBan, details);
    }

    public IpBan rowToIpBan(PunishmentTable row, Revocation revocation) {
        this.requireKind(row, PunishmentKind.IP_BAN);

        PunishmentDetails details = row.details();
        EncryptedValue encryptedIp = new EncryptedValue(
            BASE64_DECODER.decode(details.require(IP_CIPHERTEXT_KEY)),
            BASE64_DECODER.decode(details.require(IP_IV_KEY))
        );

        return new IpBan(
            row.id(),
            this.ipCryptoService.decrypt(encryptedIp),
            row.target(),
            row.operator(),
            row.reason(),
            row.createdAt(),
            row.expiresAt(),
            revocation
        );
    }

    /**
     * Decodes a row of any non-revocation kind.
     */
    public Punishment rowToPunishment(PunishmentTable row, Revocation revocation) {
        return switch (row.kind()) {
            case BAN -> this.rowToBan(row, revocation);
            case IP_BAN -> this.rowToIpBan(row, revocation);
            case MUTE -> this.rowToMute(row, revocation);
            case WARN -> this.rowToWarn(row, revocation);
            case KICK, KICK_ALL -> this.rowToKick(row);
            default -> throw new IllegalStateException("Punishment " + row.id() + " is a " + row.kind() + " row, not a punishment");
        };
    }

    private void requireKind(PunishmentTable row, PunishmentKind expected) {
        if (row.kind() != expected) {
            throw new IllegalStateException("Punishment " + row.id() + " is " + row.kind() + ", expected " + expected);
        }
    }
}
