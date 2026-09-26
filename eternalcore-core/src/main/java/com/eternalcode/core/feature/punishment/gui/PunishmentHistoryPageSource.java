package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.feature.punishment.Punishment;

import com.eternalcode.core.feature.punishment.PunishmentType;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

interface PunishmentHistoryPageSource {

    CompletableFuture<List<Punishment>> fetch(Set<PunishmentType> types, int page, int pageSize);
}
