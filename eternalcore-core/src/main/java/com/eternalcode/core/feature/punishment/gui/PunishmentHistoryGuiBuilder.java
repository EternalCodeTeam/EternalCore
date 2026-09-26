package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.commons.concurrent.FutureHandler;
import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.List;

@Service
class PunishmentHistoryGuiBuilder {

    private static final int FIRST_PAGE = 0;
    private static final int EMPTY_ITEM_SLOT_INDEX = 0;
    private static final int HUMAN_PAGE_OFFSET = 1;
    private static final String PAGE_PLACEHOLDER = "{PAGE}";
    private static final String FILTER_PLACEHOLDER = "{FILTER}";

    private final MenuRenderer menuRenderer;
    private final PunishmentHistoryItemFactory itemFactory;
    private final MiniMessage miniMessage;
    private final Scheduler scheduler;

    @Inject
    PunishmentHistoryGuiBuilder(
        MenuRenderer menuRenderer,
        PunishmentHistoryItemFactory itemFactory,
        MiniMessage miniMessage,
        Scheduler scheduler
    ) {
        this.menuRenderer = menuRenderer;
        this.itemFactory = itemFactory;
        this.miniMessage = miniMessage;
        this.scheduler = scheduler;
    }

    void open(Player viewer, String titleTemplate, PunishmentHistoryGuiSession session) {
        this.openPage(viewer, titleTemplate, session, FIRST_PAGE);
    }

    private void openPage(Player viewer, String titleTemplate, PunishmentHistoryGuiSession session, int page) {
        session.loadPage(page)
            .thenRun(() -> this.scheduler.run(() -> this.render(viewer, titleTemplate, session, page)))
            .exceptionally(FutureHandler::handleException);
    }

    private void render(Player viewer, String titleTemplate, PunishmentHistoryGuiSession session, int requestedPage) {
        if (!viewer.isOnline()) {
            return;
        }

        int page = Math.min(requestedPage, session.lastPage());
        this.menuRenderer.open(viewer, this.createMenu(titleTemplate, session, page));
    }

    private Menu createMenu(String titleTemplate, PunishmentHistoryGuiSession session, int page) {
        PunishmentHistoryGuiLayout layout = session.layout();
        Component title = this.miniMessage.deserialize(titleTemplate
            .replace(PAGE_PLACEHOLDER, String.valueOf(page + HUMAN_PAGE_OFFSET))
            .replace(FILTER_PLACEHOLDER, this.itemFactory.filterLabel(session.filter())));

        Menu.Builder menu = Menu.builder(title, layout.rows())
            .items(layout.borderSlots(), this.itemFactory.border());

        this.placeEntries(menu, layout, session.page(page));
        this.placeNavigation(menu, layout, session, titleTemplate, page);
        this.placeFilter(menu, layout, session, titleTemplate);

        return menu.build();
    }

    private void placeEntries(Menu.Builder menu, PunishmentHistoryGuiLayout layout, List<Punishment> punishments) {
        List<Integer> slots = layout.contentSlots();

        if (punishments.isEmpty()) {
            menu.item(slots.get(EMPTY_ITEM_SLOT_INDEX), this.itemFactory.empty());
            return;
        }

        Instant now = Instant.now();
        for (int index = 0; index < punishments.size(); index++) {
            menu.item(slots.get(index), this.itemFactory.punishment(punishments.get(index), now));
        }
    }

    private void placeNavigation(
        Menu.Builder menu,
        PunishmentHistoryGuiLayout layout,
        PunishmentHistoryGuiSession session,
        String titleTemplate,
        int page
    ) {
        if (page > FIRST_PAGE) {
            menu.item(layout.previousPageSlot(), this.itemFactory.previousPage(
                clicker -> this.openPage(clicker, titleTemplate, session, page - 1)));
        }

        if (session.hasNextPage(page)) {
            menu.item(layout.nextPageSlot(), this.itemFactory.nextPage(
                clicker -> this.openPage(clicker, titleTemplate, session, page + 1)));
        }
    }

    private void placeFilter(
        Menu.Builder menu,
        PunishmentHistoryGuiLayout layout,
        PunishmentHistoryGuiSession session,
        String titleTemplate
    ) {
        PunishmentHistoryFilter current = session.filter();

        menu.item(layout.filterSlot(), this.itemFactory.filter(current, clicker ->
            this.openPage(clicker, titleTemplate, session.withFilter(current.next()), FIRST_PAGE)));
    }
}
