package com.eternalcode.core.feature.lag.messages;

import eu.okaeri.configs.OkaeriConfig;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLLagMessages extends OkaeriConfig implements LagMessages {

    public List<String> info = List.of(
            "<color:#9d6eef>► <white>TPS: {TPS-1M}<gray>, {TPS-5M}<gray>, {TPS-15M}",
            "<color:#9d6eef>► <white>MSPT: {MSPT}",
            "<color:#9d6eef>► <white>Pamięć: <color:#9d6eef>{MEMORY-USED}MB <white>/ <color:#9d6eef>{MEMORY-MAX}MB <gray>({MEMORY-FREE}MB wolne)",
            "<color:#9d6eef>► <white>Uptime: <color:#9d6eef>{UPTIME}",
            "<color:#9d6eef>► <white>Gracze: <color:#9d6eef>{ONLINE}<white>/<color:#9d6eef>{MAX-PLAYERS}",
            "<color:#9d6eef>► <white>Rdzenie CPU: <color:#9d6eef>{CORES}");

    public String worldsHeader = "<color:#9d6eef>► <white>Światy:";

    public String worldEntry = "  <gray>- <color:#9d6eef>{WORLD} <white>» <gray>chunki: <color:#9d6eef>{CHUNKS}"
        + "<gray>, encje: <color:#9d6eef>{ENTITIES}<gray>, tile entities: <color:#9d6eef>{TILE-ENTITIES}"
        + "<gray>, gracze: <color:#9d6eef>{PLAYERS}";
}
