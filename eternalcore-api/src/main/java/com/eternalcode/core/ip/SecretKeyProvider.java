package com.eternalcode.core.ip;

public interface SecretKeyProvider {

    byte[] aesKey();

    byte[] hmacKey();
}
