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
    public Notice noEntitiesFound = Notice.chat("<red>► <white>No entities found within a radius of {RADIUS} blocks. Please try again with a different radius.");

    @Comment("# Available placeholders: {ENTITYAMOUNT} - the amount of entities found and shown, {RADIUS} - the radius within which entities were searched, {ENTITYLIST} - the list of all entities found grouped by the entity type")
    public Notice entitiesShown = Notice.chat("<green>► <white><bold>{ENTITYAMOUNT} entities</bold> found and shown within a radius of <bold>{RADIUS} blocks</bold>: {ENTITYLIST}");
}
