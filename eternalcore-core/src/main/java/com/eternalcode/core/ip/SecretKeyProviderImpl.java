package com.eternalcode.core.ip;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

@Service
class SecretKeyProviderImpl implements SecretKeyProvider {

    private static final String SECRET_FILE_NAME = "secret.key";
    private static final int MASTER_SECRET_LENGTH = 32;
    private static final int DERIVED_KEY_LENGTH = 32;

    private static final byte[] AES_INFO = "eternalcore-ip-aes".getBytes(StandardCharsets.UTF_8);
    private static final byte[] HMAC_INFO = "eternalcore-ip-hmac".getBytes(StandardCharsets.UTF_8);

    private final byte[] aesKey;
    private final byte[] hmacKey;

    @Inject
    SecretKeyProviderImpl(File dataFolder) {
        Objects.requireNonNull(dataFolder, "dataFolder cannot be null");

        byte[] masterSecret = this.loadOrGenerateMasterSecret(new File(dataFolder, SECRET_FILE_NAME));

        this.aesKey = Hkdf.deriveKey(masterSecret, AES_INFO, DERIVED_KEY_LENGTH);
        this.hmacKey = Hkdf.deriveKey(masterSecret, HMAC_INFO, DERIVED_KEY_LENGTH);
    }

    @Override
    public byte[] aesKey() {
        return Arrays.copyOf(this.aesKey, this.aesKey.length);
    }

    @Override
    public byte[] hmacKey() {
        return Arrays.copyOf(this.hmacKey, this.hmacKey.length);
    }

    private byte[] loadOrGenerateMasterSecret(File secretFile) {
        if (secretFile.exists()) {
            return this.readMasterSecret(secretFile);
        }

        byte[] generated = this.generateMasterSecret();
        this.writeMasterSecret(secretFile, generated);
        return generated;
    }

    private byte[] readMasterSecret(File secretFile) {
        try {
            String encoded = Files.readString(secretFile.toPath(), StandardCharsets.UTF_8).strip();
            byte[] decoded = Base64.getDecoder().decode(encoded);

            if (decoded.length != MASTER_SECRET_LENGTH) {
                throw new IllegalStateException("secret.key has an unexpected length, refusing to start with a corrupted key");
            }

            return decoded;
        }
        catch (IOException exception) {
            throw new IllegalStateException("Failed to read " + secretFile.getAbsolutePath(), exception);
        }
    }

    private byte[] generateMasterSecret() {
        byte[] secret = new byte[MASTER_SECRET_LENGTH];
        new SecureRandom().nextBytes(secret);
        return secret;
    }

    private void writeMasterSecret(File secretFile, byte[] secret) {
        try {
            File parent = secretFile.getParentFile();

            if (parent != null && !parent.exists() && !parent.mkdirs()) {
                throw new IllegalStateException("Failed to create directory " + parent.getAbsolutePath());
            }

            String encoded = Base64.getEncoder().encodeToString(secret);
            Files.writeString(secretFile.toPath(), encoded, StandardCharsets.UTF_8);

            this.restrictPermissions(secretFile);
        }
        catch (IOException exception) {
            throw new IllegalStateException("Failed to write " + secretFile.getAbsolutePath(), exception);
        }
    }

    private void restrictPermissions(File secretFile) {
        // Best-effort: works reliably on POSIX filesystems, silently ignored on platforms that don't support it (e.g. some Windows setups).
        boolean ownerOnly = secretFile.setReadable(false, false)
            & secretFile.setReadable(true, true)
            & secretFile.setWritable(false, false)
            & secretFile.setWritable(true, true);

        if (!ownerOnly) {
            System.getLogger(SecretKeyProviderImpl.class.getName())
                .log(System.Logger.Level.WARNING, "Could not restrict permissions on {0} - please verify file permissions manually", secretFile.getAbsolutePath());
        }
    }
}
