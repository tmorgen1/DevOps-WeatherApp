package edu.westga.weatherapp_service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

import edu.westga.weatherapp_service.model.SevereWeatherWarning;

public class SevereWeatherWarningTest {

    @Test
    public void TestDefaultConstructorValuesNotNullOrEmptyWithDescriptionWithBreaks() {
        String forecasterName = "Weather Channel";
        String warningName = "Excessive Georgia being weird.";
        String start = "1631493385";
        String end = "1631493378";
        String description = "Georgia decided to go crazy.\n Stay at home until the state calms down.\n So about a day or so.";
        SevereWeatherWarning testWarning = new SevereWeatherWarning(forecasterName, warningName, start, end,
                description);
        String testStart = SevereWeatherWarning.DATE_FORMAT.format(new Date(Long.parseLong(start)));
        String testEnd = SevereWeatherWarning.DATE_FORMAT.format(new Date(Long.parseLong(end)));
        String testDescription = description.replace("\n", System.lineSeparator());

        assertAll(() -> assertEquals(forecasterName, testWarning.getForecasterName()),
                () -> assertEquals(warningName, testWarning.getWarningName()),
                () -> assertEquals(testStart, testWarning.getStart()),
                () -> assertEquals(testEnd, testWarning.getEnd()),
                () -> assertEquals(testDescription, testWarning.getDescription()));
    }

    @Test
    public void TestDefaultConstructorValuesNotNullOrEmptyWithDescriptionWithoutBreaks() {
        String forecasterName = "Weather Channel";
        String warningName = "Excessive Georgia being weird.";
        String start = "1631493385";
        String end = "1631493378";
        String description = "Georgia decided to go crazy.";
        SevereWeatherWarning testWarning = new SevereWeatherWarning(forecasterName, warningName, start, end,
                description);
        String testStart = SevereWeatherWarning.DATE_FORMAT.format(new Date(Long.parseLong(start)));
        String testEnd = SevereWeatherWarning.DATE_FORMAT.format(new Date(Long.parseLong(end)));
        assertAll(() -> assertEquals(forecasterName, testWarning.getForecasterName()),
                () -> assertEquals(warningName, testWarning.getWarningName()),
                () -> assertEquals(testStart, testWarning.getStart()),
                () -> assertEquals(testEnd, testWarning.getEnd()),
                () -> assertEquals(description, testWarning.getDescription()));
    }

    @Test
    public void TestDefaultConstructorValuesAreNull() {
        SevereWeatherWarning testWarning = new SevereWeatherWarning(null, null, null, null, null);
        assertAll(() -> assertEquals(SevereWeatherWarning.UNKNOWN_FORECASTER_NAME, testWarning.getForecasterName()),
                () -> assertEquals(SevereWeatherWarning.UNKNOWN_WARNING, testWarning.getWarningName()),
                () -> assertEquals(SevereWeatherWarning.UNKNOWN_START, testWarning.getStart()),
                () -> assertEquals(SevereWeatherWarning.UNKNOWN_END, testWarning.getEnd()),
                () -> assertEquals(SevereWeatherWarning.UNKNOWN_DESCRIPTION, testWarning.getDescription()));
    }

    @Test
    public void TestDefaultConstructorValuesAreEmpty() {
        String forecasterName = "";
        String warningName = "";
        String start = "";
        String end = "";
        String description = "";
        SevereWeatherWarning testWarning = new SevereWeatherWarning(forecasterName, warningName, start, end,
                description);
        assertAll(() -> assertEquals(SevereWeatherWarning.UNKNOWN_FORECASTER_NAME, testWarning.getForecasterName()),
                () -> assertEquals(SevereWeatherWarning.UNKNOWN_WARNING, testWarning.getWarningName()),
                () -> assertEquals(SevereWeatherWarning.UNKNOWN_START, testWarning.getStart()),
                () -> assertEquals(SevereWeatherWarning.UNKNOWN_END, testWarning.getEnd()),
                () -> assertEquals(SevereWeatherWarning.UNKNOWN_DESCRIPTION, testWarning.getDescription()));
    }
}
