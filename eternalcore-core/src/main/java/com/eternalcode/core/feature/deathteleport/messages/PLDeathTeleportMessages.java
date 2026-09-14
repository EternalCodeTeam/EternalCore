package com.eternalcode.core.feature.deathteleport.messages;

import com.eternalcode.multification.notice.Notice;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class PLDeathTeleportMessages extends OkaeriConfig implements DeathTeleportMessages {

    @Comment("# {TIME} - Czas pozostały do teleportacji")
    public Notice deathTeleportStarted = Notice.actionbar(
        "<color:#9d6eef>► <white>Za <color:#9d6eef>{TIME} <white>zostaniesz przeteleportowany do miejsca swojej śmierci...");

    public Notice deathTeleportSuccess = Notice.chat(
        "<green>► <white>Zostałeś przeteleportowany do miejsca swojej śmierci!");

    public Notice deathTeleportSelfEnabled = Notice.chat(
        "<green>► <white>Automatyczna teleportacja do miejsca śmierci jest teraz <green>włączona<white>!");
    public Notice deathTeleportSelfDisabled = Notice.chat(
        "<red>► <white>Automatyczna teleportacja do miejsca śmierci jest teraz <red>wyłączona<white>!");

    public Notice deathTeleportOtherEnabled = Notice.chat(
        "<green>► <white>Automatyczna teleportacja do miejsca śmierci została <green>włączona <white>dla gracza <green>{PLAYER}<white>!");
    public Notice deathTeleportOtherDisabled = Notice.chat(
        "<red>► <white>Automatyczna teleportacja do miejsca śmierci została <red>wyłączona <white>dla gracza <green>{PLAYER}<white>!");
}
