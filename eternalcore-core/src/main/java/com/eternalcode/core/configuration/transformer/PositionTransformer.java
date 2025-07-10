package com.eternalcode.core.configuration.transformer;

import com.eternalcode.commons.bukkit.position.Position;
import eu.okaeri.configs.serdes.BidirectionalTransformer;
import eu.okaeri.configs.serdes.SerdesContext;
import eu.okaeri.configs.schema.GenericsPair;
import lombok.NonNull;

public class PositionTransformer extends BidirectionalTransformer<Position, String> {

    @Override
    public GenericsPair<Position, String> getPair() {
        return this.genericsPair(Position.class, String.class);
    }

    @Override
    public String leftToRight(@NonNull Position data, @NonNull SerdesContext context) {
        return data.toString();
    }

    @Override
    public Position rightToLeft(@NonNull String data, @NonNull SerdesContext context) {
        return Position.parse(data);
    }
}
