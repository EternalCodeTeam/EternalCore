package com.eternalcode.core.ip;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.util.Arrays;

final class Hkdf {

    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final int HASH_LENGTH = 32;

    private Hkdf() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static byte[] deriveKey(byte[] masterSecret, byte[] info, int outputLength) {
        if (outputLength <= 0) {
            throw new IllegalArgumentException("outputLength must be positive");
        }
        if (outputLength > 255 * HASH_LENGTH) {
            throw new IllegalArgumentException("outputLength too large for HKDF-SHA256");
        }

        byte[] pseudoRandomKey = extract(masterSecret);
        return expand(pseudoRandomKey, info, outputLength);
    }

    private static byte[] extract(byte[] masterSecret) {
        // Zero-filled salt is acceptable per RFC 5869 when no salt is supplied by the caller.
        byte[] salt = new byte[HASH_LENGTH];
        return hmac(salt, masterSecret);
    }

    private static byte[] expand(byte[] pseudoRandomKey, byte[] info, int outputLength) {
        int blocks = (int) Math.ceil((double) outputLength / HASH_LENGTH);
        byte[] output = new byte[blocks * HASH_LENGTH];
        byte[] previousBlock = new byte[0];

        for (int i = 0; i < blocks; i++) {
            byte[] input = concat(previousBlock, info, new byte[] { (byte) (i + 1) });
            previousBlock = hmac(pseudoRandomKey, input);
            System.arraycopy(previousBlock, 0, output, i * HASH_LENGTH, HASH_LENGTH);
        }

        return Arrays.copyOf(output, outputLength);
    }

    private static byte[] hmac(byte[] key, byte[] data) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(new SecretKeySpec(key, HMAC_ALGORITHM));
            return mac.doFinal(data);
        }
        catch (GeneralSecurityException exception) {
            throw new IllegalStateException("Failed to compute HMAC-SHA256", exception);
        }
    }

    private static byte[] concat(byte[]... arrays) {
        int length = 0;

        for (byte[] array : arrays) {
            length += array.length;
        }

        byte[] result = new byte[length];
        int offset = 0;

        for (byte[] array : arrays) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }

        return result;
    }
}
