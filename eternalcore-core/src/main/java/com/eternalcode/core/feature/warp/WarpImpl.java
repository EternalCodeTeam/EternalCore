package com.eternalcode.core.feature.warp;

import com.eternalcode.core.shared.Position;
import com.eternalcode.core.shared.PositionAdapter;
import org.bukkit.Location;

public class WarpImpl implements Warp{

    private final String name;
    private final Position position;

    public WarpImpl(String name, Position position){
        this.name = name;
        this.position = position;
    }
    @Override
    public String getName(){
        return name;
    }
    @Override
    public Location getLocation(){
        return PositionAdapter.convert(position);
    }
}
