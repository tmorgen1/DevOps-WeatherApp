package edu.westga.weatherapp_gui.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * UTC date time converter
 * 
 * @author Michael Pavich
 */
public class DateTimeConverter {

    /**
     * The hour 0
     */
    private static final int HOUR_0 = 0;

    /**
     * The hour 12
     */
    private static final int HOUR_12 = 12;

    /**
     * The hour 12 formatted to a time properly
     */
    private static final String NOON_FORMATTED = "12:00 ";

    /**
     * Converts the given utc date time and timezone to the day of the week name
     * 
     * @param utcDateTime - the specified utc date time
     * @param timezone - the specified timezone
     * @return the day of the week name
     */
    public static String convertUtcToDayOfWeek(Long utcDateTime, Long timezone) {
        Date date = DateTimeConverter.convertUtcToDate(utcDateTime, timezone);
        return DateTimeConverter.formatDate(date, "EEEE");
    }

    /**
     * Converts the given utc date time and timezone to the hour
     * 
     * @param utcDateTime - the specified utc date time
     * @param timezone - the specified timezone
     * @return the hour
     */
    public static String convertUtcToHour(Long utcDateTime, Long timezone) {
        Date date = DateTimeConverter.convertUtcToDate(utcDateTime, timezone);
        long hour = (date.getTime() % 86400000) / 3600000;
        String formattedHour = (hour == HOUR_0) 
            ? NOON_FORMATTED + GuiConstants.AM_SUFFIX 
            : (hour % HOUR_12 == HOUR_0) 
            ? NOON_FORMATTED + GuiConstants.PM_SUFFIX 
            : hour % HOUR_12 + ":00 " + ((hour >= HOUR_12) 
            ? GuiConstants.PM_SUFFIX : GuiConstants.AM_SUFFIX);
        return formattedHour;
    }

    /**
     * Converts the given utc to a short date string of the month and day.
     * 
     * @param utcDateTime - the utc date time number
     * @param timezone - the timezone number
     * @return - the shorthand date
     */
    public static String convertUtcToShortDate(Long utcDateTime, Long timezone) {
        Date date = DateTimeConverter.convertUtcToDate(utcDateTime, timezone);
        return DateTimeConverter.formatDate(date, "MMMM d");
    }

    /**
     * Formats the given date to the specifed format
     * 
     * @param date - the date
     * @param format - the date format
     * @return - the formatted date
     */
    public static String formatDate(Date date, String format) {
        if (format == null) {
            throw new IllegalArgumentException("Format cannot be null");
        }
        if (format.isEmpty()) {
            throw new IllegalArgumentException("Format cannot be empty");
        }

        DateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    /**
     * Converts the given utc date to a date object
     * 
     * @param utcDateTime - the utc date
     * @param timezone - the timezone
     * @return - the date object
     */
    public static Date convertUtcToDate(Long utcDateTime, Long timezone) {
        return new Date(utcDateTime * 1000 + (timezone * 1000));
    }
}
