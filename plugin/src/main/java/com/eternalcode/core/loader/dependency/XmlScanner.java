package com.eternalcode.core.loader.dependency;

import com.eternalcode.core.loader.dependency.relocation.Relocation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
import java.util.Properties;

public class XmlScanner {

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

    private static final String POM_FORMAT = "%s/%s/%s/%s-%s.pom";

    private final List<Relocation> relocations = new ArrayList<>();

    public XmlScanner(List<Relocation> relocations) {
        this.relocations.addAll(relocations);
    }

    public Optional<List<Dependency>> findAll(DependencyRepository repository, Dependency dependency) {
        String mavenPomXmlPath = linkToPom(dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion());
        String repositoryUrl = repository.getUrl();
        String repositoryUrlWithSlash = repositoryUrl.endsWith("/") ? repositoryUrl : repositoryUrl + "/";

        String pomUrl = repositoryUrlWithSlash + mavenPomXmlPath;

        return tryReadDependency(pomUrl);
    }

    private String linkToPom(String groupId, String artifactId, String version) {
        return String.format(POM_FORMAT,
                groupId.replace(".", "/"),
                artifactId,
                version,
                artifactId,
                version
        );
    }

    private Optional<List<Dependency>> tryReadDependency(String linkToPom) {
        try {
            URL url = new URL(linkToPom);

            try (InputStream inputStream = url.openStream()) {
                List<Dependency> dependencies = readDependency(inputStream);

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
        Properties properties = loadProperties(root);
        Element dependenciesElement = (Element) getChildNode(root, "dependencies");

        if (dependenciesElement == null) {
            throw new SAXException("Dependencies element not found");
        }

        NodeList dependencyList = dependenciesElement.getElementsByTagName("dependency");

        List<Dependency> dependencies = new ArrayList<>();

        for (int elementIndex = 0; elementIndex < dependencyList.getLength(); elementIndex++) {
            Element dependencyElement = (Element) dependencyList.item(elementIndex);
            String groupId = dependencyElement.getElementsByTagName("groupId").item(0).getTextContent();
            String artifactId = dependencyElement.getElementsByTagName("artifactId").item(0).getTextContent();
            String version = dependencyElement.getElementsByTagName("version").item(0).getTextContent();

            Node scope = dependencyElement.getElementsByTagName("scope").item(0);
            if (scope != null) {
                String scopeValue = scope.getTextContent();

                if (!scopeValue.equals("compile") && !scopeValue.equals("runtime")) {
                    continue;
                }
            }

            if (version.startsWith("${") && version.endsWith("}")) {
                version = properties.getProperty(version.substring(2, version.length() - 1));
            }

            dependencies.add(new Dependency(groupId, artifactId, version, relocations.toArray(new Relocation[0])));
        }

        return Collections.unmodifiableList(dependencies);
    }

    private Properties loadProperties(Element root) {
        Properties properties = new Properties();
        NodeList nodeList = (NodeList) getChildNode(root, "properties");

        if (nodeList == null) {
            return properties;
        }

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node item = nodeList.item(i);

            if (!(item instanceof Element element)) {
                continue;
            }

            String name = element.getNodeName();
            Node childWithValue = element.getFirstChild();

            if (childWithValue == null) {
                continue;
            }

            String value = childWithValue.getNodeValue();

            if (value == null) {
                continue;
            }

            if (value.startsWith("[")) {
                value = value.substring(1, value.length() - 1);
            }

            properties.setProperty(name, value);
        }

        return properties;
    }

    private Node getChildNode(Element parent, String name) {
        NodeList childNodes = parent.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);

            if (item.getNodeName().equals(name)) {
                return childNodes.item(i);
            }
        }

        return null;
    }

}
