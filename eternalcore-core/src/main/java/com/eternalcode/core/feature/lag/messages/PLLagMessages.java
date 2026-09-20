package com.eternalcode.core.feature.lag.messages;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLLagMessages extends OkaeriConfig implements LagMessages {

    @Comment({
        " ",
        "# {TPS-1M} - TPS z ostatniej 1 minuty",
        "# {TPS-5M} - TPS z ostatnich 5 minut",
        "# {TPS-15M} - TPS z ostatnich 15 minut",
        "# {MSPT} - Średni czas ticka w milisekundach",
        "# {MEMORY-USED} - Wykorzystana pamięć w megabajtach",
        "# {MEMORY-MAX} - Maksymalnie przydzielona pamięć w megabajtach",
        "# {MEMORY-FREE} - Wolna pamięć w megabajtach",
        "# {UPTIME} - Czas działania serwera",
        "# {ONLINE} - Liczba graczy online",
        "# {MAX-PLAYERS} - Maksymalna liczba miejsc dla graczy",
        "# {CORES} - Dostępna liczba rdzeni procesora"
    })
    public List<String> info = List.of(
            "<color:#9d6eef>► <white>TPS: {TPS-1M}<gray>, {TPS-5M}<gray>, {TPS-15M}",
            "<color:#9d6eef>► <white>MSPT: {MSPT}",
            "<color:#9d6eef>► <white>Pamięć: <color:#9d6eef>{MEMORY-USED}MB <white>/ <color:#9d6eef>{MEMORY-MAX}MB <gray>({MEMORY-FREE}MB wolne)",
            "<color:#9d6eef>► <white>Uptime: <color:#9d6eef>{UPTIME}",
            "<color:#9d6eef>► <white>Gracze: <color:#9d6eef>{ONLINE}<white>/<color:#9d6eef>{MAX-PLAYERS}",
            "<color:#9d6eef>► <white>Rdzenie CPU: <color:#9d6eef>{CORES}");

    @Comment({
        " ",
        "# Nagłówek wyświetlany nad statystykami dla poszczególnych światów"
    })
    public String worldsHeader = "<color:#9d6eef>► <white>Światy:";

    @Comment({
        " ",
        "# {WORLD} - Nazwa świata",
        "# {CHUNKS} - Liczba załadowanych chunków",
        "# {ENTITIES} - Liczba encji",
        "# {TILE-ENTITIES} - Liczba encji blokowych",
        "# {PLAYERS} - Liczba graczy aktualnie znajdujących się w tym świecie"
    })
    public String worldEntry = "  <gray>- <color:#9d6eef>{WORLD} <white>» <gray>chunki: <color:#9d6eef>{CHUNKS}"
        + "<gray>, encje: <color:#9d6eef>{ENTITIES}<gray>, tile entities: <color:#9d6eef>{TILE-ENTITIES}"
        + "<gray>, gracze: <color:#9d6eef>{PLAYERS}";
}
