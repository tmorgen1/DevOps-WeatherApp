package edu.westga.weatherapp_service.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import edu.westga.weatherapp_shared.interfaces.LocationSearcher;
import edu.westga.weatherapp_shared.model.WeatherLocation;

/**
 * Class that handles checking the database of locations that the OpenWeather api can grab data from.  Handles
 * autocomplete logic, essentially, by polling the entries with given search strings.
 */
public class WeatherLocationSearcher extends UnicastRemoteObject implements LocationSearcher {
    
    /**
     * Contains the JSONObject entries for all the locations OpenWeather has data for.
     */
    private JSONArray locations;

    /**
     * Instantiates a WeatherLocationSearcher object.  Requires the filename/path to the .json file
     * that contains the json array with json objects of locations.  These json objects of locations
     * should contain the following data for proper searching:
     * 
     * "name" : city name
     * "country" : country name
     * "state" : state name, if applicable
     * "coord" : {
     *      "lon" : double value, the longitude
     *      "lat" : double value, the latitude
     * }
     * 
     * @param fileName - filename/path to the .json file
     * @throws RemoteException
     */
    public WeatherLocationSearcher(String fileName) throws RemoteException {
        this.locations = this.readLocationDataFromJson(fileName);
    }

    /**
     * Checks for the existence of the file, then proceeds to read the data and return a 
     * JSONArray with the data.  Returns null if the file does not exist, or
     * if there was an issue reading the file.
     * 
     * @param fileName - filename/path to the .json file
     * @return JSONArray containing the data from the file
     */
    private JSONArray readLocationDataFromJson(String fileName) {
        JSONArray locationsJson = null;
        
        File locationsFile = new File(fileName);
        if (!locationsFile.exists()) {
            return locationsJson;
        }

        try (InputStream inputStream = new FileInputStream(locationsFile)) {
            String jsonText = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

            locationsJson = new JSONArray(jsonText);
        } catch (IOException ioException) {
            System.err.println("Error while reading from input stream.");
            return locationsJson;
        }

        return locationsJson;
    }

    @Override
    public Collection<WeatherLocation> searchLocations(String searchEntry, int maxEntryResponse) throws RemoteException {
        if (maxEntryResponse < 1 || maxEntryResponse > 20) {
            throw new IllegalArgumentException("maxEntryResponse should not be less than 1 or greather than 20");
        }

        Collection<WeatherLocation> searchResults = new ArrayList<WeatherLocation>();

        for (int i = 0; i < this.locations.length(); i++) {
            if (searchResults.size() >= maxEntryResponse) {
                break;
            }

            JSONObject currentJsonObject = this.locations.getJSONObject(i);
            if (currentJsonObject.getString("name").toLowerCase().startsWith(searchEntry.toLowerCase())) {
                WeatherLocation weatherLocationWrapper = this.createWeatherLocationFromJson(currentJsonObject);
                searchResults.add(weatherLocationWrapper);
            }
        }

        return searchResults;
    }

    /**
     * Wraps the information present within a weather location json object into a WeatherLocation object.
     * If the state name is empty, the WeatherLocation is returned with 'N/A' because no entry within
     * the WeatherLocation should be empty.
     * 
     * @param jsonObject - weather location json object
     * @return wrapped json object
     */
    private WeatherLocation createWeatherLocationFromJson(JSONObject jsonObject) {
        String city = jsonObject.getString("name");
        String country = jsonObject.getString("country");
        String state = jsonObject.getString("state");
        if (state.isEmpty()) {
            state = "N/A";
        }
        Double longitude = jsonObject.getJSONObject("coord").getDouble("lon");
        Double latitude = jsonObject.getJSONObject("coord").getDouble("lat");

        return new WeatherLocation(city, country, state, longitude, latitude);
    }

}
