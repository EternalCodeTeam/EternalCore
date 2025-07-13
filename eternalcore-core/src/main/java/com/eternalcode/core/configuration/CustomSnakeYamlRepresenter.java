package com.eternalcode.core.configuration;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.DumperOptions.ScalarStyle;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.representer.Representer;

import java.util.*;
import java.util.function.Function;

class CustomSnakeYamlRepresenter extends Representer {

    private static ThreadLocal<Boolean> CURRENT_IS_KEY = ThreadLocal.withInitial(() -> false);

    public CustomSnakeYamlRepresenter(DumperOptions options) {
        super(options);

        this.representer(Integer.class, data -> representScalar(Tag.INT, data.toString(), ScalarStyle.PLAIN));
        this.representer(Boolean.class, data -> representScalar(Tag.BOOL, data.toString(), ScalarStyle.PLAIN));
        this.representer(String.class, data -> representScalar(Tag.STR, data, ScalarStyle.DOUBLE_QUOTED));
        this.representer(Collection.class, data -> representList(data));
        this.representer(Map.class, data -> representMap(data));
    }



    @SuppressWarnings("unchecked")
    private <T> void representer(Class<T> type, Function<T, Node> representer) {
        this.representers.remove(type);
        this.multiRepresenters.put(type, data -> representer.apply((T) data));
    }

    private Node representList(Object data) {
        List<?> list = (List<?>) data;
        FlowStyle style = list.isEmpty() ? FlowStyle.FLOW : FlowStyle.BLOCK;
        return representSequence(getTag(data.getClass(), Tag.SEQ), list, style);
    }

    private Node representMap(Map<?, ?> data) {
        return representMapping(Tag.MAP, data, FlowStyle.BLOCK);
    }

    @Override
    protected Node representMapping(Tag tag, Map<?, ?> mapping, DumperOptions.FlowStyle flowStyle) {
        List<NodeTuple> value = new ArrayList<>(mapping.size());
        MappingNode node = new MappingNode(tag, value, flowStyle);
        representedObjects.put(objectToRepresent, node);
        DumperOptions.FlowStyle bestStyle = FlowStyle.FLOW;
        for (Map.Entry<?, ?> entry : mapping.entrySet()) {
            Node nodeKey = entry.getKey() instanceof String text
                ? representScalar(Tag.STR, text, ScalarStyle.PLAIN)
                : representData(entry.getKey());
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
