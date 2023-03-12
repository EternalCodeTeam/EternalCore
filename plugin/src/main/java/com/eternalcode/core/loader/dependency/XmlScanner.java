package com.eternalcode.core.loader.dependency;

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

    public static Optional<List<Dependency>> findAll(DependencyRepository repository, Dependency dependency) {
        String mavenPomXmlPath = linkToPom(dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion());
        String repositoryUrl = repository.getUrl();
        String repositoryUrlWithSlash = repositoryUrl.endsWith("/") ? repositoryUrl : repositoryUrl + "/";

        String pomUrl = repositoryUrlWithSlash + mavenPomXmlPath;

        return tryReadDependency(pomUrl);
    }

    private static String linkToPom(String groupId, String artifactId, String version) {
        return String.format(POM_FORMAT,
                groupId.replace(".", "/"),
                artifactId,
                version,
                artifactId,
                version
        );
    }

    private static Optional<List<Dependency>> tryReadDependency(String linkToPom) {
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

    private static List<Dependency> readDependency(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
        Document doc = builder.parse(inputStream);

        Element root = doc.getDocumentElement();

        Element dependenciesElement = (Element) root.getElementsByTagName("dependencies").item(0);
        NodeList dependencyList = dependenciesElement.getElementsByTagName("dependency");

        List<Dependency> dependencies = new ArrayList<>();

        for (int elementIndex = 0; elementIndex < dependencyList.getLength(); elementIndex++) {
            Element dependencyElement = (Element) dependencyList.item(elementIndex);
            String groupId = dependencyElement.getElementsByTagName("groupId").item(0).getTextContent();
            String artifactId = dependencyElement.getElementsByTagName("artifactId").item(0).getTextContent();
            String version = dependencyElement.getElementsByTagName("version").item(0).getTextContent();

            System.out.println(groupId + " " + artifactId + " " + version);
            dependencies.add(new Dependency(groupId, artifactId, version));
        }

        return Collections.unmodifiableList(dependencies);
    }

}
