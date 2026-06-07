package com.eternalcode.core.compatibility;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CompatibilityServiceTest {

    private final CompatibilityService compatibilityService = new CompatibilityService();

    @Test
    void testParseClassicVersion() {
        CompatibilityService.MinecraftVersion version = CompatibilityService.parseMinecraftVersion("1.21.1-R0.1-SNAPSHOT");
        assertThat(version.major()).isEqualTo(1);
        assertThat(version.minor()).isEqualTo(21);
        assertThat(version.patch()).isEqualTo(1);
    }

    @Test
    void testParseNewVersion() {
        CompatibilityService.MinecraftVersion version = CompatibilityService.parseMinecraftVersion("2026.1.2-R0.1-SNAPSHOT");
        assertThat(version.major()).isEqualTo(2026);
        assertThat(version.minor()).isEqualTo(1);
        assertThat(version.patch()).isEqualTo(2);
    }

    @Test
    void testParseVersionWithoutPatch() {
        CompatibilityService.MinecraftVersion version = CompatibilityService.parseMinecraftVersion("1.21-R0.1-SNAPSHOT");
        assertThat(version.major()).isEqualTo(1);
        assertThat(version.minor()).isEqualTo(21);
        assertThat(version.patch()).isEqualTo(0);
    }

    @Test
    void testIsCompatibleFrom() {
        CompatibilityService.MinecraftVersion current = new CompatibilityService.MinecraftVersion(2026, 1, 0);

        assertThat(this.compatibilityService.isCompatibleFrom(createVersion(1, 21, 0), current)).isTrue();
        assertThat(this.compatibilityService.isCompatibleFrom(createVersion(2026, 0, 0), current)).isTrue();
        assertThat(this.compatibilityService.isCompatibleFrom(createVersion(2026, 1, 0), current)).isTrue();
        assertThat(this.compatibilityService.isCompatibleFrom(createVersion(2026, 2, 0), current)).isFalse();
        assertThat(this.compatibilityService.isCompatibleFrom(createVersion(2027, 0, 0), current)).isFalse();
    }

    @Test
    void testIsCompatibleTo() {
        CompatibilityService.MinecraftVersion current = new CompatibilityService.MinecraftVersion(1, 21, 1);

        assertThat(this.compatibilityService.isCompatibleTo(createVersion(1, 21, 2), current)).isTrue();
        assertThat(this.compatibilityService.isCompatibleTo(createVersion(1, 21, 1), current)).isTrue();
        assertThat(this.compatibilityService.isCompatibleTo(createVersion(1, 21, 0), current)).isFalse();
        assertThat(this.compatibilityService.isCompatibleTo(createVersion(2026, 1, 0), current)).isTrue();
        assertThat(this.compatibilityService.isCompatibleTo(createVersion(0, 0, 0), current)).isFalse();
    }

    private Version createVersion(int major, int minor, int patch) {
        return new Version() {
            @Override
            public int major() { return major; }
            @Override
            public int minor() { return minor; }
            @Override
            public int patch() { return patch; }
            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() { return Version.class; }
        };
    }
}
