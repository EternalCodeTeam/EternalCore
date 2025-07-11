package com.eternalcode.core.feature.serverlinks;

import java.io.Serializable;

@SuppressWarnings("unused")
public class ServerLinksEntry implements Serializable {

    public String name;
    public String address;

    public ServerLinksEntry(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public ServerLinksEntry() {
    }

    public static ServerLinksEntry of(String name, String address) {
        return new ServerLinksEntry(name, address);
    }

    public String name() {
        return this.name;
    }

    public String address() {
        return this.address;
    }
}
