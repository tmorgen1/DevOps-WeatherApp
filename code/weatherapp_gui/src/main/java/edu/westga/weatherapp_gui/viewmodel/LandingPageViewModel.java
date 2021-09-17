package edu.westga.weatherapp_gui.viewmodel;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import org.json.JSONObject;
import edu.westga.weatherapp_gui.model.CurrentWeatherInformation;
import edu.westga.weatherapp_shared.model.WeatherLocation;
import edu.westga.weatherapp_shared.interfaces.CurrentWeatherDataRetriever;
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
     * The retrieved current weather data
     */
    private JSONObject currentWeatherData;

    /**
     * Creates an instance of the landing page view model. Binds to java rmi if no
     * data retrievers specified.
     * 
     * @param weatherDataRetriver - the weather data retriever
     * @param iconRetriever - the icon retriever
     * @param locationSearcher - the location searcher
     */
    public LandingPageViewModel(CurrentWeatherDataRetriever weatherDataRetriver, WeatherIconRetriever iconRetriever, LocationSearcher locationSearcher) {
        if (weatherDataRetriver != null && iconRetriever != null && locationSearcher != null) {
            this.weatherDataRetriever = weatherDataRetriver;
            this.weatherIconRetriever = iconRetriever;
            this.weatherLocationSearcher = locationSearcher;
        } else {
            try {
                this.weatherDataRetriever = (CurrentWeatherDataRetriever) Naming
                        .lookup("rmi://localhost:5000/current-weather");
                this.weatherIconRetriever = (WeatherIconRetriever) Naming.lookup("rmi://localhost:5000/weather-icons");
                this.weatherLocationSearcher = (LocationSearcher) Naming.lookup("rmi://localhost:5000/location-searcher");
            } catch (Exception exception) {
                System.err.println("Error looking up java rmi binding");
            }
        }
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
            Collection<WeatherLocation> locations = this.weatherLocationSearcher.searchLocations(city, 10);
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
}
