package edu.westga.weatherapp_gui;

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

import edu.westga.weatherapp_gui.mocks.LocationSearcherMock;
import edu.westga.weatherapp_gui.mocks.MockDataRetriever;
import edu.westga.weatherapp_gui.mocks.OpenWeatherCurrentDataRetrieverMock;
import edu.westga.weatherapp_gui.mocks.OpenWeatherIconRetrieverMock;
import edu.westga.weatherapp_gui.model.WeatherLocationSerializer;
import edu.westga.weatherapp_gui.viewmodel.LandingPageViewModel;
import edu.westga.weatherapp_shared.interfaces.CurrentWeatherDataRetriever;
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

    @Test
    public void removeFavoritedLocationThrowsExceptionWithNullLocation() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.RemoveFavoritedLocation(null);
        });
    }

    @Test
    public void removeFavoritedLocationWithValidLocation() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        WeatherLocation location = new WeatherLocation("city", "country", "state", 30.4, 30.4);
        viewModel.AddFavoritedLocation(location);
        viewModel.RemoveFavoritedLocation(location);
        assertEquals(0, viewModel.GetFavoritedWeatherLocations().size());
    }

    @Test
    public void addFavoritedLocationThrowsExceptionWithNullLocation() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        assertThrows(IllegalArgumentException.class, () -> {
            viewModel.AddFavoritedLocation(null);
        });
    }

    @Test
    public void addFavoritedLocationWithValidLocation() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        WeatherLocation location = new WeatherLocation("city", "country", "state", 30.4, 30.4);
        viewModel.AddFavoritedLocation(location);
        assertEquals(1, viewModel.GetFavoritedWeatherLocations().size());
    }

    @Test
    public void getFavoritedLocationWithValidLocation() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        WeatherLocation location = new WeatherLocation("city", "country", "state", 30.4, 30.4);
        viewModel.AddFavoritedLocation(location);
        Collection<WeatherLocation> locations = List.of(location);
        Collection<WeatherLocation> result = viewModel.GetFavoritedWeatherLocations();
        assertEquals(locations, result);
    }

    @Test
    public void favoritesContainsWeatherLocationTrue() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        WeatherLocation location = new WeatherLocation("city", "country", "state", 30.4, 30.4);
        viewModel.AddFavoritedLocation(location);
        boolean result = viewModel.FavoritesContainsWeatherLocation(location);
        assertTrue(result);
    }

    @Test
    public void favoritesContainsWeatherLocationWithLocationsFalse() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        WeatherLocation location = new WeatherLocation("city", "country", "state", 30.4, 30.4);
        WeatherLocation otherLocation = new WeatherLocation("otherCity", "country", "state", 30.4, 30.4);
        viewModel.AddFavoritedLocation(location);
        boolean result = viewModel.FavoritesContainsWeatherLocation(otherLocation);
        assertFalse(result);
    }

    @Test
    public void favoritesContainsWeatherLocationFalse() {
        CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
        WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
        LocationSearcher locationSearcher = new LocationSearcherMock();
        LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
        WeatherLocation location = new WeatherLocation("city", "country", "state", 30.4, 30.4);
        boolean result = viewModel.FavoritesContainsWeatherLocation(location);
        assertFalse(result);
    }

    @Test
    public void loadingFavoriteLocation() {
        try {
            WeatherLocationSerializer weatherLocationSerializer = new WeatherLocationSerializer();
            weatherLocationSerializer.saveFavoritedLocationsToFile(List.of(new WeatherLocation("city", "country", "state", 1.01, 1.01)));
            CurrentWeatherDataRetriever currentWeatherRetriever = new OpenWeatherCurrentDataRetrieverMock(new MockDataRetriever());
            WeatherIconRetriever iconRetriever = new OpenWeatherIconRetrieverMock();
            LocationSearcher locationSearcher = new LocationSearcherMock();
            LandingPageViewModel viewModel = new LandingPageViewModel(currentWeatherRetriever, iconRetriever, locationSearcher);
            assertNotNull(viewModel);
        } catch (IOException e) {
            fail("Error saving file in test");
        }
    }
}
