package com.eternalcode.core.kit;

import panda.std.Option;
import panda.std.stream.PandaStream;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class KitManager {

    private final Map<String, Kit> kitMap = new HashMap<>();

    public Option<Kit> findKit(String name){
        return PandaStream.of(this.kitMap.values())
            .filter(kit -> kit.getName().equals(name))
            .head();
    }

    public void addKit(Kit kit){
        this.kitMap.put(kit.getName(), kit);
    }

    public void removeKit(Kit kit){
        this.kitMap.remove(kit.getName());
    }

    public Map<String, Kit> getKitMap() {
        return Collections.unmodifiableMap(this.kitMap);
    }
}
