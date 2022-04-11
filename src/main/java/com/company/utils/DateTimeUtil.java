package com.company.utils;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final  DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("d/MM/yyyy");
    public static String LocalDateTimeToString (LocalDateTime localDateTime) {
        return localDateTime.format(formatter3);
    }
    public static LocalDate stringToLocalDate(String localDate) {
        return LocalDate.parse(localDate, formatter2);
    }
}
