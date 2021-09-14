package edu.westga.weatherapp_gui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import edu.westga.weatherapp_gui.model.WeatherLocation;
import edu.westga.weatherapp_gui.model.WeatherLocationSerializer;

public class WeatherLocationSerializerTests {
    
    @AfterEach
    public void removeFavoritedLocationsFile() {
        File favoritedLocationsFile = new File(WeatherLocationSerializer.FAVORITED_WEATHER_LOCATIONS_FILE_NAME);
        if (favoritedLocationsFile.exists())
        {
            favoritedLocationsFile.delete();
        }
    }

    @Test
    public void savingToFileSuccessfully() {
        Collection<WeatherLocation> locations = List.of(new WeatherLocation(null, null, null, null, null),
                new WeatherLocation("city", "country", "state", 1.0d, 1.0d));
        
        WeatherLocationSerializer serializer = new WeatherLocationSerializer();

        try {
            serializer.saveFavoritedLocationsToFile(locations);
        } catch (IOException e) {
            fail("There was an issue saving to file in tests.");
        }

        assertTrue(new File(WeatherLocationSerializer.FAVORITED_WEATHER_LOCATIONS_FILE_NAME).exists());
    }

    @Test
    public void readingFromFileSuccessfully() {
        Collection<WeatherLocation> locations = List.of(new WeatherLocation(null, null, null, null, null),
                new WeatherLocation("city", "country", "state", 1.0d, 1.0d));
        
        WeatherLocationSerializer serializer = new WeatherLocationSerializer();

        try {
            serializer.saveFavoritedLocationsToFile(locations);
        } catch (IOException e) {
            fail("There was an issue saving to file in tests.");
        }

        try {
            assertNotNull(serializer.loadFavoritedLocationsFromFile());
        } catch (ClassNotFoundException | IOException e) {
            fail("There was an issue reading from file in tests.");
        }
    }

}
