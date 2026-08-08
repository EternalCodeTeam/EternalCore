package com.eternalcode.core.loader.dependency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class ChecksumTest {

    // Known digests of the ASCII string "abc".
    private static final byte[] ABC = "abc".getBytes(StandardCharsets.UTF_8);
    private static final String ABC_SHA1 = "a9993e364706816aba3e25717850c26c9cd0d89d";
    private static final String ABC_SHA256 = "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad";
    private static final String ABC_SHA512 =
        "ddaf35a193617abacc417349ae20413112e6fa4e89a97ea20a9eeee64b55d39a"
            + "2192992a274fc1a836ba3c23a3feebbd454d4423643ce80e2a9ac94fa54ca49f";

    @Test
    void computesKnownDigests() {
        assertEquals(ABC_SHA1, Checksum.SHA1.hash(ABC));
        assertEquals(ABC_SHA256, Checksum.SHA256.hash(ABC));
        assertEquals(ABC_SHA512, Checksum.SHA512.hash(ABC));
    }

    @Test
    void matchesIgnoringCase() {
        assertTrue(Checksum.SHA256.matches(ABC, ABC_SHA256));
        assertTrue(Checksum.SHA256.matches(ABC, ABC_SHA256.toUpperCase()));
    }

    @Test
    void rejectsMismatchedChecksum() {
        assertFalse(Checksum.SHA256.matches(ABC, ABC_SHA1));
        assertFalse(Checksum.SHA256.matches(ABC, "not-a-hash"));
    }

    @Test
    void rejectsNullOrBlankChecksum() {
        assertFalse(Checksum.SHA256.matches(ABC, null));
        assertFalse(Checksum.SHA256.matches(ABC, "   "));
    }

    @Test
    void ignoresTrailingFilenameInPublishedChecksum() {
        // Maven repositories occasionally publish "<hash>  <filename>".
        assertTrue(Checksum.SHA256.matches(ABC, ABC_SHA256 + "  artifact-1.0.jar"));
        assertEquals(ABC_SHA256, Checksum.normalize(ABC_SHA256 + "  artifact-1.0.jar"));
    }

    @Test
    void exposesExpectedExtensions() {
        assertEquals("sha512", Checksum.SHA512.extension());
        assertEquals("sha256", Checksum.SHA256.extension());
        assertEquals("sha1", Checksum.SHA1.extension());
    }
}
