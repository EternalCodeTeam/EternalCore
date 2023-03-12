/*
 * This file is part of LuckPerms, licensed under the MIT License.
 *
 *  Copyright (c) lucko (Luck) <luck@lucko.me>
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.eternalcode.core.loader.dependency;

import com.eternalcode.core.loader.dependency.relocation.Relocation;
import com.google.common.collect.ImmutableList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Dependency {

    public static final Dependency ASM = new Dependency(
            "org.ow2.asm",
                "asm",
                "9.1"
    );

    public static final Dependency ASM_COMMONS = new Dependency(
            "org.ow2.asm",
                "asm-commons",
                "9.1"
    );

    public static final Dependency JAR_RELOCATOR = new Dependency(
            "me.lucko",
                "jar-relocator",
                "1.4"
    );

    private static final String MAVEN_FORMAT = "%s/%s/%s/%s-%s.jar";

    private final String groupId;
    private final String artifactId;
    private final String version;
    private final String mavenJarPath;
    private final List<Relocation> relocations;

    Dependency(String groupId, String artifactId, String version, Relocation... relocations) {
        if (version.contains("${")) {
            throw new IllegalArgumentException("Version contains a property placeholder: " + version);
        }

        this.groupId = rewriteEscaping(groupId);
        this.artifactId = rewriteEscaping(artifactId);
        this.version = rewriteEscaping(version);
        this.relocations = ImmutableList.copyOf(relocations);

        this.mavenJarPath = String.format(MAVEN_FORMAT,
                this.groupId.replace(".", "/"),
                this.artifactId,
                this.version,
                this.artifactId,
                this.version
        );

    }

    private static String rewriteEscaping(String s) {
        return s.replace("{}", ".");
    }

    public String getFileName(String classifier) {
        String extra = classifier == null || classifier.isEmpty()
            ? ""
            : "-" + classifier;

        return this.getGroupId() + "-" + this.getArtifactId() + "-" + this.version + extra + ".jar";
    }

    String getMavenJarPath() {
        return this.mavenJarPath;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public String getArtifactId() {
        return this.artifactId;
    }

    public String getGroupArtifactId() {
        return this.groupId + ":" + this.artifactId;
    }

    public String getVersion() {
        return this.version;
    }

    public List<Relocation> getRelocations() {
        return this.relocations;
    }

    /**
     * Creates a {@link MessageDigest} suitable for computing the checksums
     * of dependencies.
     *
     * @return the digest
     */
    public static MessageDigest createDigest() {
        try {
            return MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static Dependency of(String groupId, String artifactId, String version, Relocation... relocations) {
        return new Dependency(groupId, artifactId, version, relocations);
    }

    @Override
    public String toString() {
        return this.getGroupArtifactId() + ":" + this.version;
    }

    public boolean toRelocate() {
        return !this.relocations.isEmpty();
    }

}