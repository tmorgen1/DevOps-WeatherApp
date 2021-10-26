package edu.westga.weatherapp_gui;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import edu.westga.weatherapp_gui.mocks.ExceptionLogicMocks.CurrentDataRetrieverExceptionMock;
import edu.westga.weatherapp_gui.mocks.ExceptionLogicMocks.HourlyDataRetrieverExceptionMock;
import edu.westga.weatherapp_gui.mocks.ExceptionLogicMocks.LocationSearcherExceptionMock;
import edu.westga.weatherapp_gui.mocks.NormalLogicMocks.LocationSearcherMock;
import edu.westga.weatherapp_gui.mocks.NormalLogicMocks.MockDataRetriever;
import edu.westga.weatherapp_gui.mocks.NormalLogicMocks.OpenWeatherCurrentDataRetrieverMock;
import edu.westga.weatherapp_gui.mocks.NormalLogicMocks.OpenWeatherHourlyDataRetrieverMock;
import edu.westga.weatherapp_gui.mocks.NormalLogicMocks.OpenWeatherIconRetrieverMock;
import edu.westga.weatherapp_gui.model.WeatherLocationSerializer;
import edu.westga.weatherapp_gui.viewmodel.LandingPageViewModel;
import edu.westga.weatherapp_shared.interfaces.CurrentWeatherDataRetriever;
import edu.westga.weatherapp_shared.interfaces.HourlyWeatherDataRetriever;
import edu.westga.weatherapp_shared.interfaces.LocationSearcher;
import edu.westga.weatherapp_shared.interfaces.WeatherIconRetriever;
import edu.westga.weatherapp_shared.model.WeatherLocation;

public class LandingPageViewModelTests {

    @AfterEach
    public void removeFavoritedLocationsFile() {
        File favoritedLocationsFile = new File(WeatherLocationSerializer.FAVORITED_WEATHER_LOCATIONS_FILE_NAME);
        if (favoritedLocationsFile.exists())
        {
            favoritedLocationsFile.delete();
        }
    }

    @Test
    public void constructorValid() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        assertNotNull(viewModel);
    }

    @Test
    public void constructorWithWeatherDataRetrieverNull() {
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(null, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        assertNotNull(viewModel);
    }

    @Test
    public void constructorWithIconRetrieverNull() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, null, locationSearcher, hourlyWeatherDataRetriever);
        assertNotNull(viewModel);
    }

    @Test
    public void constructorWithLocationSearcherNull() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, null, hourlyWeatherDataRetriever);
        assertNotNull(viewModel);
    }

    @Test
    public void constructorWithHourlyWeatherRetrieverNull() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, null);
        assertNotNull(viewModel);
    }

    @Test
    public void getWeatherDataByWeatherLocationSuccessfullyCatchesRemoteException() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new CurrentDataRetrieverExceptionMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        assertNull(viewModel.getWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 32.40)));
    }

    @Test
    public void getWeatherDataByWeatherLocationThrowsExceptionWithNull() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getWeatherDataByWeatherLocation(null);
        });
    }

    @Test
    public void getHourlyWeatherDataByWeatherLocationSuccessfullyCatchesException() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new HourlyDataRetrieverExceptionMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        assertNull(viewModel.getHourlyForecastDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 32.40), 1));
    }

    @Test
    public void getHourlyWeatherDataByWeatherLocationThrowsExceptionWithNull() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getHourlyForecastDataByWeatherLocation(null, 1);
        });
    }

    @Test
    public void getWeatherDataWithValidWeatherLocation() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        JSONObject result = viewModel.getWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 32.40));
        assertEquals(5, result.getJSONObject("wind").getDouble("speed"));
    }

    @Test
    public void getHourlyWeatherDataWithValidWeatherLocation() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        JSONObject result = viewModel.getHourlyForecastDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 32.40), 1);
        assertEquals(5, result.getJSONObject("wind").getDouble("speed"));
    }

    @Test
    public void getWeatherDataWithEmptyState() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        JSONObject result = viewModel.getWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "N/A", 30.40, 32.40));
        assertEquals(5, result.getJSONObject("wind").getDouble("speed"));
    }

    @Test
    public void getHourlyWeatherDataWithEmptyState() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        JSONObject result = viewModel.getHourlyForecastDataByWeatherLocation(new WeatherLocation("city", "country", "N/A", 30.40, 32.40), 1);
        assertEquals(5, result.getJSONObject("wind").getDouble("speed"));
    }

    @Test
    public void getCurrentTemperatureThrowsExceptionWithNoWeatherData() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getCurrentTemperature();
        });
    }

    @Test
    public void getCurrentTemperatureValid() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        viewModel.getWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 32.40));
        String result = viewModel.getCurrentTemperature();
        assertEquals("70", result);
    }

    @Test
    public void getCurrentHumidityThrowsExceptionNoWeatherData() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getCurrentHumidity();
        });
    }

    @Test
    public void getCurrentHumidityValid() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        viewModel.getWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 32.40));
        String result = viewModel.getCurrentHumidity();
        assertEquals("80", result);
    }

    @Test
    public void getCurrentWeatherDescriptionThrowsExceptionNoWeatherData() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getCurrentWeatherDescription();
        });
    }

    @Test
    public void getCurrentWeatherDescriptionValid() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        viewModel.getWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 32.40));
        String result = viewModel.getCurrentWeatherDescription();
        assertEquals("Cloudy", result);
    }

    @Test
    public void getCurrentWindSpeedThrowsExceptionNoWeatherData() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getCurrentWindSpeed();
        });
    }

    @Test
    public void getCurrentWindSpeedValid() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
            viewModel.getWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 32.40));
        String result = viewModel.getCurrentWindSpeed();
        assertEquals("5", result);
    }

    @Test
    public void getCurrentWeatherIconThrowsExceptionNoWeatherData() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getCurrentWeatherIcon();
        });
    }

    @Test
    public void getCurrentWeatherIconValid() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        viewModel.getWeatherDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 32.40));
        String result = viewModel.getCurrentWeatherIcon();
        assertEquals("http://openweathermap.org/img/wn/test@4x.png", result);
    }

    @Test
    public void setCurrentWeatherDataThrowsExceptionWithNullData() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.setCurrentWeatherData(null);
        });
    }

    @Test
    public void setCurrentWeatherDataValid() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        JSONObject testObject = new JSONObject().put("test", 1);
        viewModel.setCurrentWeatherData(testObject);
        assertNotNull(viewModel);
    }

    @Test
    public void getLocationSearchResultsThrowsExceptionWithNullCity() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getLocationSearchResults(null);
        });
    }

    @Test
    public void getLocationSearchResultsThrowsExceptionWithEmptyCity() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getLocationSearchResults("");
        });
    }

    @Test
    public void getLocationSearchResultsValidCity() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        Collection<WeatherLocation> locations = viewModel.getLocationSearchResults("Newnan");
        assertEquals(1, locations.size());
    }

    @Test
    public void removeFavoritedLocationThrowsExceptionWithNullLocation() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.removeFavoritedLocation(null);
        });
    }

    @Test
    public void removeFavoritedLocationWithValidLocation() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        WeatherLocation location = new WeatherLocation("city", "country", "state", 30.4, 30.4);
        viewModel.addFavoritedLocation(location);
        viewModel.removeFavoritedLocation(location);
        assertEquals(0, viewModel.getFavoritedWeatherLocations().size());
    }

    @Test
    public void addFavoritedLocationThrowsExceptionWithNullLocation() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.addFavoritedLocation(null);
        });
    }

    @Test
    public void addFavoritedLocationWithValidLocation() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        WeatherLocation location = new WeatherLocation("city", "country", "state", 30.4, 30.4);
        viewModel.addFavoritedLocation(location);
        assertEquals(1, viewModel.getFavoritedWeatherLocations().size());
    }

    @Test
    public void getFavoritedLocationWithValidLocation() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        WeatherLocation location = new WeatherLocation("city", "country", "state", 30.4, 30.4);
        viewModel.addFavoritedLocation(location);
        Collection<WeatherLocation> locations = List.of(location);
        Collection<WeatherLocation> result = viewModel.getFavoritedWeatherLocations();
        assertEquals(locations, result);
    }

    @Test
    public void favoritesContainsWeatherLocationTrue() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        WeatherLocation location = new WeatherLocation("city", "country", "state", 30.4, 30.4);
        viewModel.addFavoritedLocation(location);
        boolean result = viewModel.favoritesContainsWeatherLocation(location);
        assertTrue(result);
    }

    @Test
    public void favoritesContainsWeatherLocationWithLocationsFalse() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        WeatherLocation location = new WeatherLocation("city", "country", "state", 30.4, 30.4);
        WeatherLocation otherLocation = new WeatherLocation("otherCity", "country", "state", 30.4, 30.4);
        viewModel.addFavoritedLocation(location);
        boolean result = viewModel.favoritesContainsWeatherLocation(otherLocation);
        assertFalse(result);
    }

    @Test
    public void favoritesContainsWeatherLocationFalse() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        WeatherLocation location = new WeatherLocation("city", "country", "state", 30.4, 30.4);
        boolean result = viewModel.favoritesContainsWeatherLocation(location);
        assertFalse(result);
    }

    @Test
    public void loadingFavoriteLocation() {
        try {
            WeatherLocationSerializer weatherLocationSerializer = new WeatherLocationSerializer();
            weatherLocationSerializer.saveFavoritedLocationsToFile(List.of(new WeatherLocation("city", "country", "state", 1.01, 1.01)));
            HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
            CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
            LocationSearcher locationSearcher = new LocationSearcherMock();
            LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
            assertNotNull(viewModel);
        } catch (IOException e) {
            fail("Error saving file in test");
        }
    }

    @Test
    public void getTimezoneThrowsExceptionWithNoWeatherData() {
            HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
            CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
            LocationSearcher locationSearcher = new LocationSearcherMock();
            LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getTimezone();
        });
    }

    @Test
    public void getTimezoneValid() {
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        viewModel.getHourlyForecastDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 30.40), 1);
        Long timezone = viewModel.getTimezone();
        assertEquals(100, timezone);
    }

    @Test
    public void getHourMaxTemperatureThrowsExceptionWithNoWeatherData() {
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getHourTemperature(0);
        });
    }

    @Test
    public void getHourMaxTemperatureValid() {
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        viewModel.getHourlyForecastDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 30.40), 1);
        String result = viewModel.getHourTemperature(0);
        assertEquals("98", result);
    }

    @Test
    public void getHourWeatherIconThrowsExceptionWithNoWeatherData() {
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getDayWeatherIcon(0);
        });
    }

    @Test
    public void getHourWeatherIconValid() {
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        viewModel.getHourlyForecastDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 30.40), 1);
        String result = viewModel.getDayWeatherIcon(0);
        assertEquals("http://openweathermap.org/img/wn/fake-url@4x.png", result);
    }

    @Test
    public void getHourUtcDateTimeThrowsExceptionWithNoWeatherData() {
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.getHourUtcDateTime(0);
        });
    }

    @Test
    public void getHourUtcDateTimeValid() {
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        viewModel.getHourlyForecastDataByWeatherLocation(new WeatherLocation("city", "country", "state", 30.40, 30.40), 1);
        Long utcTime = viewModel.getHourUtcDateTime(0);
        assertEquals(500, utcTime);
    }

    @Test
    public void getCurrentLocationSuccessfullyCatchesException() {
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherExceptionMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);

        assertNull(viewModel.getCurrentLocation());
    }

    @Test
    public void getCurrentLocation() {
        HourlyWeatherDataRetriever hourlyWeatherDataRetriever = new OpenWeatherHourlyDataRetrieverMock(new MockDataRetriever());
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher, hourlyWeatherDataRetriever);
        WeatherLocation expected = new WeatherLocation("city", "country", "state", 30.40, 30.40);
        WeatherLocation result = viewModel.getCurrentLocation();

        assertEquals(expected, result);
    }
}
