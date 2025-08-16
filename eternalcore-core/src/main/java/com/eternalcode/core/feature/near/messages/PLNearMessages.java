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
    public Notice entitiesNotFound = Notice.chat("<red>► <white>Nie znaleziono żadnych entity w zasięgu {RADIUS} bloków. Spróbuj ponownie z większym zasięgiem");

    @Comment({
        "# Available placeholders: ",
        "{ENTITY_AMOUNT} - the amount of entities found and shown",
        "{RADIUS} - the radius within which entities were searched"
    })
    public Notice entitiesFound = Notice.chat("<green>► <white>Liczba <bold>{ENTITY_AMOUNT}</bold> stworzeń w zasięgu: <bold>{RADIUS} bloków</bold>:");

    @Comment("# Entry format for each entity type in the list. Placeholders: {ENTITY_TYPE} - type of entity, {COUNT} - amount of entities of this type")
    public Notice entityEntry = Notice.chat("<gray>- <white>{ENTITY_TYPE}: <yellow>{COUNT}");
}
