package com.eternalcode.core.loader.dependency;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Verifies the integrity of downloaded artifacts against the checksum files published alongside them in a Maven
 * repository (e.g. {@code artifact-1.0.jar.sha256}).
 *
 * <p>Ordered strongest-first so callers can prefer the most secure digest a repository publishes. SHA-1 is retained
 * only as a last-resort fallback because it is the single digest Maven repositories are guaranteed to serve; it is
 * cryptographically weak and should not be relied upon on its own.
 */
public enum Checksum {

    SHA512("sha512", "SHA-512"),
    SHA256("sha256", "SHA-256"),
    SHA1("sha1", "SHA-1");

    private final String extension;
    private final String algorithm;

    Checksum(String extension, String algorithm) {
        this.extension = extension;
        this.algorithm = algorithm;
    }

    /**
     * The file extension appended to the artifact name to locate this checksum (without a leading dot).
     */
    public String extension() {
        return this.extension;
    }

    /**
     * Computes the lower-case hex digest of the given data using this algorithm.
     */
    public String hash(byte[] data) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(this.algorithm);
        }
        catch (NoSuchAlgorithmException exception) {
            throw new DependencyException("Missing digest algorithm: " + this.algorithm, exception);
        }

        return toHex(digest.digest(data));
    }

    /**
     * Returns {@code true} if the digest of {@code data} equals the published checksum.
     *
     * <p>Published checksum files sometimes contain trailing content such as {@code "<hash>  <filename>"};
     * only the leading token is compared, and comparison is case-insensitive.
     */
    public boolean matches(byte[] data, String publishedChecksum) {
        if (publishedChecksum == null) {
            return false;
        }

        String expected = normalize(publishedChecksum);
        if (expected.isEmpty()) {
            return false;
        }

        return expected.equalsIgnoreCase(this.hash(data));
    }

    static String normalize(String rawChecksum) {
        String trimmed = rawChecksum.trim();

        for (int index = 0; index < trimmed.length(); index++) {
            if (Character.isWhitespace(trimmed.charAt(index))) {
                return trimmed.substring(0, index);
            }
        }

        return trimmed;
    }

    private static String toHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder(bytes.length * 2);

        for (byte value : bytes) {
            builder.append(Character.forDigit((value >> 4) & 0xF, 16));
            builder.append(Character.forDigit(value & 0xF, 16));
        }

        return builder.toString();
    }

}
