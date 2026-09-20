package com.eternalcode.core.ip;

import java.util.Arrays;
import java.util.Objects;

public final class EncryptedValue {

    private final byte[] ciphertext;
    private final byte[] iv;

    public EncryptedValue(byte[] ciphertext, byte[] iv) {
        this.ciphertext = Arrays.copyOf(ciphertext, ciphertext.length);
        this.iv = Arrays.copyOf(iv, iv.length);
    }

    public byte[] ciphertext() {
        return Arrays.copyOf(this.ciphertext, this.ciphertext.length);
    }

    public byte[] iv() {
        return Arrays.copyOf(this.iv, this.iv.length);
    }
}
