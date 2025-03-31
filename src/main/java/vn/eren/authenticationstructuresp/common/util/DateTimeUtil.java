package vn.eren.authenticationstructuresp.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {

    public static String formatInstant(Instant input, String format) {
        if (input == null) {
            return null;
        } else {
            DateTimeFormatter dateTimeFormatter =
                    DateTimeFormatter.ofPattern(format).withZone(ZoneId.systemDefault());
            return dateTimeFormatter.format(input);
        }
    }

    public static Date convertStringToDate(String input, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        return sdf.parse(input);
    }

    public static String convertDateToString(Date input, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        return sdf.format(input);
    }

    public static Instant convertStringToInstant(String dateStr, String format) {
        if (dateStr == null || format == null)
            return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

        TemporalAccessor temporalAccessor = formatter.parse(dateStr);
        LocalDateTime localDateTime = LocalDateTime.from(temporalAccessor);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        return Instant.from(zonedDateTime);
    }

    public static String convertInstantToString(Instant date, String format) {
        try {
            if (date == null)
                return null;
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(format).withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());
            return formatter.format(date);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format");
        }

    }

    public static String convertLocalDateTimeToString(LocalDateTime localDateTime, String format) {
        if (localDateTime == null)
            return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }


    public static String convertMillisecondsToString(Long milliseconds , String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date resultDate = new Date(milliseconds);
        return sdf.format(resultDate);
    }
}
