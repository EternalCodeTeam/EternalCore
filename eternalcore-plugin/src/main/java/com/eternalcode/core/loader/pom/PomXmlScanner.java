package com.eternalcode.core.loader.pom;

import com.eternalcode.core.loader.dependency.Dependency;
import com.eternalcode.core.loader.dependency.DependencyCollector;
import com.eternalcode.core.loader.repository.Repository;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PomXmlScanner implements DependencyScanner {

    private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();

    static {
        try {
            DOCUMENT_BUILDER_FACTORY.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DOCUMENT_BUILDER_FACTORY.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            DOCUMENT_BUILDER_FACTORY.setFeature("http://xml.org/sax/features/external-general-entities", false);
            DOCUMENT_BUILDER_FACTORY.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            DOCUMENT_BUILDER_FACTORY.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            DOCUMENT_BUILDER_FACTORY.setXIncludeAware(false);
            DOCUMENT_BUILDER_FACTORY.setExpandEntityReferences(false);
        }
        catch (ParserConfigurationException exception) {
            throw new RuntimeException("Failed to configure XML parser", exception);
        }
    }

    private final Repository localRepository;
    private final List<Repository> repositories = new ArrayList<>();

    public PomXmlScanner(List<Repository> repositories, Repository localRepository) {
        this.localRepository = localRepository;
        this.repositories.addAll(repositories);
    }

    @Override
    public DependencyCollector findAllChildren(DependencyCollector collector, Dependency dependency) {
        for (Repository repository : this.repositories) {
            Optional<List<Dependency>> subDependencies = this.tryReadDependency(dependency, repository);
            if (subDependencies.isEmpty()) {
                continue;
            }

            subDependencies.get().stream()
                .filter(subdependency -> !collector.hasScannedDependency(subdependency))
                .map(subdependency -> collector.addScannedDependency(subdependency))
                .filter(updatedDependency -> !updatedDependency.isBom())
                .forEach(updatedDependency -> this.findAllChildren(collector, updatedDependency));
            break;
        }

        return collector;
    }

    private Optional<List<Dependency>> tryReadDependency(Dependency dependency, Repository repository) {
        try {
            File pomXml = this.savePomXmlToLocalRepository(dependency, repository);
            List<Dependency> dependencies = this.readDependency(pomXml);

            return Optional.of(dependencies);
        }
        catch (IOException | SAXException | ParserConfigurationException | URISyntaxException exception) {
            return Optional.empty();
        }
    }

    private File savePomXmlToLocalRepository(Dependency dependency, Repository repository)
        throws URISyntaxException, IOException {
        File localFile = dependency.toPomXml(this.localRepository).toFile();

        if (localFile.exists() && !this.isEmpty(localFile)) {
            return localFile;
        }

        URL url = dependency.toPomXml(repository).toURL();

        try (InputStream inputStream = url.openStream()) {
            Files.createDirectories(localFile.toPath());
            Files.copy(inputStream, localFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        return localFile;
    }

    private boolean isEmpty(File file) {
        try {
            return Files.size(file.toPath()) == 0;
        }
        catch (IOException exception) {
            return true;
        }
    }

    private List<Dependency> readDependency(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
        Document doc = builder.parse(file);

        Element root = doc.getDocumentElement();
        PomXmlProperties properties = PomXmlProperties.from(root);
        List<Dependency> dependencies = new ArrayList<>();
        dependencies.addAll(readBomDependencies(root, properties));

        Element dependenciesElement = (Element) XmlUtil.getChildNode(root, "dependencies");
        if (dependenciesElement == null) {
            return dependencies;
        }

        NodeList dependencyList = dependenciesElement.getElementsByTagName("dependency");

        for (int elementIndex = 0; elementIndex < dependencyList.getLength(); elementIndex++) {
            Element dependencyElement = (Element) dependencyList.item(elementIndex);

            String groupId = XmlUtil.getElementContent(dependencyElement, "groupId");
            String artifactId = XmlUtil.getElementContent(dependencyElement, "artifactId");
            String version = XmlUtil.getElementContent(dependencyElement, "version");

            if (groupId == null || artifactId == null || version == null) {
                continue;
            }

            String scope = XmlUtil.getElementContent(dependencyElement, "scope");
            if (isUnexpectedScope(scope)) {
                continue;
            }

            String optional = XmlUtil.getElementContent(dependencyElement, "optional");
            if ("true".equals(optional)) {
                continue;
            }

            version = properties.replaceProperties(version);
            if (version == null) {
                continue;
            }

            dependencies.add(Dependency.of(groupId, artifactId, version));
        }

        return Collections.unmodifiableList(dependencies);
    }

    private List<Dependency> readBomDependencies(Element root, PomXmlProperties properties) {
        List<Dependency> dependencies = new ArrayList<>();
        Element dependencyManagementElement = (Element) XmlUtil.getChildNode(root, "dependencyManagement");
        if (dependencyManagementElement == null) {
            return dependencies;
        }

        Element dependenciesXml = (Element) XmlUtil.getChildNode(dependencyManagementElement, "dependencies");
        if (dependenciesXml == null) {
            return dependencies;
        }

        NodeList dependenciesListXml = dependenciesXml.getElementsByTagName("dependency");
        for (int i = 0; i < dependenciesListXml.getLength(); i++) {
            Element depElement = (Element) dependenciesListXml.item(i);
            String scope = XmlUtil.getElementContent(depElement, "scope");
            if (isUnexpectedScope(scope)) {
                continue;
            }

            String groupId = XmlUtil.getElementContent(depElement, "groupId");
            String artifactId = XmlUtil.getElementContent(depElement, "artifactId");
            String version = properties.replaceProperties(XmlUtil.getElementContent(depElement, "version"));

            if (groupId != null && artifactId != null && version != null) {
                if ("import".equals(scope)) {
                    dependencies.addAll(readBomDependencies((Dependency.of(groupId, artifactId, version).asBom())));
                }

                dependencies.add(Dependency.of(groupId, artifactId, version).asBom());
            }
        }

        return dependencies;
    }

    private List<Dependency> readBomDependencies(Dependency dependency) {
        for (Repository repository : this.repositories) {
            Optional<List<Dependency>> optionalFirstChildren = this.tryReadDependency(dependency, repository);

            if (optionalFirstChildren.isEmpty()) {
                continue;
            }

            return optionalFirstChildren.get().stream()
                .map(subdependency -> subdependency.asBom())
                .toList();
        }

        return Collections.emptyList();
    }

    private static boolean isUnexpectedScope(String scope) {
        return scope != null && !scope.equals("compile") && !scope.equals("runtime") && !scope.equals("import");
    }

}
