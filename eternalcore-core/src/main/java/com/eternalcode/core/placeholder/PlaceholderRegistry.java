package com.eternalcode.core.placeholder;

import com.eternalcode.core.viewer.Viewer;

import java.util.Optional;

public interface PlaceholderRegistry {

    void register(Placeholder stack);

    String format(String text, Viewer target);

    Optional<PlaceholderRaw> getRawPlaceholder(String target);

}
