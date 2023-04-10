package com.eternalcode.core.loader.pom;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.Nullable;

final class XmlUtil {

    private XmlUtil() {
    }

    @Nullable
    static Node getChildNode(Element parent, String name) {
        NodeList childNodes = parent.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);

            if (item.getNodeName().equals(name)) {
                return childNodes.item(i);
            }
        }

        return null;
    }

    @Nullable
    static String getElementContent(Element dependencyElement, String tagName) {
        Node node = dependencyElement.getElementsByTagName(tagName).item(0);

        if (node == null) {
            return null;
        }

        return node.getTextContent();
    }

}
