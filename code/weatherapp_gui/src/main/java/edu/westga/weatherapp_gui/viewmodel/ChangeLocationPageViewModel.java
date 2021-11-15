package edu.westga.weatherapp_gui.viewmodel;

import java.io.IOException;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.Collection;

import edu.westga.weatherapp_gui.model.CurrentWeatherInformation;
import edu.westga.weatherapp_gui.model.WeatherLocationSerializer;
import edu.westga.weatherapp_shared.interfaces.LocationSearcher;
import edu.westga.weatherapp_shared.model.WeatherLocation;

/**
 * This class is the ViewModel for the ChangeLocationPage.
 * 
 * @author Michael Pavich
 */
public class ChangeLocationPageViewModel {

    /**
     * City cannot be null error message
     */
    private static final String CITY_CANNOT_BE_NULL_ERROR_MESSAGE = "City cannot be null";

    /**
     * The weather location searcher
     */
    private LocationSearcher weatherLocationSearcher;

    /**
     * The favorited weather locations
     */
    private Collection<WeatherLocation> favoritedWeatherLocations;
    
    /**
     * Creates a new ChangeLocationPageViewModel and connects the weather location searcher to the service. Also, loads the favorited locations.
     * 
     * @param locationSearcher - the weather location searcher
     */
    public ChangeLocationPageViewModel(LocationSearcher locationSearcher) {
        if (locationSearcher != null) {
            this.weatherLocationSearcher = locationSearcher;
        } else {
            try {
                this.weatherLocationSearcher = (LocationSearcher) Naming.lookup("rmi://localhost:5000/location-searcher");
            } catch (Exception exception) {
                System.err.println("Error looking up java rmi binding");
            }
        }

        this.loadFavoritedLocations();
    }

    /**
     * Loads the favorited locations from a file
     */
    private void loadFavoritedLocations() {
        try {
            WeatherLocationSerializer weatherLocationSerializer = new WeatherLocationSerializer();
            this.favoritedWeatherLocations = weatherLocationSerializer.loadFavoritedLocationsFromFile();
        } catch (ClassNotFoundException | IOException e) {
            this.favoritedWeatherLocations = (Collection<WeatherLocation>) new ArrayList<WeatherLocation>();
        }
    }

    /**
     * Gets the autocompletion search results based on the given city name
     * 
     * @param city - the city name
     * @return a collection of weather locations
     */
    public Collection<WeatherLocation> getLocationSearchResults(String city) {
        if (city == null) {
            throw new IllegalArgumentException(CITY_CANNOT_BE_NULL_ERROR_MESSAGE);
        }
        if (city.isEmpty()) {
            throw new IllegalArgumentException("City cannot be empty");
        }

        try {
            WeatherLocation currentLocation = CurrentWeatherInformation.getUserLocation();
            Collection<WeatherLocation> locations = this.weatherLocationSearcher.searchLocations(city, 10, currentLocation.getLatitude(), currentLocation.getLongitude());
            
            return locations;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets the collection of favorited weather locations
     * 
     * @return the favorited weather locations
     */
    public Collection<WeatherLocation> getFavoritedWeatherLocations() {
        return this.favoritedWeatherLocations;
    }
}
