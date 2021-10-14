package edu.westga.weatherapp_gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.Test;

import edu.westga.weatherapp_gui.model.DateTimeConverter;

public class DateTimeConverterTests {
    
    @Test
    public void convertUtcToDayOfWeek() {
        Long utcDate = 1631552400L;
        Long timezone = -14400L;
        String result = DateTimeConverter.ConvertUtcToDayOfWeek(utcDate, timezone);
        assertEquals("Monday", result);
    }

    @Test
    public void convertUtcToShortDate() {
        Long utcDate = 1631552400L;
        Long timezone = -14400L;
        String result = DateTimeConverter.ConvertUtcToShortDate(utcDate, timezone);
        assertEquals("September 13", result);
    }

    @Test
    public void formatDateThrowsExceptionWithNullFormat() {
        assertThrows(IllegalArgumentException.class, () -> {
            DateTimeConverter.FormatDate(new Date(), null);
        });
    }

    @Test
    public void formatDateThrowsExceptionWithEmptyFormat() {
        assertThrows(IllegalArgumentException.class, () -> {
            DateTimeConverter.FormatDate(new Date(), "");
        });
    }

    @Test
    public void formatDateValid() {
        Date testDate = new Date(1631552400L);
        String format = "EEEE";
        String formattedDate = DateTimeConverter.FormatDate(testDate, format);
        assertEquals("Monday", formattedDate);
    }

    @Test
    public void convertUtcToDateValid() {
        Long utcDate = 1631552400L;
        Long timezone = -14400L;
        Date result = DateTimeConverter.ConvertUtcToDate(utcDate, timezone);
        Date expected = Date.from(Instant.ofEpochMilli(1631538000000L));
        assertEquals(expected, result);
    }

    @Test
    public void convertUtcToHourValid() {
        Long utcDate = 1631552400L;
        Long timezone = -14400L;
        String result = DateTimeConverter.ConvertUtcToHour(utcDate, timezone);
        String expected = "1:00 PM";
        assertEquals(expected, result);
    }
}
