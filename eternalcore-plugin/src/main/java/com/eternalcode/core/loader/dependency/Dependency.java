package com.eternalcode.core.loader.dependency;

import com.eternalcode.core.loader.repository.Repository;

import com.eternalcode.core.loader.resource.ResourceLocator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dependency {

    private static final Pattern VERSION_PATTERN = Pattern.compile("(?<major>[0-9]+)\\.(?<minor>[0-9]+)\\.?(?<patch>[0-9]?)(-(?<label>[-+.a-zA-Z0-9]+))?");

    private static final String PATH_FORMAT = "%s/%s/%s/%s/%s";
    private static final String JAR_MAVEN_FORMAT = "%s-%s.jar";
    private static final String JAR_MAVEN_FORMAT_WITH_CLASSIFIER = "%s-%s-%s.jar";
    private static final String POM_XML_FORMAT = "%s-%s.pom";

    private final String groupId;
    private final String artifactId;
    private final String version;
    private final boolean isBom;

    private Dependency(String groupId, String artifactId, String version) {
        this(groupId, artifactId, version, false);
    }

    private Dependency(String groupId, String artifactId, String version, boolean isBom) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.isBom = isBom;
    }

    public boolean isBom() {
        return this.isBom;
    }

    public ResourceLocator toMavenJar(Repository repository) {
        return toResource(repository, JAR_MAVEN_FORMAT.formatted(this.artifactId, this.version));
    }

    public ResourceLocator toMavenJar(Repository repository, String classifier) {
        return toResource(repository, JAR_MAVEN_FORMAT_WITH_CLASSIFIER.formatted(this.artifactId, this.version, classifier));
    }

    public ResourceLocator toPomXml(Repository repository) {
        return toResource(repository, POM_XML_FORMAT.formatted(this.artifactId, this.version));
    }

    public ResourceLocator toResource(Repository repository, String fileName) {
        String url = String.format(PATH_FORMAT,
            repository.url(),
            this.groupId.replace(".", "/"),
            this.artifactId,
            this.version,
            fileName
        );

        return ResourceLocator.from(url);
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

    public boolean isNewerThan(Dependency dependency) {
        int thisMajor = this.getMajorVersion();
        int dependencyMajor = dependency.getMajorVersion();

        if (thisMajor != dependencyMajor) {
            return thisMajor > dependencyMajor;
        }

        int thisMinor = this.getMinorVersion();
        int dependencyMinor = dependency.getMinorVersion();

        if (thisMinor != dependencyMinor) {
            return thisMinor > dependencyMinor;
        }

        int thisPatch = this.getPatchVersion();
        int dependencyPatch = dependency.getPatchVersion();

        if (thisPatch != dependencyPatch) {
            return thisPatch > dependencyPatch;
        }

        return false;
    }

    public int getMajorVersion() {
        return this.getSemanticVersionPart("major");
    }

    public int getMinorVersion() {
        return this.getSemanticVersionPart("minor");
    }

    public int getPatchVersion() {
        return this.getSemanticVersionPart("patch");
    }

    public String getLabelVersion() {
        Matcher matcher = VERSION_PATTERN.matcher(this.version);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid version format: " + this.version + " for dependency: " + this);
        }

        return matcher.group("label");
    }

    private int getSemanticVersionPart(String name) {
        Matcher matcher = VERSION_PATTERN.matcher(this.version);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid version format: " + this.version + " for dependency: " + this);
        }

        String versionNumber = matcher.group(name);

        if (versionNumber.isEmpty()) {
            return 0;
        }

        return Integer.parseInt(versionNumber);
    }

    @Override
    public String toString() {
        return this.getGroupArtifactId() + ":" + this.version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dependency that = (Dependency) o;
        return Objects.equals(this.groupId, that.groupId) && Objects.equals(this.artifactId, that.artifactId) && Objects.equals(this.version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.groupId, this.artifactId, this.version);
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

    public Dependency asNotBom() {
        return new Dependency(this.groupId, this.artifactId, this.version, false);
    }

    public Dependency asBom() {
        return new Dependency(this.groupId, this.artifactId, this.version, true);
    }

}