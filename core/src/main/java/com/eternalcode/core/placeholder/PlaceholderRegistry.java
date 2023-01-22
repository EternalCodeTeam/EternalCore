package com.eternalcode.core.placeholder;

import com.eternalcode.core.viewer.Viewer;

public interface PlaceholderRegistry {

    void registerPlaceholderReplacer(PlaceholderReplacer stack);

    void registerPlayerPlaceholderReplacer(PlayerPlaceholderReplacer stack);

    String format(String text);

    String format(String text, Viewer target);

}
