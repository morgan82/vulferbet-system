package com.ml.vulferbetsystem.utils;

import java.time.Duration;
import java.time.LocalDate;

public class DateUtils {
    //TODO:mejorar
    public static long getDaysBetweenTodayAndOtherDate(LocalDate otherDate) {

        Duration duration = Duration.between(LocalDate.now().atStartOfDay(), otherDate.atStartOfDay());

        return Math.abs(duration.toDays());
    }

}
