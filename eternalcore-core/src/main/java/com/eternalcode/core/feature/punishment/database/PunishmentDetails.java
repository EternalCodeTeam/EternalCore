package com.eternalcode.core.feature.punishment.database;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Immutable JSON object with the data that is specific to a single punishment kind
 * (IP ban address, id of the revoked punishment, ...), so the table stays kind-agnostic.
 */
final class PunishmentDetails {

    private static final String EMPTY_JSON = "{}";

    private final JsonObject json;

    private PunishmentDetails(JsonObject json) {
        this.json = json;
    }

    static PunishmentDetails empty() {
        return new PunishmentDetails(new JsonObject());
    }

    static PunishmentDetails parse(String raw) {
        if (raw == null || raw.isBlank()) {
            return empty();
        }

        return new PunishmentDetails(JsonParser.parseString(raw).getAsJsonObject());
    }

    PunishmentDetails with(String key, String value) {
        JsonObject copy = this.json.deepCopy();
        copy.addProperty(key, value);

        return new PunishmentDetails(copy);
    }

    String require(String key) {
        if (!this.json.has(key)) {
            throw new IllegalStateException("Punishment details are missing required key \"" + key + "\": " + this.json);
        }

        return this.json.get(key).getAsString();
    }

    String serialize() {
        return this.json.size() == 0 ? EMPTY_JSON : this.json.toString();
    }
}
