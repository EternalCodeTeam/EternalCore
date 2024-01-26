package com.eternalcode.core.feature.afk;

/**
 * Enum representing possible reasons for a player being marked as Away From Keyboard (AFK).
 */
public enum AfkReason {

    /**
     * Reason is inactivity, that is the player hasn't interacted with the game for a defined period of time.
     */
    INACTIVITY,

    /**
     * Reason is a command, that is the status was set manually by a player or an admin using a command.
     */
    COMMAND,

    /**
     * Reason is a plugin, that is the status was set programmatically by a plugin or API.
     */
    PLUGIN,
}