package com.eternalcode.core.ip;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.HexFormat;
import java.util.Objects;

@Service
class IpCryptoServiceImpl implements IpCryptoService {

    private static final String AES_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final int GCM_IV_LENGTH_BYTES = 12;
    private static final int GCM_TAG_LENGTH_BITS = 128;

    private final SecretKeyProvider secretKeyProvider;

    @Inject
    IpCryptoServiceImpl(SecretKeyProvider secretKeyProvider) {
        this.secretKeyProvider = secretKeyProvider;
    }

    @Override
    public EncryptedValue encrypt(String plainIp) {
        Objects.requireNonNull(plainIp, "plainIp cannot be null");

        try {
            byte[] iv = new byte[GCM_IV_LENGTH_BYTES];
            new SecureRandom().nextBytes(iv);

            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, this.aesKeySpec(), new GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv));

            byte[] ciphertext = cipher.doFinal(plainIp.getBytes(StandardCharsets.UTF_8));

            return new EncryptedValue(ciphertext, iv);
        }
        catch (GeneralSecurityException exception) {
            throw new IllegalStateException("Failed to encrypt value", exception);
        }
    }

    @Override
    public String decrypt(EncryptedValue encryptedValue) {
        Objects.requireNonNull(encryptedValue, "encryptedValue cannot be null");

        try {
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, this.aesKeySpec(), new GCMParameterSpec(GCM_TAG_LENGTH_BITS, encryptedValue.iv()));

            byte[] plaintext = cipher.doFinal(encryptedValue.ciphertext());

            return new String(plaintext, StandardCharsets.UTF_8);
        }
        catch (GeneralSecurityException exception) {
            throw new IllegalStateException("Failed to decrypt value - wrong key or corrupted data", exception);
        }
    }

    @Override
    public String hash(String plainIp) {
        Objects.requireNonNull(plainIp, "plainIp cannot be null");

        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(new SecretKeySpec(this.secretKeyProvider.hmacKey(), HMAC_ALGORITHM));

            byte[] digest = mac.doFinal(plainIp.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(digest);
        }
        catch (GeneralSecurityException exception) {
            throw new IllegalStateException("Failed to compute lookup hash", exception);
        }
    }

    private SecretKeySpec aesKeySpec() {
        return new SecretKeySpec(this.secretKeyProvider.aesKey(), "AES");
    }
}
