package edu.westga.weatherapp_gui.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;

/**
 * This class handles saving/loading favorited weather location data.
 */
public class WeatherLocationSerializer {

    /**
     * File name/path for favorited weather locations.
     */
    public static final String FAVORITED_WEATHER_LOCATIONS_FILE_NAME = "favorited_locations.wl";
    
    /**
     * Saves a collection of weather locations to file through object serialization.
     * 
     * @param locations - the collection of locations
     * @throws IOException
     */
    public void saveFavoritedLocationsToFile(Collection<WeatherLocation> locations) throws IOException {
        try (FileOutputStream fileOutStream = new FileOutputStream(WeatherLocationSerializer.FAVORITED_WEATHER_LOCATIONS_FILE_NAME)) {
            try (ObjectOutputStream objectOutStream = new ObjectOutputStream(fileOutStream)) {
                objectOutStream.writeObject(locations);
            }
        }
    }

    /**
     * Loads a collection of weather locations from file through object deserialization.
     * 
     * @return the collection of weather locations, null if there was an issue.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public Collection<WeatherLocation> loadFavoritedLocationsFromFile() throws IOException, ClassNotFoundException {
        Collection<WeatherLocation> savedLocations = null;

        try (FileInputStream fileInputStream = new FileInputStream(WeatherLocationSerializer.FAVORITED_WEATHER_LOCATIONS_FILE_NAME)) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                savedLocations = (Collection<WeatherLocation>) objectInputStream.readObject();
            }
        }

        return savedLocations;
    }
}
