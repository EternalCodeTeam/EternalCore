package com.eternalcode.core.loader.pom;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.Nullable;
import java.util.Properties;

class PomXmlProperties {

    private static final String PLACEHOLDER_PREFIX = "${";
    private static final String PLACEHOLDER_SUFFIX = "}";
    private static final int MAX_RESOLVE_DEPTH = 10;

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
    public String replaceProperties(@Nullable String value) {
        return this.replaceProperties(value, 0);
    }

    @Nullable
    private String replaceProperties(@Nullable String value, int depth) {
        if (value == null || depth > MAX_RESOLVE_DEPTH) {
            return null;
        }

        if (!value.contains(PLACEHOLDER_PREFIX)) {
            return value;
        }

        if (!value.startsWith(PLACEHOLDER_PREFIX) || !value.endsWith(PLACEHOLDER_SUFFIX)) {
            return null;
        }

        String key = value.substring(PLACEHOLDER_PREFIX.length(), value.length() - PLACEHOLDER_SUFFIX.length());

        return this.replaceProperties(this.properties.getProperty(key), depth + 1);
    }

}
