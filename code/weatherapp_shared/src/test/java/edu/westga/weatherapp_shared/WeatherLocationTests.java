package edu.westga.weatherapp_shared;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import edu.westga.weatherapp_shared.model.WeatherLocation;

public class WeatherLocationTests {
    
    @Test
    public void parameterizedConstructorSuccessfullyInstantiates() {
        assertNotNull(new WeatherLocation("city", "country", "state", 1.0d, 1.0d));
    }

    @Test
    public void parameterizedConstructorSuccessfullyInstantiatesWithNulls() {
        assertNotNull(new WeatherLocation(null, null, null, null, null));
    }

    @Test
    public void parameterizedConstructorThrowsExceptionOnEmptyCity() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WeatherLocation("", "country", "state", 1.0d, 1.0d);
        });
    }

    @Test
    public void parameterizedConstructorThrowsExceptionOnEmptyCountry() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WeatherLocation("state", "", "state", 1.0d, 1.0d);
        });
    }

    @Test
    public void parameterizedConstructorThrowsExceptionOnEmptyState() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WeatherLocation("state", "country", "", 1.0d, 1.0d);
        });
    }

    @Test
    public void getCityReturnsSuccessfully() {
        String cityName = "test";

        WeatherLocation location = new WeatherLocation(cityName, "country", "state", 1.0d, 1.0d);

        assertEquals(cityName, location.getCity());
    }

    @Test
    public void getCountryReturnsSuccessfully() {
        String countryName = "test";

        WeatherLocation location = new WeatherLocation("city", countryName, "state", 1.0d, 1.0d);

        assertEquals(countryName, location.getCountry());
    }

    @Test
    public void getStateReturnsSuccessfully() {
        String stateName = "test";

        WeatherLocation location = new WeatherLocation("city", "country", stateName, 1.0d, 1.0d);

        assertEquals(stateName, location.getState());
    }

    @Test
    public void getLongitudeReturnsSuccessfully() {
        Double longitude = 1.0d;

        WeatherLocation location = new WeatherLocation("city", "country", "state", longitude, 1.0d);

        assertEquals(longitude, location.getLongitude());
    }

    @Test
    public void getLatitudeReturnsSuccessfully() {
        Double latitude = 1.0d;

        WeatherLocation location = new WeatherLocation("city", "country", "state", 1.0d, latitude);

        assertEquals(latitude, location.getLatitude());
    }

    @Test
    public void toStringWithEmptyState() {
        WeatherLocation location = new WeatherLocation("city", "country", "N/A", 1.0d, 1.0d);
        String expected = "city country 1.0 1.0";
        assertEquals(expected, location.toString());
    }

    @Test
    public void toStringWithState() {
        WeatherLocation location = new WeatherLocation("city", "country", "state", 1.0d, 1.0d);
        String expected = "city country state 1.0 1.0";
        assertEquals(expected, location.toString());
    }

    @Test
    public void equalsWithNullLocation() {
        WeatherLocation location = new WeatherLocation("city", "country", "state", 1.0d, 1.0d);
        boolean result = location.equals(null);
        assertFalse(result);
    }

    @Test
    public void equalsWithOnlyStateMatching() {
        WeatherLocation location = new WeatherLocation("city", "country", "state", 1.0d, 1.0d);
        WeatherLocation otherLocation = new WeatherLocation("otherCity", "otherCountry", "state", 1.1d, 1.1d);
        boolean result = location.equals(otherLocation);
        assertFalse(result);
    }

    @Test
    public void equalsWithOnlyCountryMatching() {
        WeatherLocation location = new WeatherLocation("city", "country", "state", 1.0d, 1.0d);
        WeatherLocation otherLocation = new WeatherLocation("otherCity", "country", "otherState", 1.1d, 1.1d);
        boolean result = location.equals(otherLocation);
        assertFalse(result);
    }

    @Test
    public void equalsWithOnlyCityMatching() {
        WeatherLocation location = new WeatherLocation("city", "country", "state", 1.0d, 1.0d);
        WeatherLocation otherLocation = new WeatherLocation("city", "otherCountry", "otherState", 1.1d, 1.1d);
        boolean result = location.equals(otherLocation);
        assertFalse(result);
    }

    @Test
    public void equalsWithOnlyLatitudeMatching() {
        WeatherLocation location = new WeatherLocation("city", "country", "state", 1.0d, 1.0d);
        WeatherLocation otherLocation = new WeatherLocation("city", "otherCountry", "otherState", 1.1d, 1.0d);
        boolean result = location.equals(otherLocation);
        assertFalse(result);
    }

    @Test
    public void equalsWithOnlyLongitudeMatching() {
        WeatherLocation location = new WeatherLocation("city", "country", "state", 1.0d, 1.0d);
        WeatherLocation otherLocation = new WeatherLocation("city", "otherCountry", "otherState", 1.0d, 1.1d);
        boolean result = location.equals(otherLocation);
        assertFalse(result);
    }

    @Test
    public void equalsWithEverythingMatching() {
        WeatherLocation location = new WeatherLocation("city", "country", "state", 1.0d, 1.0d);
        WeatherLocation otherLocation = new WeatherLocation("city", "country", "state", 1.0d, 1.0d);
        boolean result = location.equals(otherLocation);
        assertTrue(result);
    }
}
