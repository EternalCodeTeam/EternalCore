package com.eternalcode.core.configuration.composer;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import net.dzikoysk.cdn.CdnSettings;
import net.dzikoysk.cdn.CdnUtils;
import net.dzikoysk.cdn.model.Element;
import net.dzikoysk.cdn.model.Entry;
import net.dzikoysk.cdn.model.NamedElement;
import net.dzikoysk.cdn.model.Section;
import static net.dzikoysk.cdn.module.standard.StandardOperators.ARRAY_SEPARATOR;
import net.dzikoysk.cdn.reflect.TargetType;
import net.dzikoysk.cdn.serdes.Composer;
import panda.std.Result;
import static panda.std.Result.error;
import static panda.std.Result.ok;
import panda.std.stream.PandaStream;
import static panda.utilities.StringUtils.EMPTY;

public final class SetComposer implements Composer<Set<Object>> {

    @Override
    public Result<Set<Object>, Exception> deserialize(CdnSettings settings, Element<?> source, TargetType type, Set<Object> defaultValue, boolean entryAsRecord) {
        if (source instanceof Entry) {
            Entry entry = (Entry) source;

            return CdnUtils.destringify(entry.getPieceValue()).trim().endsWith("[]")
                ? ok(Collections.emptySet())
                : error(new UnsupportedOperationException("Cannot deserialize list of " + entry));
        }

        Section section = (Section) source;
        TargetType collectionType = type.getAnnotatedActualTypeArguments()[0];

        return CdnUtils.findComposer(settings, collectionType, null)
            .flatMap(composer -> PandaStream.of(section.getValue())
                .map(element -> settings.getModule().resolveArrayValue(element))
                .map(element -> composer.deserialize(settings, element, collectionType, null, true))
                .filterToResult(Result::errorToOption)
                .map(stream -> stream.map(Result::get).toSet()));
    }

    @Override
    public Result<NamedElement<?>, Exception> serialize(CdnSettings settings, List<String> description, String key, TargetType type, Set<Object> entity) {
        if (entity.isEmpty()) {
            return ok(new Entry(description, key, "[]"));
        }

        TargetType collectionType = type.getAnnotatedActualTypeArguments()[0];

        return CdnUtils.findComposer(settings, collectionType, null)
            .flatMap(serializer -> PandaStream.of(entity)
                .map(element -> serializer.serialize(settings, Collections.emptyList(), EMPTY, collectionType, element))
                .filterToResult(Result::errorToOption)
                .map(stream -> stream
                    .map(Result::get)
                    .map(serializedElement -> settings.getModule().visitArrayValue(serializedElement))
                    .collect(Section.collector(() -> new Section(description, ARRAY_SEPARATOR, key)))));
    }

}
