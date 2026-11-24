package com.eternalcode.core.loader.pom;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.Nullable;
import java.util.Properties;

// [06:52:47 ERROR]: Error occurred while enabling EternalCore v2.1.1-SNAPSHOT (Is it up to date?)
// java.util.concurrent.CompletionException: java.lang.IllegalArgumentException: Version contains a property placeholder: ${jackson.version}
//     at java.base/java.util.concurrent.CompletableFuture.wrapInCompletionException(CompletableFuture.java:323) ~[?:?]
//     at java.base/java.util.concurrent.CompletableFuture.encodeThrowable(CompletableFuture.java:359) ~[?:?]
//     at java.base/java.util.concurrent.CompletableFuture.completeThrowable(CompletableFuture.java:364) ~[?:?]
//     at java.base/java.util.concurrent.CompletableFuture$AsyncSupply.run(CompletableFuture.java:1791) ~[?:?]
//     at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1090) ~[?:?]
//     at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:614) ~[?:?]
//     at java.base/java.lang.Thread.run(Thread.java:1474) ~[?:?]
// Caused by: java.lang.IllegalArgumentException: Version contains a property placeholder: ${jackson.version}
//     at EternalCore v2.1.1-SNAPSHOT-1795499558452.jar//com.eternalcode.core.loader.dependency.Dependency.of(Dependency.java:166) ~[?:?]
//     at EternalCore v2.1.1-SNAPSHOT-1795499558452.jar//com.eternalcode.core.loader.pom.PomXmlScanner.readBomDependencies(PomXmlScanner.java:196) ~[?:?]
//     at EternalCore v2.1.1-SNAPSHOT-1795499558452.jar//com.eternalcode.core.loader.pom.PomXmlScanner.readDependency(PomXmlScanner.java:126) ~[?:?]
//     at EternalCore v2.1.1-SNAPSHOT-1795499558452.jar//com.eternalcode.core.loader.pom.PomXmlScanner.tryReadDependency(PomXmlScanner.java:79) ~[?:?]
//     at EternalCore v2.1.1-SNAPSHOT-1795499558452.jar//com.eternalcode.core.loader.pom.PomXmlScanner.readBomDependencies(PomXmlScanner.java:205) ~[?:?]
//     at EternalCore v2.1.1-SNAPSHOT-1795499558452.jar//com.eternalcode.core.loader.pom.PomXmlScanner.readBomDependencies(PomXmlScanner.java:193) ~[?:?]
//     at EternalCore v2.1.1-SNAPSHOT-1795499558452.jar//com.eternalcode.core.loader.pom.PomXmlScanner.readDependency(PomXmlScanner.java:126) ~[?:?]
//     at EternalCore v2.1.1-SNAPSHOT-1795499558452.jar//com.eternalcode.core.loader.pom.PomXmlScanner.tryReadDependency(PomXmlScanner.java:79) ~[?:?]
//     at EternalCore v2.1.1-SNAPSHOT-1795499558452.jar//com.eternalcode.core.loader.pom.PomXmlScanner.findAllChildren(PomXmlScanner.java:60) ~[?:?]
//     at EternalCore v2.1.1-SNAPSHOT-1795499558452.jar//com.eternalcode.core.loader.dependency.DependencyLoaderImpl.lambda$resolveDependencies$0(DependencyLoaderImpl.java:77) ~[?:?]
//     at EternalCore v2.1.1-SNAPSHOT-1795499558452.jar//com.eternalcode.core.loader.dependency.DependencyLoaderImpl.lambda$runParallel$2(DependencyLoaderImpl.java:117) ~[?:?]
//     at java.base/java.util.concurrent.CompletableFuture$AsyncSupply.run(CompletableFuture.java:1789) ~[?:?]
//     ... 3 more
// [06:52:47 INFO]: [EternalCore] Disabling EternalCore v2.1.1-SNAPSHOT
// Caused by: java.lang.IllegalArgumentException: Version contains a property placeholder: ${jackson.version}
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
