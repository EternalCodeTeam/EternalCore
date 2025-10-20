package com.eternalcode.core.feature.near.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENNearMessages extends OkaeriConfig implements NearMessages {

    @Comment("# Available placeholders: {RADIUS} - the radius within which entities were searched")
    Notice entitiesNotFound = Notice.chat("<red>► <white>No entities found within a radius of {RADIUS} blocks. Please try again with a different radius.");

    @Comment("# Available placeholders: {ENTITY_AMOUNT} - the amount of entities found and shown, {RADIUS} - the radius within which entities were searched")
    Notice entitiesFound = Notice.chat("<green>► <white><bold>{ENTITY_AMOUNT} entities</bold> found and shown within a radius of <bold>{RADIUS} blocks</bold>:");

    @Comment("# Entry format for each entity type in the list. Placeholders: {ENTITY_TYPE} - type of entity, {COUNT} - amount of entities of this type")
    Notice entityEntry = Notice.chat("<gray>- <white>{ENTITY_TYPE}: <yellow>{COUNT}");
    Notice invalidEntityType = Notice.chat("<red>✘ <dark_red>No valid entity scope provided! Use a suggested option.");

}
