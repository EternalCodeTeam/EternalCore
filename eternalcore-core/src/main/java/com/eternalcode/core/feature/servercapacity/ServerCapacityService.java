package com.eternalcode.core.feature.servercapacity;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.util.ReflectUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

@Service
public class ServerCapacityService {

    private final Plugin plugin;
    private Field maxPlayersField;
    private final ServerCapacitySaver serverCapacitySaver;

    @Inject
    public ServerCapacityService(Plugin plugin, ServerCapacitySaver serverCapacitySaver) {
        this.plugin = plugin;
        this.serverCapacitySaver = serverCapacitySaver;
    }

    public void setCapacity(int slots) {
        Object playerList = getPlayerList();

        if (maxPlayersField == null) {
            maxPlayersField = findMaxPlayersField(playerList);
        }

        this.setMaxPlayers(playerList, slots);
        this.serverCapacitySaver.save();
    }

    private Object getPlayerList() {
        Server server = plugin.getServer();
        Method serverGetHandle = ReflectUtil.getDeclaredMethod(server.getClass(), "getHandle");

        return ReflectUtil.invokeMethod(serverGetHandle, server);
    }

    private Field findMaxPlayersField(Object playerList) {
        Class<?> playerListClass = playerList.getClass().getSuperclass();

        for (Field field : playerListClass.getDeclaredFields()) {
            if (field.getType() == int.class) {
                field.setAccessible(true);
                try {
                    if (field.getInt(playerList) == plugin.getServer().getMaxPlayers()) {
                        return field;
                    }
                }
                catch (IllegalAccessException exception) {
                    throw new IllegalStateException("Error accessing field value for maxPlayers", exception);
                }
            }
        }

        throw new IllegalStateException("Unable to find maxPlayers field in " + playerListClass.getName());
    }

    private void setMaxPlayers(Object playerList, int slots) {
        try {
            this.maxPlayersField.setInt(playerList, slots);
        }
        catch (IllegalAccessException exception) {
            throw new IllegalStateException("Failed to set maxPlayers field", exception);
        }
    }
}

