package edu.westga.weatherapp_gui.viewmodel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import edu.westga.weatherapp_gui.mocks.NormalLogicMocks.OpenWeatherMapRadarDataRetrieverMock;
import edu.westga.weatherapp_gui.model.CurrentWeatherInformation;
import edu.westga.weatherapp_shared.interfaces.MapRadarDataRetriever;
import edu.westga.weatherapp_shared.model.WeatherLocation;

public class WeatherRadarPageViewModelTests {
    
    @Test
    public void constructorValid() {
        MapRadarDataRetriever mapRadarDataRetriever = new OpenWeatherMapRadarDataRetrieverMock();
        WeatherRadarPageViewModel viewModel = new WeatherRadarPageViewModel(mapRadarDataRetriever);
        assertNotNull(viewModel);
    }

    @Test
    public void constructorWithNullMapRadarDataRetriever() {
        WeatherRadarPageViewModel viewModel = new WeatherRadarPageViewModel(null);
        assertNotNull(viewModel);
    }

    @Test
    public void getLocationRadarMap() {
        CurrentWeatherInformation.setUserLocation(new WeatherLocation("city", "country", "state", 1.0, 1.0));
        MapRadarDataRetriever mapRadarDataRetriever = new OpenWeatherMapRadarDataRetrieverMock();
        WeatherRadarPageViewModel viewModel = new WeatherRadarPageViewModel(mapRadarDataRetriever);
        String expected = "test";
        String result = viewModel.loadWeatherRadarMap();
        assertEquals(expected, result);
    }
}
