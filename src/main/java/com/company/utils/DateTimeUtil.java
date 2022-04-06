package com.company.utils;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final  DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static String LocalDateTimeToString (LocalDateTime localDateTime) {
        return localDateTime.format(formatter3);
    }
}
