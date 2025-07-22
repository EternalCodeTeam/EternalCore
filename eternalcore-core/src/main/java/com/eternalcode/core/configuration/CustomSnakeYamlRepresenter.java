package com.eternalcode.core.configuration;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.DumperOptions.ScalarStyle;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.representer.Representer;

import java.util.*;

class CustomSnakeYamlRepresenter extends Representer {

    public CustomSnakeYamlRepresenter(DumperOptions options) {
        super(options);
        this.representers.put(String.class, data -> representScalar(Tag.STR, (String) data, ScalarStyle.DOUBLE_QUOTED));
    }

    /**
     * This method is overridden to change the representation of maps. Specifically, it sets the keys of the map to
     * plain style if they are strings.
     *
     * @see Representer#representMapping(Tag, Map, DumperOptions.FlowStyle)
     */
    @Override
    protected Node representMapping(Tag tag, Map<?, ?> mapping, DumperOptions.FlowStyle flowStyle) {
        List<NodeTuple> value = new ArrayList<>(mapping.size());
        MappingNode node = new MappingNode(tag, value, flowStyle);
        representedObjects.put(objectToRepresent, node);
        DumperOptions.FlowStyle bestStyle = FlowStyle.FLOW;
        for (Map.Entry<?, ?> entry : mapping.entrySet()) {
            Node nodeKey = entry.getKey() instanceof String text // EternalCode: START - set plain style for keys
                ? representScalar(Tag.STR, text, ScalarStyle.PLAIN)
                : representData(entry.getKey()); // EternalCode: END
            Node nodeValue = representData(entry.getValue());
            if (!(nodeKey instanceof ScalarNode && ((ScalarNode) nodeKey).isPlain())) {
                bestStyle = FlowStyle.BLOCK;
            }
            if (!(nodeValue instanceof ScalarNode && ((ScalarNode) nodeValue).isPlain())) {
                bestStyle = FlowStyle.BLOCK;
            }
            value.add(new NodeTuple(nodeKey, nodeValue));
        }
        if (flowStyle == FlowStyle.AUTO) {
            if (defaultFlowStyle != FlowStyle.AUTO) {
                node.setFlowStyle(defaultFlowStyle);
            } else {
                node.setFlowStyle(bestStyle);
            }
        }
        return node;
    }

}
