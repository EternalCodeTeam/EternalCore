package com.eternalcode.core.feature.near.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;

@Getter
public class PLNearMessages extends OkaeriConfig implements NearMessages {

    //@TODO: add the translations

    @Comment("# Available placeholders: {RADIUS} - the radius within which entities were searched")
    public Notice noEntitiesFound = Notice.chat("");

    @Comment("# Available placeholders: {ENTITYAMOUNT} - the amount of entities found and shown, {RADIUS} - the radius within which entities were searched, {ENTITYLIST} - the list of all entities found grouped by the entity type")
    public Notice entitiesShown = Notice.chat("");

}
