package com.eternalcode.core.bridge.placeholderapi;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.placeholder.PlaceholderReplacer;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

@FeatureDocs(
    name = "PlaceholderAPI",
    description = "Adds support for PlaceholderAPI"
)
public class PlaceholderApiReplacer implements PlaceholderReplacer {

    @Override
    public String apply(String text, Player targetPlayer) {
        return PlaceholderAPI.setPlaceholders(targetPlayer, text);
    }


}
