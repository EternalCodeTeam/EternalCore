package com.eternalcode.core.command.argument;

import com.eternalcode.core.feature.essentials.mob.MobEntity;
import com.eternalcode.core.feature.essentials.mob.MobType;
import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.util.EntityUtil;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.entity.EntityType;
import panda.std.Option;
import panda.std.Result;

import java.util.List;
import java.util.stream.Stream;

@ArgumentName("mob")
public class MobEntityArgument extends AbstractViewerArgument<MobEntity> {

    public MobEntityArgument(BukkitViewerProvider viewerProvider, TranslationManager translationManager) {
        super(viewerProvider, translationManager);
    }

    @Override
    public Result<MobEntity, Notification> parse(LiteInvocation invocation, String argument, Translation translation) {
        MobEntity mob = new MobEntity(MobType.UNDEFINED);

        Option.runThrowing(() -> {
            MobType mobType = MobType.valueOf(argument.toUpperCase());

            if (!mobType.isParseable()) {
                return;
            }

            mob.setMobType(mobType);
        });

        if (mob.getMobType() == MobType.UNDEFINED) {

            Option.runThrowing(() -> {
                EntityType entityType = EntityType.valueOf(argument.toUpperCase());

                if (!EntityUtil.isMob(entityType)) {
                    return;
                }

                mob.setMobType(MobType.OTHER);
                mob.setMobClass(entityType.getEntityClass());
            });

        }

        return Result.when(mob.getMobType() != MobType.UNDEFINED, mob, translation.argument().noArgument());
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
