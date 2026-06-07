package com.eternalcode.core.configuration.transformer;

import com.eternalcode.core.util.MaterialUtil;
import eu.okaeri.configs.serdes.BidirectionalTransformer;
import eu.okaeri.configs.serdes.SerdesContext;
import eu.okaeri.configs.schema.GenericsPair;
import lombok.NonNull;
import org.bukkit.Material;

public class MaterialTransformer extends BidirectionalTransformer<Material, String> {

    @Override
    public GenericsPair<Material, String> getPair() {
        return this.genericsPair(Material.class, String.class);
    }

    @Override
    public String leftToRight(@NonNull Material material, @NonNull SerdesContext context) {
        return material.name();
    }

    @Override
    public Material rightToLeft(@NonNull String materialName, @NonNull SerdesContext context) {
        return MaterialUtil.parse(materialName)
            .orElseThrow(() -> new IllegalArgumentException("Unsupported material: " + materialName));
    }
}
