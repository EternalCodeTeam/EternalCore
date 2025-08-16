package com.eternalcode.core.feature.near.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLNearMessages extends OkaeriConfig implements NearMessages {

    @Comment("# Available placeholders: {RADIUS} - the radius within which entities were searched")
    public Notice noEntitiesFound = Notice.chat("<red>► <white>Nie znaleziono stworzeń w zasięgu {RADIUS} bloków. Spróbuj ponownie z większym zasięgiem");

    @Comment(
        "# Available placeholders: " +
            "{ENTITYAMOUNT} - the amount of entities found and shown, " +
            "{RADIUS} - the radius within which entities were searched, " +
            "{ENTITYLIST} - the list of all entities found grouped by the entity type"
    )
    public Notice entitiesShown = Notice.chat("<green>► <white>Liczba <bold>{ENTITYAMOUNT}</bold> stworzeń w zasięgu: <bold>{RADIUS} bloków</bold>: {ENTITYLIST}");

}
