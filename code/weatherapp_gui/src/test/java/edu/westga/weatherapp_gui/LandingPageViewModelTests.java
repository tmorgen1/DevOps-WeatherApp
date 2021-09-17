package edu.westga.weatherapp_gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import edu.westga.weatherapp_gui.mocks.LocationSearcherMock;
import edu.westga.weatherapp_gui.mocks.MockDataRetriever;
import edu.westga.weatherapp_gui.mocks.OpenWeatherCurrentDataRetrieverMock;
import edu.westga.weatherapp_gui.mocks.OpenWeatherIconRetrieverMock;
import edu.westga.weatherapp_gui.viewmodel.LandingPageViewModel;
import edu.westga.weatherapp_shared.interfaces.CurrentWeatherDataRetriever;
import edu.westga.weatherapp_shared.interfaces.LocationSearcher;
import edu.westga.weatherapp_shared.interfaces.WeatherIconRetriever;
import edu.westga.weatherapp_shared.model.WeatherLocation;

public class LandingPageViewModelTests {

    @Test
    public void constructorValid() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        assertNotNull(viewModel);
    }

    @Test
    public void constructorWithWeatherDataRetrieverNull() {
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(null, iconRetriever, locationSearcher);
        assertNotNull(viewModel);
    }

    @Test
    public void constructorWithIconRetrieverNull() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, null, locationSearcher);
        assertNotNull(viewModel);
    }

    @Test
    public void constructorWithLocationSearcherNull() {
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(null, null, locationSearcher);
        assertNotNull(viewModel);
    }

    @Test
    public void getWeatherDataByWeatherLocationThrowsExceptionWithNull() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.GetWeatherDataByWeatherLocation(null);
        });
    }

    @Test
    public void getWeatherDataWithValidWeatherLocation() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        JSONObject result = viewModel.GetWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 32.40));
        assertEquals(5, result.getJSONObject("wind").getDouble("speed"));
    }

    @Test
    public void getWeatherDataWithEmptyState() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        JSONObject result = viewModel.GetWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "N/A", 30.40, 32.40));
        assertEquals(5, result.getJSONObject("wind").getDouble("speed"));
    }

    @Test
    public void getCurrentTemperatureThrowsExceptionWithNoWeatherData() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.GetCurrentTemperature();
        });
    }

    @Test
    public void getCurrentTemperatureValid() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        viewModel.GetWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 32.40));
        String result = viewModel.GetCurrentTemperature();
        assertEquals("70", result);
    }

    @Test
    public void getCurrentHumidityThrowsExceptionNoWeatherData() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.GetCurrentHumidity();
        });
    }

    @Test
    public void getCurrentHumidityValid() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        viewModel.GetWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 32.40));
        String result = viewModel.GetCurrentHumidity();
        assertEquals("80", result);
    }

    @Test
    public void getCurrentWeatherDescriptionThrowsExceptionNoWeatherData() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.GetCurrentWeatherDescription();
        });
    }

    @Test
    public void getCurrentWeatherDescriptionValid() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        viewModel.GetWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 32.40));
        String result = viewModel.GetCurrentWeatherDescription();
        assertEquals("Cloudy", result);
    }

    @Test
    public void getCurrentWindSpeedThrowsExceptionNoWeatherData() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.GetCurrentWindSpeed();
        });
    }

    @Test
    public void getCurrentWindSpeedValid() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
            viewModel.GetWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 32.40));
        String result = viewModel.GetCurrentWindSpeed();
        assertEquals("5", result);
    }

    @Test
    public void getCurrentWeatherIconThrowsExceptionNoWeatherData() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.GetCurrentWeatherIcon();
        });
    }

    @Test
    public void getCurrentWeatherIconValid() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        viewModel.GetWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 32.40));
        String result = viewModel.GetCurrentWeatherIcon();
        assertEquals("http://openweathermap.org/img/wn/test@4x.png", result);
    }

    @Test
    public void setCurrentWeatherDataThrowsExceptionWithNullData() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.SetCurrentWeatherData(null);
        });
    }

    @Test
    public void setCurrentWeatherDataValid() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        JSONObject testObject = new JSONObject().put("test", 1);
        viewModel.SetCurrentWeatherData(testObject);
        assertNotNull(viewModel);
    }

    @Test
    public void getLocationSearchResultsThrowsExceptionWithNullCity() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.GetLocationSearchResults(null);
        });
    }

    @Test
    public void getLocationSearchResultsThrowsExceptionWithEmptyCity() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.GetLocationSearchResults("");
        });
    }

    @Test
    public void getLocationSearchResultsValidCity() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        Collection<WeatherLocation> locations = viewModel.GetLocationSearchResults("Newnan");
        assertEquals(1, locations.size());
    }
}
