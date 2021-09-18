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
    public void constructorInstantiationNotNullButWithNonExistingFile() {
        try {
            assertNotNull(new WeatherLocationSearcher("file-that-does-not-exist"));
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void constructorInstantiatesSuccessfully() {
        try {
            assertNotNull(new WeatherLocationSearcher(App.WEATHER_LOCATIONS_FILE_NAME));
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void searchLocationsThrowsExceptionOnLessThan1MaxEntry() {
        try {
            WeatherLocationSearcher searcher = new WeatherLocationSearcher(App.WEATHER_LOCATIONS_FILE_NAME);
            assertThrows(IllegalArgumentException.class, () -> {
                searcher.searchLocations("blah", 0);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void searchLocationsThrowsExceptionOnGreaterThan20MaxEntries() {
        try {
            WeatherLocationSearcher searcher = new WeatherLocationSearcher(App.WEATHER_LOCATIONS_FILE_NAME);
            assertThrows(IllegalArgumentException.class, () -> {
                searcher.searchLocations("blah", 21);
            });
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }

    @Test
    public void searchLocationsSuccessfully() {
        try {
            WeatherLocationSearcher searcher = new WeatherLocationSearcher(App.WEATHER_LOCATIONS_FILE_NAME);
            assertEquals(1, searcher.searchLocations("newn", 1).size());
        } catch (RemoteException e) {
            e.printStackTrace();
            fail("Remote Exception while testing");
        }
    }
}
