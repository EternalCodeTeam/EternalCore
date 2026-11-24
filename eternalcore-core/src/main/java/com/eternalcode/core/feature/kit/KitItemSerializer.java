package com.eternalcode.core.feature.kit;

import com.eternalcode.core.injector.annotations.component.Service;
import de.eldoria.jacksonbukkit.JacksonPaper;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

@Service
public class KitItemSerializer {

    private final JsonMapper mapper = JsonMapper.builder()
        .addModule(JacksonPaper.builder().build())
        .build();

    public String serialize(List<ItemStack> items) {
        return this.mapper.writeValueAsString(items);
    }

    public List<ItemStack> deserialize(String serialized) {
        try {
            return this.mapper.readValue(serialized, new TypeReference<>() {});
        }
        catch (JacksonException exception) {
            throw new IllegalStateException("Corrupted serialized kit items", exception);
        }
    }

    public String serializeItem(ItemStack item) {
        return this.mapper.convertValue(item, String.class);
    }

    public ItemStack deserializeItem(String serialized) {
        try {
            return this.mapper.convertValue(serialized, ItemStack.class);
        }
        catch (JacksonException | IllegalArgumentException exception) {
            throw new IllegalStateException("Corrupted serialized kit item", exception);
        }
    }
}
