package com.eternalcode.core.util.date;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class DateFormatterImpl implements DateFormatter {

    private final DateSettings dateSettings;
    private DateTimeFormatter cachedFormatter;
    private String lastFormat;

    @Inject
    public DateFormatterImpl(DateSettings dateSettings) {
        this.dateSettings = dateSettings;
    }

    @Override
    public String format(Instant instant) {
        if (instant == null) {
            return "N/A";
        }

        String currentFormat = this.dateSettings.format();
        if (this.cachedFormatter == null || !currentFormat.equals(this.lastFormat)) {
            this.lastFormat = currentFormat;
            this.cachedFormatter = DateTimeFormatter.ofPattern(currentFormat)
                    .withZone(ZoneId.systemDefault());
        }

        return this.cachedFormatter.format(instant);
    }
}
