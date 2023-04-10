package com.eternalcode.core.configuration.composer;

import net.dzikoysk.cdn.serdes.Composer;
import net.dzikoysk.cdn.serdes.SimpleDeserializer;
import net.dzikoysk.cdn.serdes.SimpleSerializer;

public interface SimpleComposer<T> extends Composer<T>, SimpleDeserializer<T>, SimpleSerializer<T> {

}
