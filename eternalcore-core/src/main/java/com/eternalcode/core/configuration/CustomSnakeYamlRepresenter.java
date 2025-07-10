package com.eternalcode.core.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.DumperOptions.ScalarStyle;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

class CustomSnakeYamlRepresenter extends Representer {

    public CustomSnakeYamlRepresenter(DumperOptions options) {
        super(options);
        this.representers.put(Integer.class, data -> representScalar(Tag.INT, data.toString(), ScalarStyle.PLAIN));
        this.representers.put(ArrayList.class, data -> representList(data));
        this.representers.put(Boolean.class, data -> representScalar(Tag.BOOL, data.toString(), ScalarStyle.PLAIN));
        this.representers.put(String.class, data -> representString((String) data));
        this.representers.put(Map.class, data -> representMap((Map<?, ?>) data));
        this.representers.put(HashMap.class, data -> representMap((Map<?, ?>) data));
        this.representers.put(LinkedHashMap.class, data -> representMap((Map<?, ?>) data));
    }

    private Node representList(Object data) {
        return representSequence(getTag(data.getClass(), Tag.SEQ), (Iterable<?>) data, FlowStyle.BLOCK);
    }

    private Node representMap(Map<?, ?> data) {
        return representMapping(Tag.MAP, data, FlowStyle.BLOCK);
    }

    private Node representString(String data) {
        return representScalar(Tag.STR, data, data.contains(" ") ? ScalarStyle.DOUBLE_QUOTED :  ScalarStyle.PLAIN);
    }

}
