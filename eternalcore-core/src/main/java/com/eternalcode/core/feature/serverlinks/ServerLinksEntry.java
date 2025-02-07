package com.eternalcode.core.feature.serverlinks;

import net.dzikoysk.cdn.entity.Contextual;

@Contextual
public class ServerLinksEntry {

    public final String name;
    public final String address;

    public ServerLinksEntry(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public static ServerLinksEntry of(String name, String address) {
        return new ServerLinksEntry(name, address);
    }

    public String name() {
        return name;
    }

    public String address() {
        return address;
    }
}
