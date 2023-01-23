package com.eternalcode.core.feature.warp;

import com.eternalcode.core.shared.Position;

public class Warp {

    private final String name;
    private final Position position;

    public Warp(String name, Position position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return this.name;
    }

    public Position getPosition() {
        return this.position;
    }

}
