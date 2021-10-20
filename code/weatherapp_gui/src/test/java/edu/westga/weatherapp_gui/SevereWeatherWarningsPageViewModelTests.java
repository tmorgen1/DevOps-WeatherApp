package edu.westga.weatherapp_gui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import edu.westga.weatherapp_gui.mocks.OpenWeatherSevereWarningsRetrieverMocks;
import edu.westga.weatherapp_gui.model.DateTimeConverter;
import edu.westga.weatherapp_gui.model.SevereWeatherWarning;
import edu.westga.weatherapp_gui.viewmodel.SevereWeatherWarningsPageViewModel;
import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import edu.westga.weatherapp_shared.model.WeatherLocation;

public class SevereWeatherWarningsPageViewModelTests {

    @Test
    public void testSevereWeatherWarningsPageViewModelConstructorGivenNullRetiever() {
        SevereWeatherWarningsPageViewModel viewModel = new SevereWeatherWarningsPageViewModel(null);
        assertAll(() -> {
            assertNotNull(viewModel);
        }, () -> {
            assertNotNull(viewModel.getErrorTextStringProperty());
        }, () -> {
            assertNotNull(viewModel.getErrorTextVisibilityProperty());
        }, () -> {
            assertNotNull(viewModel.getNoWarningsForLocationVisibilityProperty());
        }, () -> {
            assertNotNull(viewModel.getSevereWarningComboBoxVisibilityProperty());
        }, () -> {
            assertNotNull(viewModel.getSevereWarningComboBoxListProperty());
        });
    }

    @Test
    public void testSevereWeatherWarningsPageViewModelConstructorGivenRetriever() {
        SevereWeatherWarningsPageViewModel viewModel = new SevereWeatherWarningsPageViewModel(
                new OpenWeatherSevereWarningsRetrieverMocks.OpenWeatherSevereWarningRetrieverMockNoAlerts());
        assertAll(() -> {
            assertNotNull(viewModel);
        }, () -> {
            assertNotNull(viewModel.getErrorTextStringProperty());
        }, () -> {
            assertNotNull(viewModel.getErrorTextVisibilityProperty());
        }, () -> {
            assertNotNull(viewModel.getNoWarningsForLocationVisibilityProperty());
        }, () -> {
            assertNotNull(viewModel.getSevereWarningComboBoxVisibilityProperty());
        }, () -> {
            assertNotNull(viewModel.getSevereWarningComboBoxListProperty());
        });
    }

    @Test
    public void testSetSevereWeatherWarningsPagePropertiesValuesGivenNullLocation() {

        SevereWeatherWarningsPageViewModel viewModel = new SevereWeatherWarningsPageViewModel(
                new OpenWeatherSevereWarningsRetrieverMocks.OpenWeatherSevereWarningRetrieverMockOneAlert());
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.setsevereWeatherWarningsPagePropertiesValues(null, MeasurementUnits.Metric);
        });
    }

    @Test
    public void testSetSevereWeatherWarningsObjectPropertyValueGivenValidLocationNoAlerts() {
        SevereWeatherWarningsPageViewModel viewModel = new SevereWeatherWarningsPageViewModel(
                new OpenWeatherSevereWarningsRetrieverMocks.OpenWeatherSevereWarningRetrieverMockNoAlerts());
        WeatherLocation location = new WeatherLocation("someplace", "somewhere", "somehow", 0.0, 0.0);
        viewModel.setsevereWeatherWarningsPagePropertiesValues(location, MeasurementUnits.Metric);

        assertAll(() -> {
        }, () -> {
            assertTrue(viewModel.getNoWarningsForLocationVisibilityProperty().getValue());
        }, () -> {
            assertTrue(viewModel.getSevereWarningComboBoxListProperty().isEmpty());
        }, () -> {
            assertFalse(viewModel.getSevereWarningComboBoxVisibilityProperty().getValue());
        });
    }

    @Test
    public void testSetSevereWeatherWarningsObjectPropertyValueGivenValidLocationAlertsAreNull() {
        SevereWeatherWarningsPageViewModel viewModel = new SevereWeatherWarningsPageViewModel(
                new OpenWeatherSevereWarningsRetrieverMocks.OpenWeatherSevereWarningRetrieverMockNullAlerts());
        WeatherLocation location = new WeatherLocation("someplace", "somewhere", "somehow", 0.0, 0.0);
        viewModel.setsevereWeatherWarningsPagePropertiesValues(location, MeasurementUnits.Metric);
        assertAll(() -> {
        }, () -> {
            assertTrue(viewModel.getErrorTextVisibilityProperty().getValue());
        }, () -> {
            assertTrue(viewModel.getSevereWarningComboBoxListProperty().isEmpty());
        }, () -> {
            assertFalse(viewModel.getSevereWarningComboBoxVisibilityProperty().getValue());
        });
    }

    @Test
    public void testSetSevereWeatherWarningsObjectPropertyValueGivenValidLocationOneAlert() {
        SevereWeatherWarningsPageViewModel viewModel = new SevereWeatherWarningsPageViewModel(
                new OpenWeatherSevereWarningsRetrieverMocks.OpenWeatherSevereWarningRetrieverMockOneAlert());
        WeatherLocation location = new WeatherLocation("someplace", "somewhere", "somehow", 0.0, 0.0);
        viewModel.setsevereWeatherWarningsPagePropertiesValues(location, MeasurementUnits.Metric);
        SevereWeatherWarning warning = viewModel.getSevereWarningComboBoxListProperty().get(0);
        Long timezone = Long.parseLong(warning.getTimeZone());
        assertAll(() -> {
            assertNotNull(warning);
        }, () -> {
            assertEquals("Air Quality Alert", warning.getWarningName());
        }, () -> {
            assertEquals(DateTimeConverter.convertUtcToShortDate(1631722620L, timezone), warning.getStartingDate());
        }, () -> {
            assertEquals(DateTimeConverter.convertUtcToShortDate(1631856600L, timezone), warning.getEndingDate());
        }, () -> {
            assertEquals(" Ozone High Pollution Advisory for the Greater Phoenix Area through.", warning.getDetails());
        }, () -> {
            assertFalse(viewModel.getNoWarningsForLocationVisibilityProperty().getValue());
        }, () -> {
            assertEquals(1, viewModel.getSevereWarningComboBoxListProperty().getSize());
        });
    }

    @Test
    public void testSetSevereWeatherWarningsObjectPropertyValueGivenValidLocationTwoAlerts() {
        SevereWeatherWarningsPageViewModel viewModel = new SevereWeatherWarningsPageViewModel(
                new OpenWeatherSevereWarningsRetrieverMocks.OpenWeatherSevereWarningRetrieverMockTwoAlerts());
        WeatherLocation location = new WeatherLocation("someplace", "somewhere", "somehow", 0.0, 0.0);
        viewModel.setsevereWeatherWarningsPagePropertiesValues(location, MeasurementUnits.Metric);
        SevereWeatherWarning warning1 = viewModel.getSevereWarningComboBoxListProperty().get(0);
        SevereWeatherWarning warning2 = viewModel.getSevereWarningComboBoxListProperty().get(1);
        Long timezone1 = Long.parseLong(warning1.getTimeZone());
        Long timezone2 = Long.parseLong(warning2.getTimeZone());
        assertAll(() -> {
            assertNotNull(warning1);
        }, () -> {
            assertNotNull(warning2);
        }, () -> {
            assertFalse(viewModel.getNoWarningsForLocationVisibilityProperty().getValue());
        }, () -> {
            assertTrue(viewModel.getSevereWarningComboBoxVisibilityProperty().getValue());
        }, () -> {
            assertEquals("Air Quality Alert", warning1.getWarningName());
        }, () -> {
            assertEquals(DateTimeConverter.convertUtcToShortDate(1631722620L, timezone1), warning1.getStartingDate());
        }, () -> {
            assertEquals(DateTimeConverter.convertUtcToShortDate(1631856600L, timezone1), warning1.getEndingDate());
        }, () -> {
            assertEquals(" Ozone High Pollution Advisory for the Greater Phoenix Area through.", warning1.getDetails());
        }, () -> {
            assertEquals("Flash Flood Watch", warning2.getWarningName());
        }, () -> {
            assertEquals(DateTimeConverter.convertUtcToShortDate(1632164880L, timezone2), warning2.getStartingDate());
        }, () -> {
            assertEquals(DateTimeConverter.convertUtcToShortDate(1632182400L, timezone2), warning2.getEndingDate());
        }, () -> {
            assertEquals(" The sky is green. Its raining in Pheonix.", warning2.getDetails());
        });
    }

    @Test
    public void testSetSevereWeatherWarningsObjectPropertyValueGivenValidLocationThreeAlerts() {
        SevereWeatherWarningsPageViewModel viewModel = new SevereWeatherWarningsPageViewModel(
                new OpenWeatherSevereWarningsRetrieverMocks.OpenWeatherSevereWarningRetrieverMockThreeAlerts());
        WeatherLocation location = new WeatherLocation("someplace", "somewhere", "somehow", 0.0, 0.0);
        viewModel.setsevereWeatherWarningsPagePropertiesValues(location, MeasurementUnits.Metric);
        SevereWeatherWarning warning1 = viewModel.getSevereWarningComboBoxListProperty().get(0);
        SevereWeatherWarning warning2 = viewModel.getSevereWarningComboBoxListProperty().get(1);
        SevereWeatherWarning warning3 = viewModel.getSevereWarningComboBoxListProperty().get(2);
        Long timezone1 = Long.parseLong(warning1.getTimeZone());
        Long timezone2 = Long.parseLong(warning2.getTimeZone());
        Long timezone3 = Long.parseLong(warning3.getTimeZone());
        assertAll(() -> {
            assertNotNull(warning1);
        }, () -> {
            assertNotNull(warning2);
        }, () -> {
            assertFalse(viewModel.getNoWarningsForLocationVisibilityProperty().getValue());
        }, () -> {
            assertTrue(viewModel.getSevereWarningComboBoxVisibilityProperty().getValue());
        }, () -> {
            assertEquals("Air Quality Alert", warning1.getWarningName());
        }, () -> {
            assertEquals(DateTimeConverter.convertUtcToShortDate(1631722620L, timezone1), warning1.getStartingDate());
        }, () -> {
            assertEquals(DateTimeConverter.convertUtcToShortDate(1631856600L, timezone1), warning1.getEndingDate());
        }, () -> {
            assertEquals(" Ozone High Pollution Advisory for the Greater Phoenix Area through.", warning1.getDetails());
        }, () -> {
            assertEquals("Flash Flood Watch", warning2.getWarningName());
        }, () -> {
            assertEquals(DateTimeConverter.convertUtcToShortDate(1632164880L, timezone2), warning2.getStartingDate());
        }, () -> {
            assertEquals(DateTimeConverter.convertUtcToShortDate(1632182400L, timezone2), warning2.getEndingDate());
        }, () -> {
            assertEquals(" The sky is green. Its raining in Pheonix.", warning2.getDetails());
        }, () -> {
            assertEquals("Ozone is forecast to reach 108 AQI - Unhealthy for Sensitive Groups on Tue 09/21/2021.",
                    warning3.getWarningName());
        }, () -> {
            assertEquals(DateTimeConverter.convertUtcToShortDate(1632124800L,  timezone3), warning3.getStartingDate());
        }, () -> {
            assertEquals(DateTimeConverter.convertUtcToShortDate(1632211200L, timezone3), warning3.getEndingDate());
        }, () -> {
            assertEquals(" Bad air everywhere.", warning3.getDetails());
        });
    }

}
