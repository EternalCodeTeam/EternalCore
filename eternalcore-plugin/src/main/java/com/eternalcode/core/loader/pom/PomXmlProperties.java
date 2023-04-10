package com.eternalcode.core.loader.pom;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.Nullable;
import java.util.Properties;

class PomXmlProperties {

    private final Properties properties;

    private PomXmlProperties(Properties properties) {
        this.properties = properties;
    }

    static PomXmlProperties from(Element root) {
        Properties properties = new Properties();
        NodeList nodeList = (NodeList) XmlUtil.getChildNode(root, "properties");

        if (nodeList == null) {
            return new PomXmlProperties(properties);
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

        return new PomXmlProperties(properties);
    }

    @Nullable
    public String replaceProperties(String value) {
        if (value == null) {
            return null;
        }

        if (value.startsWith("${") && value.endsWith("}")) {
            String key = value.substring(2, value.length() - 1);

            return this.properties.getProperty(key);
        }

        return value;
    }

}
