package com.eternalcode.core.command.argument;

import com.eternalcode.core.feature.essentials.mob.MobType;
import com.eternalcode.core.notification.Notification;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.viewer.BukkitViewerProvider;
import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.entity.EntityType;
import panda.std.Option;
import panda.std.Result;
import panda.std.function.ThrowingSupplier;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ArgumentName("mobtype")
public class MobTypeArgument extends AbstractViewerArgument<MobType> {

    public MobTypeArgument(BukkitViewerProvider viewerProvider, TranslationManager translationManager) {
        super(viewerProvider, translationManager);
    }

    @Override
    public Result<MobType, Notification> parse(LiteInvocation invocation, String argument, Translation translation) {
        AtomicReference<MobType> atomicMobType = new AtomicReference<>(MobType.UNDEFINED);

        this.tryParseMob(atomicMobType, () -> {
            MobType mobType = MobType.valueOf(argument.toUpperCase());
            return mobType.isParseable() ? mobType : null;
        });

        this.tryParseMob(atomicMobType, () -> {
            EntityType entityType = EntityType.valueOf(argument.toUpperCase());

            if (!MobType.isMob(entityType)) {
                return null;
            }

            MobType mobType = MobType.OTHER;
            mobType.setMobClass(entityType.getEntityClass());

            return mobType;
        });

        MobType mobType = atomicMobType.get();
        return Result.when(mobType != MobType.UNDEFINED, mobType, translation.argument().noArgument());
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        Stream<MobType> mobTypeStream = Stream.of(MobType.values()).filter(MobType::isSuggeestable);
        Stream<EntityType> entityTypeStream = Stream.of(EntityType.values()).filter(MobType::isMob);

        return Stream.concat(entityTypeStream, mobTypeStream)
            .map(Enum::name)
            .map(String::toLowerCase)
            .map(Suggestion::of)
            .toList();
    }

    private void tryParseMob(AtomicReference<MobType> reference, ThrowingSupplier<MobType, IllegalArgumentException> supplier) {
        if (reference.get() != MobType.UNDEFINED) {
            return;
        }

        Option<MobType> option = Option.supplyThrowing(IllegalArgumentException.class, supplier)
            .orElse(MobType.UNDEFINED);

        reference.set(option.get());
    }
}
