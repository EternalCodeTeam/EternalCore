package com.eternalcode.core.notice;

import dev.rollczi.litecommands.shared.EstimatedTemporalAmountParser;
import net.dzikoysk.cdn.CdnSettings;
import net.dzikoysk.cdn.CdnUtils;
import net.dzikoysk.cdn.model.*;
import net.dzikoysk.cdn.module.standard.StandardOperators;
import net.dzikoysk.cdn.reflect.TargetType;
import net.dzikoysk.cdn.serdes.Composer;
import org.bukkit.SoundCategory;
import panda.std.Option;
import panda.std.Result;

import java.time.Duration;
import java.util.List;

public class NoticeComposer implements Composer<Notice> {

    private static final String EMPTY_NOTICE = "[]";
    private static final String TIMES = "%s %s %s";
    private static final String MUSIC_WITH_CATEGORY = "%s %s %s %s";
    private static final String MUSIC_WITHOUT_CATEGORY = "%s %s %s";

    private static final EstimatedTemporalAmountParser<Duration> DURATION_PARSER = EstimatedTemporalAmountParser.TIME_UNITS;

    @Override
    public Result<? extends Element<?>, ? extends Exception> serialize(CdnSettings settings, List<String> description, String key, TargetType type, Notice entity) {
        Context context = new Context(settings, description, key, type, entity);

        return serializeEmpty(context)
            .orElse(serializerUndisclosedChat(context))
            .orElse(serializeSection(context))
            .toResult(() -> new RuntimeException("Unknown notice type"));
    }

    private Option<Element<?>> serializeEmpty(Context context) {
        if (context.notice.parts().isEmpty()) {
            return Option.of(empty(context));
        }

        return Option.none();
    }

    private Option<Element<?>> serializerUndisclosedChat(Context context) {
        List<Notice.Part<?>> parts = context.notice.parts();

        if (parts.size() != 1) {
            return Option.none();
        }

        Notice.Part<?> part = parts.get(0);

        if (part.type() != NoticeType.CHAT) {
            return Option.none();
        }

        if (!(part.content() instanceof NoticeContent.Text text)) {
            return Option.none();
        }

        List<String> messages = text.messages();

        if (messages.isEmpty()) {
            return Option.of(empty(context));
        }

        if (messages.size() == 1) {
            return Option.of(oneLine(context.key, context.description, messages.get(0)));
        }

        return Option.of(toArray(context.key, context.description, messages));
    }

    private Option<Element<?>> serializeSection(Context context) {
        List<Notice.Part<?>> parts = context.notice.parts();
        Section section = new Section(context.description, context.key);

        for (Notice.Part<?> part : parts) {
            String key = part.type().name();

            if (part.content() instanceof NoticeContent.Text text) {
                List<String> messages = text.messages();

                if (messages.isEmpty()) {
                    continue;
                }

                if (messages.size() == 1) {
                    section.append(oneLine(key, List.of(), messages.get(0)));
                    continue;
                }

                section.append(toArray(key, List.of(), messages));
                continue;
            }

            if (part.content() instanceof NoticeContent.Times times) {
                String textTimes = TIMES.formatted(
                    DURATION_PARSER.format(times.fadeIn()),
                    DURATION_PARSER.format(times.stay()),
                    DURATION_PARSER.format(times.fadeOut())
                );

                section.append(new Entry(context.description, key, new Piece(textTimes)));
                continue;
            }

            if (part.content() instanceof NoticeContent.None) {
                for (NoticeType<?> type : NoticeType.VALUES) {
                    if (type != part.type()) {
                        continue;
                    }

                    section.append(new Entry(context.description, key, "true"));
                }

                continue;
            }

            if (part.content() instanceof NoticeContent.Music music) {
                SoundCategory category = music.category();

                Entry entry = category == null
                    ?
                    new Entry(context.description, key, new Piece(MUSIC_WITHOUT_CATEGORY.formatted(
                        music.sound().name(),
                        String.valueOf(music.pitch()),
                        String.valueOf(music.volume())
                    )))
                    :
                    new Entry(context.description, key, new Piece(MUSIC_WITH_CATEGORY.formatted(
                        music.sound().name(),
                        category.name(),
                        String.valueOf(music.pitch()),
                        String.valueOf(music.volume())
                    )));

                section.append(entry);
                continue;
            }

            throw new UnsupportedOperationException("Unsupported notice type: " + part.type());
        }

        return Option.of(section);
    }

    private static Element<?> empty(Context context) {
        return oneLine(context.key, context.description, EMPTY_NOTICE);
    }

    private static Element<?> oneLine(String key, List<String> description, String value) {
        return key == null || key.isEmpty() ? new Piece(value) : new Entry(description, key, value);
    }

    private static Array toArray(String key, List<String> description, List<String> elements) {
        Array array = new Array(description, key);

        for (String message : elements) {
            array.append(new Piece(StandardOperators.ARRAY + " " + CdnUtils.stringify(true, message)));
        }

        return array;
    }

    @Override
    public Result<Notice, Exception> deserialize(CdnSettings settings, Element<?> source, TargetType type, Notice defaultValue, boolean entryAsRecord) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private record Context(CdnSettings settings, List<String> description, String key, TargetType type, Notice notice) {
    }

}
