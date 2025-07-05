package com.eternalcode.core.feature.setslot;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.util.ReflectUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Blocking;

@Service
public class SetSlotService {

    private final Plugin plugin;
    private Field maxPlayersField;
    private final SetSlotSaver setSlotSaver;

    @Inject
    public SetSlotService(Plugin plugin, SetSlotSaver setSlotSaver) {
        this.plugin = plugin;
        this.setSlotSaver = setSlotSaver;
    }

    @Blocking
    public void setCapacity(int slots) {
        Object playerList = getPlayerList();

        if (this.maxPlayersField == null) {
            this.maxPlayersField = findMaxPlayersField(playerList);
        }

        this.setMaxPlayers(playerList, slots);
        this.setSlotSaver.save();
    }

    private Object getPlayerList() {
        Server server = this.plugin.getServer();
        Method serverGetHandle = ReflectUtil.getDeclaredMethod(server.getClass(), "getHandle");

        return ReflectUtil.invokeMethod(serverGetHandle, server);
    }

    private Field findMaxPlayersField(Object playerList) {
        Class<?> playerListClass = playerList.getClass().getSuperclass();

        for (Field field : playerListClass.getDeclaredFields()) {
            if (field.getType() == int.class) {
                field.setAccessible(true);
                try {
                    if (field.getInt(playerList) == this.plugin.getServer().getMaxPlayers()) {
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

