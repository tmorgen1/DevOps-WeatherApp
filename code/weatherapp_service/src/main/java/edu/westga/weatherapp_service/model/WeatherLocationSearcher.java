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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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

    private static final double TOTAL_KILOMETERS_IN_MILE = 1.609344;

    private static final double TOTAL_STATUTE_MILES_IN_NAUTICAL_MILE = 1.1515;

    private static final double TOTAL_MINUTES_IN_DEGREE = 60;

    private static final double HALF_CIRCLE_DEGREES = 180;
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
    public Collection<WeatherLocation> searchLocations(String searchEntry, int maxEntryResponse, double latitude, double longitude) throws RemoteException {
        if (maxEntryResponse < 1 || maxEntryResponse > 20) {
            throw new IllegalArgumentException("maxEntryResponse should not be less than 1 or greather than 20");
        }

        Collection<WeatherLocation> searchResults = new ArrayList<WeatherLocation>();

        for (int i = 0; i < this.searchLocations.length(); i++) {
            JSONObject currentJsonObject = this.searchLocations.getJSONObject(i);
            if (currentJsonObject.getString("name").toLowerCase().startsWith(searchEntry.toLowerCase())) {
                WeatherLocation weatherLocationWrapper = this.createWeatherLocationFromJson(currentJsonObject);
                searchResults.add(weatherLocationWrapper);
            }
        }

        List<WeatherLocation> listOfSearchResults = new ArrayList<WeatherLocation>(searchResults);
        this.sortSearchLocationsByClosestDistance(listOfSearchResults, latitude, longitude);
        if (searchResults.size() > 10) {
            searchResults = new ArrayList<WeatherLocation>(listOfSearchResults.subList(0, 10));
        } else {
            searchResults = listOfSearchResults;
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

    /**
     * Calculates the distance between the two sets of coordiantes
     * 
     * @param latitude1 - the first latitude
     * @param longitude1 - the first longitude
     * @param latitude2 - the other latitude
     * @param longitude2 - the other longitude
     * @return the distance between the two sets coordinates
     */
    private double calculateDistanceBetweenCoordinates(double latitude1, double longitude1, double latitude2, double longitude2) {
        double radiansLatitude1 = Math.PI * latitude1 / HALF_CIRCLE_DEGREES;
        double radiansLatitude2 = Math.PI * latitude2 / HALF_CIRCLE_DEGREES;
        double theta = longitude1 - longitude2;
        double radiansTheta = Math.PI * theta / HALF_CIRCLE_DEGREES;
        double distance = (Math.sin(radiansLatitude1) * Math.sin(radiansLatitude2)) + (Math.cos(radiansLatitude1) * Math.cos(radiansLatitude2) * Math.cos(radiansTheta));
        distance = Math.acos(distance);
        distance = distance * HALF_CIRCLE_DEGREES / Math.PI;
        distance = distance * TOTAL_MINUTES_IN_DEGREE * TOTAL_STATUTE_MILES_IN_NAUTICAL_MILE;
        distance = distance * TOTAL_KILOMETERS_IN_MILE;

        return distance;
    }

    /**
     * Sorts the list of weather locations based on how close they are to the given coordinates
     * 
     * @param locations - the list of weather locations
     * @param latitude - the latitude to sort by
     * @param longitude - the longitude to sort by
     */
    private void sortSearchLocationsByClosestDistance(List<WeatherLocation> locations, double latitude, double longitude) {
        Collections.sort(locations, new Comparator<WeatherLocation>(){
            @Override
            public int compare(WeatherLocation weatherLocation, WeatherLocation otherWeatherLocation) {
                return (int) (calculateDistanceBetweenCoordinates(latitude, longitude, weatherLocation.getLatitude(), weatherLocation.getLongitude())
                    - calculateDistanceBetweenCoordinates(latitude, longitude, otherWeatherLocation.getLatitude(), otherWeatherLocation.getLongitude()));
            }
        });
    }
}
