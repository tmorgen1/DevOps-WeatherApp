package edu.westga.weatherapp_service;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.rmi.RemoteException;
import org.junit.jupiter.api.Test;
import edu.westga.weatherapp_service.model.WeatherLocationSearcher;

public class WeatherLocationSearcherTests {
    
    @Test
    public void constructorInstantiationNotNullButWithNonExistingFiles() {
        try {
            assertNotNull(new WeatherLocationSearcher("file-that-does-not-exist", "file-that-does-not-exist-part-2"));
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void constructorInstantiatesSuccessfully() {
        try {
            assertNotNull(new WeatherLocationSearcher(App.WEATHER_SEARCH_LOCATIONS_FILE_NAME, App.WEATHER_GEOIP_LOCATIONS_DATABASE_NAME));
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void searchLocationsThrowsExceptionOnLessThan1MaxEntry() {
        try {
            WeatherLocationSearcher searcher = new WeatherLocationSearcher(App.WEATHER_SEARCH_LOCATIONS_FILE_NAME, App.WEATHER_GEOIP_LOCATIONS_DATABASE_NAME);
            assertThrows(IllegalArgumentException.class, () -> {
                searcher.searchLocations("blah", 0, 0, 0);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void searchLocationsThrowsExceptionOnGreaterThan20MaxEntries() {
        try {
            WeatherLocationSearcher searcher = new WeatherLocationSearcher(App.WEATHER_SEARCH_LOCATIONS_FILE_NAME, App.WEATHER_GEOIP_LOCATIONS_DATABASE_NAME);
            assertThrows(IllegalArgumentException.class, () -> {
                searcher.searchLocations("blah", 21, 0, 0);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void searchLocationsSuccessfully() {
        try {
            WeatherLocationSearcher searcher = new WeatherLocationSearcher(App.WEATHER_SEARCH_LOCATIONS_FILE_NAME, App.WEATHER_GEOIP_LOCATIONS_DATABASE_NAME);
            assertEquals(2, searcher.searchLocations("newn", 1, 0, 0).size());
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void getLocationFromIpSuccessfully() {
        try {
            WeatherLocationSearcher searcher = new WeatherLocationSearcher(App.WEATHER_SEARCH_LOCATIONS_FILE_NAME, App.WEATHER_GEOIP_LOCATIONS_DATABASE_NAME);
            assertNotNull(searcher.getLocationByIP("12.231.7.4"));
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }
}
