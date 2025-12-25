package com.corridasonline.api.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class DateTimeUtil {

    public static final ZoneId ZONE_FORTALEZA = ZoneId.of("America/Fortaleza");

    private DateTimeUtil() {
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(ZONE_FORTALEZA);
    }

    public static LocalDate today() {
        return LocalDate.now(ZONE_FORTALEZA);
    }

    public static LocalTime currentTime() {
        return LocalTime.now(ZONE_FORTALEZA);
    }

    public static ZonedDateTime zonedNow() {
        return ZonedDateTime.now(ZONE_FORTALEZA);
    }

}
