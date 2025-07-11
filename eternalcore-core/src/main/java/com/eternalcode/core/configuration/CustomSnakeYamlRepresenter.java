package com.eternalcode.core.configuration;

import java.util.*;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.DumperOptions.ScalarStyle;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

class CustomSnakeYamlRepresenter extends Representer {

    public CustomSnakeYamlRepresenter(DumperOptions options) {
        super(options);

        this.representers.put(Integer.class, data ->
            representScalar(Tag.INT, data.toString(), ScalarStyle.PLAIN)
        );

        this.representers.put(Boolean.class, data ->
            representScalar(Tag.BOOL, data.toString(), ScalarStyle.PLAIN)
        );

        this.representers.put(String.class, data ->
            representString((String) data)
        );

        this.representers.put(ArrayList.class, data ->
            representList(data)
        );
        this.representers.put(Collection.class, data ->
            representList(data)
        );
        this.representers.put(List.of().getClass(), data ->
            representList(data)
        );
        this.representers.put(Collections.emptyMap().getClass(), data ->
            representMap(Collections.emptyMap())
        );

        this.representers.put(Map.class, data ->
            representMap((Map<?, ?>) data)
        );
        this.representers.put(HashMap.class, data ->
            representMap((Map<?, ?>) data)
        );
        this.representers.put(LinkedHashMap.class, data ->
            representMap((Map<?, ?>) data)
        );

        this.representers.put(Collections.emptyMap().getClass(), data ->
            representScalar(Tag.STR, "", ScalarStyle.DOUBLE_QUOTED)
        );
    }

    private Node representList(Object data) {
        List<?> list = (List<?>) data;
        FlowStyle style = list.isEmpty() ? FlowStyle.FLOW : FlowStyle.BLOCK;
        return representSequence(getTag(data.getClass(), Tag.SEQ), list, style);
    }

    private Node representMap(Map<?, ?> data) {
        return representMapping(Tag.MAP, data, FlowStyle.BLOCK);
    }

    private Node representString(String data) {
        return representScalar(Tag.STR, data, data.contains(" ") ? ScalarStyle.DOUBLE_QUOTED : ScalarStyle.PLAIN);
    }
}
