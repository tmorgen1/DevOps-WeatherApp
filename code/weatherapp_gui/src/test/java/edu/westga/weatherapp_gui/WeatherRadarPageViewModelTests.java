package edu.westga.weatherapp_gui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import edu.westga.weatherapp_gui.mocks.LocationSearcherMock;
import edu.westga.weatherapp_gui.mocks.OpenWeatherMapRadarDataRetrieverMock;
import edu.westga.weatherapp_gui.viewmodel.WeatherRadarPageViewModel;
import edu.westga.weatherapp_shared.interfaces.LocationSearcher;
import edu.westga.weatherapp_shared.interfaces.MapRadarDataRetriever;

public class WeatherRadarPageViewModelTests {
    
    @Test
    public void constructorValid() {
        MapRadarDataRetriever mapRadarDataRetriever = new OpenWeatherMapRadarDataRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        WeatherRadarPageViewModel viewModel = new WeatherRadarPageViewModel(mapRadarDataRetriever, locationSearcher);
        assertNotNull(viewModel);
    }

    @Test
    public void constructorWithNullMapRadarDataRetriever() {
        LocationSearcher locationSearcher = new LocationSearcherMock();
        WeatherRadarPageViewModel viewModel = new WeatherRadarPageViewModel(null, locationSearcher);
        assertNotNull(viewModel);
    }

    @Test
    public void constructorWithNullLocationSearcher() {
        MapRadarDataRetriever mapRadarDataRetriever = new OpenWeatherMapRadarDataRetrieverMock();
        WeatherRadarPageViewModel viewModel = new WeatherRadarPageViewModel(mapRadarDataRetriever, null);
        assertNotNull(viewModel);
    }

    @Test
    public void getLocationRadarMap() {
        MapRadarDataRetriever mapRadarDataRetriever = new OpenWeatherMapRadarDataRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        WeatherRadarPageViewModel viewModel = new WeatherRadarPageViewModel(mapRadarDataRetriever, locationSearcher);
        String expected = "test";
        String result = viewModel.LoadWeatherRadarMap();
        assertEquals(expected, result);
    }
}
