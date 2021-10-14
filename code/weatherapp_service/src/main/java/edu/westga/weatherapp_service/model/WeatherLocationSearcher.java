package edu.westga.weatherapp_service.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import edu.westga.weatherapp_shared.interfaces.LocationSearcher;
import edu.westga.weatherapp_shared.model.WeatherLocation;

/**
 * Class that handles checking the database of locations that the OpenWeather
 * api can grab data from. Handles autocomplete logic, essentially, by polling
 * the entries with given search strings.
 */
public class WeatherLocationSearcher extends UnicastRemoteObject implements LocationSearcher {

    /**
     * Contains the JSONObject entries for all the locations OpenWeather has data
     * for.
     */
    private JSONArray searchLocations;

    /**
     * Contains the information regarding locations and their relation to IP
     * Addresses.
     */
    private DatabaseReader geoIpLocationDatabase;

    /**
     * Instantiates a WeatherLocationSearcher object. Requires the filename/path to
     * the .json file that contains the json array with json objects of locations.
     * These json objects of locations should contain the following data for proper
     * searching:
     * 
     * "name" : city name "country" : country name "state" : state name, if
     * applicable "coord" : { "lon" : double value, the longitude "lat" : double
     * value, the latitude }
     * 
     * Also requires the filename/path to the .mmdb database for geoip locations to
     * determine location by IP.
     * 
     * @param searchLocationFileName    - filename/path to the .json file
     * @param geoIpLocationDatabaseName - filename/path to the .mmdb file
     * @throws RemoteException
     */
    public WeatherLocationSearcher(String searchLocationFileName, String geoIpLocationDatabaseName)
            throws RemoteException {
        this.searchLocations = this.readLocationDataFromJson(searchLocationFileName);
        this.geoIpLocationDatabase = this.getGeoIpDatabase(geoIpLocationDatabaseName);
    }

    /**
     * Checks for the existence of the database file, then proceeds to create a
     * DatabaseReader and return the DatabaseReader after it's built. Returns null
     * if the database does not exist, or if there was an issue reading the
     * database.
     * 
     * @param databaseFileName
     * @return DatabaseReader containing the data from the file
     */
    private DatabaseReader getGeoIpDatabase(String databaseFileName) {
        File database = new File(databaseFileName);
        if (!database.exists()) {
            return null;
        }

        DatabaseReader dbReader = null;
        try {
            dbReader = new DatabaseReader.Builder(database).build();
        } catch (IOException e) {
            System.err.println("Error while building the geoip database.");
        }

        return dbReader;
    }

    /**
     * Checks for the existence of the file, then proceeds to read the data and
     * return a JSONArray with the data. Returns null if the file does not exist, or
     * if there was an issue reading the file.
     * 
     * @param fileName - filename/path to the .json file
     * @return JSONArray containing the data from the file
     */
    private JSONArray readLocationDataFromJson(String fileName) {
        JSONArray locationsJson = null;

        File locationsFile = new File(fileName);
        if (!locationsFile.exists()) {
            return null;
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
    public Collection<WeatherLocation> searchLocations(String searchEntry, int maxEntryResponse)
            throws RemoteException {
        if (maxEntryResponse < 1 || maxEntryResponse > 20) {
            throw new IllegalArgumentException("maxEntryResponse should not be less than 1 or greather than 20");
        }

        Collection<WeatherLocation> searchResults = new ArrayList<WeatherLocation>();

        for (int i = 0; i < this.searchLocations.length(); i++) {
            if (searchResults.size() >= maxEntryResponse) {
                break;
            }

            JSONObject currentJsonObject = this.searchLocations.getJSONObject(i);
            if (currentJsonObject.getString("name").toLowerCase().startsWith(searchEntry.toLowerCase())) {
                WeatherLocation weatherLocationWrapper = this.createWeatherLocationFromJson(currentJsonObject);
                searchResults.add(weatherLocationWrapper);
            }
        }

        return searchResults;
    }

    /**
     * Wraps the information present within a weather location json object into a
     * WeatherLocation object. If the state name is empty, the WeatherLocation is
     * returned with 'N/A' because no entry within the WeatherLocation should be
     * empty.
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

    @Override
    public WeatherLocation getLocationByIP(String ipAddress) throws RemoteException {
        InetAddress iNetAddress = null;
        try {
            iNetAddress = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            System.err.println("Error while creating an INetAddress");
            return null;
        }

        CityResponse response = null;
        try {
            response = this.geoIpLocationDatabase.city(iNetAddress);
        } catch (IOException | GeoIp2Exception e) {
            System.err.println("Error while searching the database reader.");
            return null;
        }

        WeatherLocation weatherLocation = new WeatherLocation(response.getCity().getName(),
                response.getCountry().getName(), response.getLeastSpecificSubdivision().getName(),
                response.getLocation().getLongitude(), response.getLocation().getLatitude());

        return weatherLocation;
    }
}
