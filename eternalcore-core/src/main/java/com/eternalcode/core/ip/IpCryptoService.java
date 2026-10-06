package com.eternalcode.core.ip;

public interface IpCryptoService {

    EncryptedValue encrypt(String plainIp);

    String decrypt(EncryptedValue encryptedValue);

    String hash(String plainIp);
}
