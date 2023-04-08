package com.eternalcode.core.loader.dependency;

import com.eternalcode.core.loader.repository.Repository;

import java.util.Objects;

public class Dependency {

    private static final String JAR_MAVEN_FORMAT = "%s/%s/%s/%s/%s-%s.jar";
    private static final String JAR_FORMAT = "%s-%s-%s.jar";
    private static final String JAR_FORMAT_WITH_CLASSIFIER = "%s-%s-%s-%s.jar";
    private static final String POM_XML_FORMAT = "%s/%s/%s/%s/%s-%s.pom";

    private final String groupId;
    private final String artifactId;
    private final String version;

    private Dependency(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public String toJarFileName() {
        return String.format(JAR_FORMAT, this.groupId, this.artifactId, this.version);
    }

    public String toJarFileName(String classifier) {
        return String.format(JAR_FORMAT_WITH_CLASSIFIER, this.groupId, this.artifactId, this.version, classifier);
    }

    public String toMavenJarPath(Repository repository) {
        return String.format(JAR_MAVEN_FORMAT,
                repository.url(),
                this.groupId.replace(".", "/"),
                this.artifactId,
                this.version,
                this.artifactId,
                this.version
        );
    }

    public String toPomXmlPath(Repository repository) {
        return String.format(POM_XML_FORMAT,
                repository.url(),
                this.groupId.replace(".", "/"),
                this.artifactId,
                this.version,
                this.artifactId,
                this.version
        );
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

    @Override
    public String toString() {
        return this.getGroupArtifactId() + ":" + this.version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dependency that)) return false;
        return Objects.equals(groupId, that.groupId) && Objects.equals(artifactId, that.artifactId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, artifactId);
    }

    public static Dependency of(String groupId, String artifactId, String version) {
        if (version.contains("${")) {
            throw new IllegalArgumentException("Version contains a property placeholder: " + version);
        }

        return new Dependency(rewriteEscaping(groupId), rewriteEscaping(artifactId), rewriteEscaping(version));
    }

    private static String rewriteEscaping(String value) {
        return value.replace("{}", ".");
    }

}