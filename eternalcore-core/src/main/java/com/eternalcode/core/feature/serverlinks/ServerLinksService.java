package com.eternalcode.core.feature.serverlinks;

import static net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacySection;

import com.eternalcode.core.compatibility.Compatibility;
import com.eternalcode.core.compatibility.Version;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.net.URI;
import java.net.URISyntaxException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ServerLinks;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@Service
@Compatibility(from = @Version(minor = 21, patch = 0))
public class ServerLinksService {

    private final Plugin plugin;
    private final MiniMessage miniMessage;
    private final ServerLinksConfig config;

    @Inject
    public ServerLinksService(Plugin plugin, MiniMessage miniMessage, ServerLinksConfig config) {
        this.plugin = plugin;
        this.miniMessage = miniMessage;
        this.config = config;
    }

    public void sendServerLinks(Player player) {
        ServerLinks serverLinks = this.plugin.getServer().getServerLinks().copy();

        for (ServerLinksEntry serverLink : this.config.serverLinks) {
            this.parseLinks(serverLinks, serverLink);
        }

        player.sendLinks(serverLinks);
    }

    private URI parseUrl(String url) {
        try {
            if (!url.startsWith("https://") && !url.startsWith("http://")) {
                return null;
            }
            return new URI(url);
        }
        catch (URISyntaxException exception) {
            return null;
        }
    }

    private org.bukkit.ServerLinks.ServerLink parseLinks(org.bukkit.ServerLinks serverLinks, ServerLinksEntry links) {
        URI url = parseUrl(links.address());

        if (url == null) {
            return null;
        }

        // TODO: Use ServerLinks#addLinks(Component, URI) instead of ServerLinks#addLink(String, URI) when we use
        //  PaperAPI in nearly future.
        Component deserialize = this.miniMessage.deserialize(links.name());
        return serverLinks.addLink(legacySection().serialize(deserialize), url);
    }
}
