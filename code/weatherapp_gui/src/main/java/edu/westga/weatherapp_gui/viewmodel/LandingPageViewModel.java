package edu.westga.weatherapp_gui.viewmodel;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONObject;
import edu.westga.weatherapp_gui.model.CurrentWeatherInformation;
import edu.westga.weatherapp_gui.model.IpGrabber;
import edu.westga.weatherapp_gui.model.WeatherLocationSerializer;
import edu.westga.weatherapp_shared.model.WeatherLocation;
import edu.westga.weatherapp_shared.interfaces.CurrentWeatherDataRetriever;
import edu.westga.weatherapp_shared.interfaces.HourlyWeatherDataRetriever;
import edu.westga.weatherapp_shared.interfaces.LocationSearcher;
import edu.westga.weatherapp_shared.interfaces.WeatherIconRetriever;

/**
 * Defines the landing page view model class and contains all functionality for
 * the landing page view
 */
public class LandingPageViewModel {

    /**
     * The current weather data retriever
     */
    private CurrentWeatherDataRetriever weatherDataRetriever;

    /**
     * The weather icon retriever
     */
    private WeatherIconRetriever weatherIconRetriever;

    /**
     * The weather location searcher
     */
    private LocationSearcher weatherLocationSearcher;

    /**
     * The hourly weather data retriver
     */
    private HourlyWeatherDataRetriever hourlyWeatherDataRetriever;

    /**
     * The retrieved current weather data
     */
    private JSONObject currentWeatherData;

    /**
     * The retrieved hourly weather data
     */
    private JSONObject currentHourlyData;

    /**
     * The favorited weather locations
     */
    private Collection<WeatherLocation> favoritedWeatherLocations;

    /**
     * Creates an instance of the landing page view model. Binds to java rmi if no
     * data retrievers specified.
     * 
     * @param weatherDataRetriver - the weather data retriever
     * @param iconRetriever - the icon retriever
     * @param locationSearcher - the location searcher
     */
    public LandingPageViewModel(CurrentWeatherDataRetriever weatherDataRetriver, WeatherIconRetriever iconRetriever, LocationSearcher locationSearcher, HourlyWeatherDataRetriever hourlyWeatherDataRetriever) {
        if (weatherDataRetriver != null && iconRetriever != null && locationSearcher != null && hourlyWeatherDataRetriever != null) {
            this.weatherDataRetriever = weatherDataRetriver;
            this.weatherIconRetriever = iconRetriever;
            this.weatherLocationSearcher = locationSearcher;
            this.hourlyWeatherDataRetriever = hourlyWeatherDataRetriever;
        } else {
            try {
                this.weatherDataRetriever = (CurrentWeatherDataRetriever) Naming
                        .lookup("rmi://localhost:5000/current-weather");
                this.weatherIconRetriever = (WeatherIconRetriever) Naming.lookup("rmi://localhost:5000/weather-icons");
                this.weatherLocationSearcher = (LocationSearcher) Naming.lookup("rmi://localhost:5000/location-searcher");
                this.hourlyWeatherDataRetriever = (HourlyWeatherDataRetriever) Naming.lookup("rmi://localhost:5000/hourly-weather");
            } catch (Exception exception) {
                System.err.println("Error looking up java rmi binding");
            }
        }

        this.LoadFavoritedLocations();
    }

    /**
     * Gets the current weather data from the weather data retriever by city name
     * 
     * @param weatherLocation - the name of the city
     * @return the current weather data json object
     */
    public JSONObject GetWeatherDataByWeatherLocation(WeatherLocation weatherLocation) {
        if (weatherLocation == null) {
            throw new IllegalArgumentException("City cannot be null");
        }
        String state = weatherLocation.getState();
        String city = weatherLocation.getCity();
        String country = weatherLocation.getCountry();

        try {
            this.weatherDataRetriever.setUnitsOfMeasurement(CurrentWeatherInformation.getMeasurementUnits());
            if (state.equals("N/A")) {
                this.currentWeatherData = new JSONObject(this.weatherDataRetriever.GetDataByCityAndCountryCode(city, country));
            } else {
                this.currentWeatherData = new JSONObject(this.weatherDataRetriever.GetDataByCityAndStateCodeAndCountryCode(city, state, country));
            }
            CurrentWeatherInformation.setWeatherData(this.currentWeatherData);
            return this.currentWeatherData;
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Gets the current hourly weather forecast from the hourly weather data retriever by city location
     * 
     * @param weatherLocation - The location of the city
     * @param hours - the number of hours to gather data for
     * @return the hourly weather data json object
     */
    public JSONObject GetHourlyForecastDataByWeatherLocation(WeatherLocation weatherLocation, int hours) {
        if (weatherLocation == null) {
            throw new IllegalArgumentException("City cannot be null");
        }
        String state = weatherLocation.getState();
        String city = weatherLocation.getCity();
        String country = weatherLocation.getCountry();

        try {
            this.hourlyWeatherDataRetriever.setUnitsOfMeasurement(CurrentWeatherInformation.getMeasurementUnits());
            if (state.equals("N/A")) {
                this.currentHourlyData = new JSONObject(this.hourlyWeatherDataRetriever.GetDataByCityAndCountryCode(city, country, hours));
            } else {
                this.currentHourlyData = new JSONObject(this.hourlyWeatherDataRetriever.GetDataByCityAndStateCodeAndCountryCode(city, state, country, hours));
            }
            return this.currentHourlyData;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets the current location of the user based on their ip address
     * 
     * @return a weather location of the user's current location
     */
    public WeatherLocation GetCurrentLocation() {
        try {
            String ip = IpGrabber.GetCurrentIpAddress();
            WeatherLocation currentLocation = this.weatherLocationSearcher.getLocationByIP(ip);
            
            return currentLocation;
        } catch (RemoteException e) {
            return null;
        }
    }

    /**
     * Gets the autocompletion search results based on the given city name
     * 
     * @param city - the city name
     * @return a collection of weather locations
     */
    public Collection<WeatherLocation> GetLocationSearchResults(String city) {
        if (city == null) {
            throw new IllegalArgumentException("City cannot be null");
        }
        if (city.isEmpty()) {
            throw new IllegalArgumentException("City cannot be empty");
        }

        try {
            String ip = IpGrabber.GetCurrentIpAddress();
            WeatherLocation currentLocation = this.weatherLocationSearcher.getLocationByIP(ip);
            Collection<WeatherLocation> locations = this.weatherLocationSearcher.searchLocations(city, 10, currentLocation.getLatitude(), currentLocation.getLongitude());
            
            return locations;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets the current weather icon url from the current weather data. Must
     * retrieve weather data before calling
     * 
     * @return String - Current weather icon url
     */
    public String GetCurrentWeatherIcon() {
        if (this.currentWeatherData == null) {
            throw new IllegalArgumentException("No current weather data");
        }

        try {
            Object icon = this.currentWeatherData.getJSONArray("weather").getJSONObject(0).get("icon");
            String iconString = String.valueOf(icon);

            return this.weatherIconRetriever.GetWeatherIconUrlByIconId(iconString);
        } catch (RemoteException exception) {
            System.err.println("Remote Exception: Error retrieving weather icon url by icon id");
            return null;
        }
    }

    /**
     * Gets the current weather temperature from the current weather data. Must
     * retrieve weather data before calling
     * 
     * @return String - Current temperature
     */
    public String GetCurrentTemperature() {
        if (this.currentWeatherData == null) {
            throw new IllegalArgumentException("No current weather data");
        }

        Long temperature = Math.round(this.currentWeatherData.getJSONObject("main").getDouble("temp"));
        return String.valueOf(temperature);
    }

    /**
     * Gets the current weather wind speed from the current weather data. Must
     * retrieve weather data before calling
     * 
     * @return String - Current wind speed
     */
    public String GetCurrentWindSpeed() {
        if (this.currentWeatherData == null) {
            throw new IllegalArgumentException("No current weather data");
        }

        Long windSpeed = Math.round(this.currentWeatherData.getJSONObject("wind").getDouble("speed"));
        return String.valueOf(windSpeed);
    }

    /**
     * Gets the current weather humidity from the current weather data. Must
     * retrieve weather data before calling
     * 
     * @return String - Current humidity
     */
    public String GetCurrentHumidity() {
        if (this.currentWeatherData == null) {
            throw new IllegalArgumentException("No current weather data");
        }

        Long humidity = Math.round(this.currentWeatherData.getJSONObject("main").getDouble("humidity"));
        return String.valueOf(humidity);
    }

    /**
     * Gets the current weather description from the current weather data. Must
     * retrieve weather data before calling
     * 
     * @return String - Current weather description
     */
    public String GetCurrentWeatherDescription() {
        if (this.currentWeatherData == null) {
            throw new IllegalArgumentException("No current weather data");
        }

        Object descriptionObject = this.currentWeatherData.getJSONArray("weather").getJSONObject(0).get("main");
        return String.valueOf(descriptionObject);
    }

    /**
     * Updates the current weather data
     * 
     * @param weatherData - the new weather data
     */
    public void SetCurrentWeatherData(JSONObject weatherData) {
        if (weatherData == null) {
            throw new IllegalArgumentException("Weather data cannot be null");
        }
        this.currentWeatherData = weatherData;
    }

    /**
     * Removes the favorited location and saves the favorites to a file.
     * 
     * @param weatherLocation - the favorited location to remove
     */
    public void RemoveFavoritedLocation(WeatherLocation weatherLocation) {
        if (weatherLocation == null) {
            throw new IllegalArgumentException("Weather location cannot be null");
        }

        this.favoritedWeatherLocations.remove(weatherLocation);
        this.SaveFavoritedLocations();
    }

    /**
     * Adds the favorited location and saves the favorites to a file.
     * 
     * @param weatherLocation - the favorited location to add
     */
    public void AddFavoritedLocation(WeatherLocation weatherLocation) {
        if (weatherLocation == null) {
            throw new IllegalArgumentException("Weather location cannot be null");
        }

        this.favoritedWeatherLocations.add(weatherLocation);
        this.SaveFavoritedLocations();
    }

    /**
     * Gets the collection of favorited weather locations
     * 
     * @return the favorited weather locations
     */
    public Collection<WeatherLocation> GetFavoritedWeatherLocations() {
        return this.favoritedWeatherLocations;
    }

    /**
     * Checks if the given weather location is contained in the favorited locations list
     * 
     * @param weatherLocation - the given weather location
     * @return True if it is contained, false otherwise
     */
    public boolean FavoritesContainsWeatherLocation(WeatherLocation weatherLocation) {
        for (WeatherLocation currentLocation : favoritedWeatherLocations) {
            if (currentLocation.equals(weatherLocation)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the timezone for the searched location
     * 
     * @return the timezone
     */
    public Long GetTimezone() {
        if (this.currentHourlyData == null) {
            throw new IllegalArgumentException("No hourly weather data");
        }

        return Math.round(this.currentHourlyData.getJSONObject("city").getDouble("timezone"));
    }

    /**
     * Gets the day utc time for the specified hour
     * 
     * @param hour - the hour index
     * @return the hour utc time
     */
    public Long GetHourUtcDateTime(int hour) {
        if (this.currentHourlyData == null) {
            throw new IllegalArgumentException("No hourly weather data");
        }

        return Math.round(this.currentHourlyData.getJSONArray("list").getJSONObject(hour).getDouble("dt"));
    }

    /**
     * Gets the max temperature for the specified hour
     * 
     * @param hour - the hour index
     * @return the max temperature
     */
    public String GetHourTemperature(int hour) {
        if (this.currentHourlyData == null) {
            throw new IllegalArgumentException("No hourly weather data");
        }

        Long temperature = Math.round(
                this.currentHourlyData.getJSONArray("list").getJSONObject(hour).getJSONObject("main").getDouble("temp"));
        return String.valueOf(temperature);
    }

    /**
     * Gets the weather icon for the specified hour
     * 
     * @param hour - the hour index
     * @return the weather icon
     */
    public String GetDayWeatherIcon(int hour) {
        if (this.currentHourlyData == null) {
            throw new IllegalArgumentException("No hourly weather data");
        }

        try {
            Object icon = this.currentHourlyData.getJSONArray("list").getJSONObject(hour).getJSONArray("weather")
                    .getJSONObject(0).get("icon");
            String iconString = String.valueOf(icon);

            return this.weatherIconRetriever.GetWeatherIconUrlByIconId(iconString);
        } catch (RemoteException exception) {
            System.err.println("Remote Exception: Error retrieving weather icon url by icon id");
            return null;
        }
    }

    /**
     * Saves the collection of favorited weather locations to a file
     */
    private void SaveFavoritedLocations() {
        try {
            WeatherLocationSerializer weatherLocationSerializer = new WeatherLocationSerializer();
            weatherLocationSerializer.saveFavoritedLocationsToFile(this.favoritedWeatherLocations);
        } catch (IOException e) {
            System.err.println("IOException: Error saving favorited locations");
        }
    }

    /**
     * Loads the favorited locations from a file
     */
    private void LoadFavoritedLocations() {
        try {
            WeatherLocationSerializer weatherLocationSerializer = new WeatherLocationSerializer();
            this.favoritedWeatherLocations = weatherLocationSerializer.loadFavoritedLocationsFromFile();
        } catch (ClassNotFoundException | IOException e) {
            this.favoritedWeatherLocations = (Collection<WeatherLocation>) new ArrayList<WeatherLocation>();
        }
    }
}
