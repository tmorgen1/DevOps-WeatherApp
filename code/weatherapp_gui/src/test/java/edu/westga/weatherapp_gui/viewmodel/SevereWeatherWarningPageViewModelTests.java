package edu.westga.weatherapp_gui.viewmodel;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;

import edu.westga.weatherapp_gui.model.SevereWeatherWarning;
import edu.westga.weatherapp_gui.viewmodel.SevereWeatherWarningPageViewModel;

@TestMethodOrder(Alphanumeric.class)
public class SevereWeatherWarningPageViewModelTests {

    @Test
    public void testConstructionNoSevereWeatherWarningSet() {
        SevereWeatherWarningPageViewModel.setSevereWeatherWarningObjectPropertyValue(null);
        SevereWeatherWarningPageViewModel viewModel = new SevereWeatherWarningPageViewModel();
        assertAll(() -> {
            assertFalse(viewModel.getErrorTextStringProperty().getValue().isEmpty());
        }, () -> {
            assertTrue(viewModel.getErrorTextVisibilityProperty().getValue());
        });
    }
    

    @Test
    public void testConstructionWithSevereWeatherWarningSet() {
        SevereWeatherWarning warning = new SevereWeatherWarning("Heat Advisory", "1631722620", "1631856600", "Its hot.",
                "-2500");
        SevereWeatherWarningPageViewModel.setSevereWeatherWarningObjectPropertyValue(warning);
        SevereWeatherWarningPageViewModel viewModel = new SevereWeatherWarningPageViewModel();
        assertAll(() -> {
            assertEquals("Heat Advisory", viewModel.getWarningNameTextStringProperty().getValue());
        }, () -> {
            assertFalse(viewModel.getDescriptionTextProperty().getValue().isEmpty());
        }, () -> {
            assertNotNull(SevereWeatherWarningPageViewModel.getSevereWeatherWarningObjectProperty().getValue());
        });
    }
}
