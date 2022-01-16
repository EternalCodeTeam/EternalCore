package com.eternalcode.core.scoreboard.api.interfances;

@FunctionalInterface
public interface PacketConstructor {
    Object invoke() throws Throwable;
}
