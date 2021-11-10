package edu.westga.weatherapp_gui.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class DateTimeConverterTests {
    
    @Test
    public void convertUtcToDayOfWeek() {
        Long utcDate = 1631552400L;
        Long timezone = -14400L;
        String result = DateTimeConverter.convertUtcToDayOfWeek(utcDate, timezone);
        assertEquals("Monday", result);
    }

    @Test
    public void convertUtcToShortDate() {
        Long utcDate = 1631552400L;
        Long timezone = -14400L;
        String result = DateTimeConverter.convertUtcToShortDate(utcDate, timezone);
        assertEquals("September 13", result);
    }

    @Test
    public void formatDateThrowsExceptionWithNullFormat() {
        assertThrows(IllegalArgumentException.class, () -> {
            DateTimeConverter.formatDate(new Date(), null);
        });
    }

    @Test
    public void formatDateThrowsExceptionWithEmptyFormat() {
        assertThrows(IllegalArgumentException.class, () -> {
            DateTimeConverter.formatDate(new Date(), "");
        });
    }

    @Test
    public void formatDateValid() {
        Date testDate = new Date(1631552400L);
        String format = "EEEE";
        String formattedDate = DateTimeConverter.formatDate(testDate, format);
        assertEquals("Monday", formattedDate);
    }

    @Test
    public void convertUtcToDateValid() {
        Long utcDate = 1631552400L;
        Long timezone = -14400L;
        Date result = DateTimeConverter.convertUtcToDate(utcDate, timezone);
        Date expected = Date.from(Instant.ofEpochMilli(1631538000000L));
        assertEquals(expected, result);
    }

    @Test
    public void convertUtcToHourValid() {
        Long utcDate = 1631552400L;
        Long timezone = -14400L;
        String result = DateTimeConverter.convertUtcToHour(utcDate, timezone);
        String expected = "1:00 PM";
        assertEquals(expected, result);
    }
}
