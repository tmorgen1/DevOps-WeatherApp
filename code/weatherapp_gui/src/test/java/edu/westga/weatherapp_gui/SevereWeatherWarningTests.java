package edu.westga.weatherapp_gui;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.westga.weatherapp_gui.model.DateTimeConverter;
import edu.westga.weatherapp_gui.model.SevereWeatherWarning;

public class SevereWeatherWarningTests {

    @Test
    public void TestConstuctorNullInputs() {
        SevereWeatherWarning warning = new SevereWeatherWarning(null, null, null, null, null);
        assertAll(() -> {
            assertEquals(SevereWeatherWarning.UNDETERMINED_WARNING, warning.getWarningName());
        }, () -> {
            assertEquals(SevereWeatherWarning.UNDETERMINED_START, warning.getStartingDate());
        }, () -> {
            assertEquals(SevereWeatherWarning.UNDETERMINED_END, warning.getEndingDate());
        }, () -> {
            assertEquals(SevereWeatherWarning.UNDETERMINED_DETAILS, warning.getDetails());
        }, () -> {
            assertEquals(SevereWeatherWarning.DEFAULT_TIMEZONE, warning.getTimeZone());
        });
    }

    @Test
    public void TestConstuctorEmptyInputs() {
        SevereWeatherWarning warning = new SevereWeatherWarning("", "", "", "", "");
        assertAll(() -> {
            assertEquals(SevereWeatherWarning.UNDETERMINED_WARNING, warning.getWarningName());
        }, () -> {
            assertEquals(SevereWeatherWarning.UNDETERMINED_START, warning.getStartingDate());
        }, () -> {
            assertEquals(SevereWeatherWarning.UNDETERMINED_END, warning.getEndingDate());
        }, () -> {
            assertEquals(SevereWeatherWarning.UNDETERMINED_DETAILS, warning.getDetails());
        }, () -> {
            assertEquals(SevereWeatherWarning.DEFAULT_TIMEZONE, warning.getTimeZone());
        });
    }

    @Test
    public void TestConstuctorValidInputs() {
        SevereWeatherWarning warning = new SevereWeatherWarning("Heat Advisory", "1631722620", "1631856600", "Its hot.", "-2500");
        Long timezone = Long.parseLong("-2500");
        assertAll(() -> {
            assertEquals("Heat Advisory", warning.getWarningName());
        }, () -> {
            assertEquals(DateTimeConverter.ConvertUtcToShortDate(1631722620L, timezone), warning.getStartingDate());
        }, () -> {
            assertEquals(DateTimeConverter.ConvertUtcToShortDate(1631856600L, timezone), warning.getEndingDate());
        }, () -> {
            assertEquals("Its hot.", warning.getDetails());
        }, () -> {
            assertEquals("-2500", warning.getTimeZone());
        });
    }

}
