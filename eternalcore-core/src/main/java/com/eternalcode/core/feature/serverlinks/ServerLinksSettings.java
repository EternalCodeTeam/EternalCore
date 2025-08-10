package com.eternalcode.core.feature.serverlinks;

public interface ServerLinksSettings {
    java.util.List<ServerLinksEntry> serverLinks();
    boolean sendLinksOnJoin();
}
