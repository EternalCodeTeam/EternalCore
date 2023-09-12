package com.eternalcode.core.feature.essentials.mob;

import com.eternalcode.core.litecommand.argument.AbstractViewerArgument;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.util.EntityUtil;
import com.eternalcode.core.viewer.ViewerProvider;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.entity.EntityType;
import panda.std.Result;

import java.util.List;
import java.util.stream.Stream;

@LiteArgument(type = MobEntity.class)
@ArgumentName("mob")
class MobEntityArgument extends AbstractViewerArgument<MobEntity> {

    @Inject
    MobEntityArgument(ViewerProvider viewerProvider, TranslationManager translationManager) {
        super(viewerProvider, translationManager);
    }

    @Override
    public Result<MobEntity, Notice> parse(LiteInvocation invocation, String argument, Translation translation) {
        try {
            MobType mobType = MobType.valueOf(argument.toUpperCase());

            if (mobType.isParseable()) {
                return Result.ok(new MobEntity(mobType));
            }
        }
        catch (IllegalArgumentException ignore) {
            /* ignore */
        }

        try {
            EntityType entityType = EntityType.valueOf(argument.toUpperCase());

            if (EntityUtil.isMob(entityType)) {
                return Result.ok(new MobEntity(MobType.OTHER, entityType.getEntityClass()));
            }
        }
        catch (IllegalArgumentException ignore) {
            /* ignore */
        }

        return Result.error(translation.argument().noArgument());
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        Stream<MobType> mobTypeStream = Stream.of(MobType.values()).filter(MobType::isSuggeestable);
        Stream<EntityType> entityTypeStream = Stream.of(EntityType.values()).filter(EntityUtil::isMob);

        return Stream.concat(entityTypeStream, mobTypeStream)
            .map(Enum::name)
            .map(String::toLowerCase)
            .map(Suggestion::of)
            .toList();
    }
}
