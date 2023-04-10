package com.eternalcode.core.loader.pom;

import com.eternalcode.core.loader.dependency.Dependency;
import com.eternalcode.core.loader.dependency.DependencyCollector;
import com.eternalcode.core.loader.repository.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PomXmlScanner {

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

    private final List<Repository> repositories = new ArrayList<>();

    public PomXmlScanner(List<Repository> repositories) {
        this.repositories.addAll(repositories);
    }

    public DependencyCollector findAllChildren(DependencyCollector collector, Dependency dependency) {

        for (Repository repository : this.repositories) {
            Optional<List<Dependency>> optionalFirstChildren = this.tryReadDependency(dependency, repository);

            if (optionalFirstChildren.isEmpty()) {
                continue;
            }

            List<Dependency> firstChildren = optionalFirstChildren.get();

            for (Dependency firstChild : firstChildren) {
                if (collector.hasScannedDependency(firstChild)) {
                    continue;
                }

                collector = this.findAllChildren(collector, firstChild);
                collector.scannedDependency(firstChild);
            }

            break;
        }

        return collector;
    }

    private Optional<List<Dependency>> tryReadDependency(Dependency dependency, Repository repository) {
        try {
            String pomXmlPath = dependency.toPomXmlPath(repository);
            URL url = new URL(pomXmlPath);

            try (InputStream inputStream = url.openStream()) {
                List<Dependency> dependencies = this.readDependency(inputStream);

                return Optional.of(dependencies);
            }
        }
        catch (IOException | SAXException | ParserConfigurationException exception) {
            return Optional.empty();
        }
    }

    private List<Dependency> readDependency(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
        Document doc = builder.parse(inputStream);

        Element root = doc.getDocumentElement();
        PomXmlProperties properties = PomXmlProperties.from(root);
        Element dependenciesElement = (Element) XmlUtil.getChildNode(root, "dependencies");

        if (dependenciesElement == null) {
            throw new SAXException("Dependencies element not found");
        }

        NodeList dependencyList = dependenciesElement.getElementsByTagName("dependency");

        List<Dependency> dependencies = new ArrayList<>();

        for (int elementIndex = 0; elementIndex < dependencyList.getLength(); elementIndex++) {
            Element dependencyElement = (Element) dependencyList.item(elementIndex);

            String groupId = XmlUtil.getElementContent(dependencyElement, "groupId");
            String artifactId = XmlUtil.getElementContent(dependencyElement, "artifactId");
            String version = XmlUtil.getElementContent(dependencyElement, "version");

            if (groupId == null || artifactId == null || version == null) {
                continue;
            }

            String scope = XmlUtil.getElementContent(dependencyElement, "scope");
            if (scope != null && !scope.equals("compile") && !scope.equals("runtime")) {
                continue;
            }

            String optional = XmlUtil.getElementContent(dependencyElement, "optional");
            if (optional != null && optional.equals("true")) {
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

}
