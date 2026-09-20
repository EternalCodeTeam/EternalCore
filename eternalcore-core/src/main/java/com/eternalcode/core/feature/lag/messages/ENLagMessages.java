package com.eternalcode.core.feature.lag.messages;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class ENLagMessages extends OkaeriConfig implements LagMessages {

    @Comment({
            " ",
            "# {TPS-1M} - TPS over the last 1 minute",
            "# {TPS-5M} - TPS over the last 5 minutes",
            "# {TPS-15M} - TPS over the last 15 minutes",
            "# {MSPT} - Average tick time in milliseconds",
            "# {MEMORY-USED} - Used memory in megabytes",
            "# {MEMORY-MAX} - Max allocated memory in megabytes",
            "# {MEMORY-FREE} - Free memory in megabytes",
            "# {UPTIME} - Server uptime",
            "# {ONLINE} - Online players count",
            "# {MAX-PLAYERS} - Max players slots",
            "# {CORES} - Available CPU cores"
    })
    public List<String> info = List.of(
            "<color:#9d6eef>► <white>TPS: {TPS-1M}<gray>, {TPS-5M}<gray>, {TPS-15M}",
            "<color:#9d6eef>► <white>MSPT: {MSPT}",
            "<color:#9d6eef>► <white>Memory: <color:#9d6eef>{MEMORY-USED}MB <white>/<color:#9d6eef>{MEMORY-MAX}MB<gray>({MEMORY-FREE}MB free)",
            "<color:#9d6eef>► <white>Uptime: <color:#9d6eef>{UPTIME}",
            "<color:#9d6eef>► <white>Players: <color:#9d6eef>{ONLINE}<white>/<color:#9d6eef>{MAX-PLAYERS}",
            "<color:#9d6eef>► <white>CPU cores: <color:#9d6eef>{CORES}");

    @Comment({
        " ",
        "# Header shown above the per-world statistics"
    })
    public String worldsHeader = "<color:#9d6eef>► <white>Worlds:";

    @Comment({
        " ",
        "# {WORLD} - World name",
        "# {CHUNKS} - Loaded chunks count",
        "# {ENTITIES} - Entities count",
        "# {TILE-ENTITIES} - Tile (block) entities count",
        "# {PLAYERS} - Players currently in this world"
    })
    public String worldEntry = "  <gray>- <color:#9d6eef>{WORLD} <white>» <gray>chunks: <color:#9d6eef>{CHUNKS}"
        + "<gray>, entities: <color:#9d6eef>{ENTITIES}<gray>, tile entities: <color:#9d6eef>{TILE-ENTITIES}"
        + "<gray>, players: <color:#9d6eef>{PLAYERS}";
}
