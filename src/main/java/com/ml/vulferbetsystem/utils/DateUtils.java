package com.ml.vulferbetsystem.utils;

import java.time.Duration;
import java.time.LocalDate;

public class DateUtils {
    /**
     * Determina la cantidad de dias que hay desde la fecha actual y otra fecha
     *
     * @param otherDate
     * @return diferencia de dias entre las fechas, siempre positivo
     */
    public static long getDaysBetweenTodayAndOtherDate(LocalDate otherDate) {

        Duration duration = Duration.between(LocalDate.now().atStartOfDay(), otherDate.atStartOfDay());

        return Math.abs(duration.toDays());
    }

}
