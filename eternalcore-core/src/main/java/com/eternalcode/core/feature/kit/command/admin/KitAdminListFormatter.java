package com.eternalcode.core.feature.kit.command.admin;

import com.eternalcode.core.feature.kit.KitNotices;
import com.eternalcode.multification.shared.Formatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class KitAdminListFormatter {

    private KitAdminListFormatter() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }

    static String format(List<String> entries, String entryFormat, String entryPlaceholder) {
        return IntStream.range(0, entries.size())
            .mapToObj(index -> new Formatter()
                .register(KitNotices.INDEX, index + 1)
                .register(entryPlaceholder, entries.get(index))
                .format(entryFormat))
            .collect(Collectors.joining("<newline>"));
    }
}
