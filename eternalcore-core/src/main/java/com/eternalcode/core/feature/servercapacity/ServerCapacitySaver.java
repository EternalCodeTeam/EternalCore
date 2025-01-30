package com.eternalcode.core.feature.servercapacity;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import org.bukkit.Server;

@Service
public class ServerCapacitySaver {

    private static final String MAX_PLAYERS_PROPERTY = "max-players";
    private final Server server;

    @Inject
    public ServerCapacitySaver(Server server) {
        this.server = server;
    }

    public void save() {
        File propertiesFile = new File("server.properties");
        Properties properties = new Properties();

        try (InputStream is = new FileInputStream(propertiesFile)) {
            properties.load(is);

            String maxPlayers = Integer.toString(this.server.getMaxPlayers());
            if (maxPlayers.equals(properties.getProperty(MAX_PLAYERS_PROPERTY))) {
                return;
            }

            this.server.getLogger().info("Saving max players to server.properties...");
            properties.setProperty(MAX_PLAYERS_PROPERTY, maxPlayers);

            try (OutputStream os = new FileOutputStream(propertiesFile)) {
                properties.store(os, "Minecraft server properties");
            }
        }
        catch (IOException exception) {
            this.server.getLogger()
                .log(Level.SEVERE, "An error occurred while updating the server properties", exception);
        }
    }
}
