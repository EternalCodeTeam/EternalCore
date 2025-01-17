package com.eternalcode.core.feature.essentials.mob;

import com.eternalcode.core.bridge.litecommand.argument.AbstractViewerArgument;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteArgument;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.util.EntityUtil;
import dev.rollczi.litecommands.argument.Argument;
import dev.rollczi.litecommands.argument.parser.ParseResult;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.suggestion.SuggestionContext;
import dev.rollczi.litecommands.suggestion.SuggestionResult;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;

import java.util.stream.Stream;

@LiteArgument(type = MobEntity.class, name = MobEntityArgument.KEY)
class MobEntityArgument extends AbstractViewerArgument<MobEntity> {

    static final String KEY = "mobType";

    @Inject
    MobEntityArgument(TranslationManager translationManager) {
        super(translationManager);
    }

    @Override
    public ParseResult<MobEntity> parse(Invocation<CommandSender> invocation, String argument, Translation translation) {
        try {
            MobType mobType = MobType.valueOf(argument.toUpperCase());

            if (mobType.isParseable()) {
                return ParseResult.success(new MobEntity(mobType));
            }
        }
        catch (IllegalArgumentException ignore) {
            /* ignore */
        }

        try {
            EntityType entityType = EntityType.valueOf(argument.toUpperCase());

            if (EntityUtil.isMob(entityType)) {
                return ParseResult.success(new MobEntity(MobType.OTHER, entityType.getEntityClass()));
            }
        }
        catch (IllegalArgumentException ignore) {
            /* ignore */
        }

        return ParseResult.failure(translation.argument().noArgument());
    }

    @Override
    public SuggestionResult suggest(Invocation<CommandSender> invocation, Argument<MobEntity> argument, SuggestionContext context) {
        Stream<MobType> mobTypeStream = Stream.of(MobType.values()).filter(MobType::isSuggestable);
        Stream<EntityType> entityTypeStream = Stream.of(EntityType.values()).filter(EntityUtil::isMob);

        return Stream.concat(entityTypeStream, mobTypeStream)
            .map(Enum::name)
            .map(String::toLowerCase)
            .collect(SuggestionResult.collector());
    }

}
