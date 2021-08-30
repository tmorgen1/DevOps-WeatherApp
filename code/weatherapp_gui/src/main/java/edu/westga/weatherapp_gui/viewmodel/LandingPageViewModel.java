package edu.westga.weatherapp_gui.viewmodel;

import java.rmi.Naming;
import org.json.JSONObject;
import edu.westga.weatherapp_shared.WeatherDataRetriever;
import edu.westga.weatherapp_shared.WeatherIconRetriever;

/**
 * Defines the landing page view model class and contains all functionality for the landing page view
 */
public class LandingPageViewModel {
    
    private WeatherDataRetriever weatherDataRetriever;
    private WeatherIconRetriever weatherIconRetriever;
    private JSONObject currentWeatherData;

    /**
     * Creates an instance of the landing page view model
     */
    public LandingPageViewModel() {
        try {
            this.weatherDataRetriever = (WeatherDataRetriever) Naming.lookup("rmi://localhost:5000/current-weather");
            this.weatherIconRetriever = (WeatherIconRetriever) Naming.lookup("rmi://localhost:5000/weather-icons");
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }

    /**
     * Gets the current weather data from the weather data retriever by city name
     * 
     * @param city - the name of the city
     * @return True if successful, false otherwise
     */
    public boolean getWeatherDataByCity(String city) {
        try {
            this.currentWeatherData = new JSONObject(this.weatherDataRetriever.GetDataByCity(city));
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * Gets the current weather temperature from the current weather data
     * 
     * @return String - Current temperature
     */
    public String getCurrentTemperature() {
        if (this.currentWeatherData == null) {
            throw new IllegalArgumentException("No current weather data detected.");
        }

        Long temperature = Math.round(this.currentWeatherData.getJSONObject("main").getDouble("temp"));
        return String.valueOf(temperature);
    }

    /**
     * Gets the current weather description from the current weather data
     * 
     * @return String - Current weather description
     */
    public String getCurrentWeatherDescription() {
        if (this.currentWeatherData == null) {
            throw new IllegalArgumentException("No current weather data detected.");
        }

        Object descriptionObject = this.currentWeatherData.getJSONArray("weather").getJSONObject(0).get("main");
        return String.valueOf(descriptionObject);
    }

    /**
     * Gets the current weather icon url from the current weather data
     * 
     * @return String - Current weather icon url
     */
    public String getCurrentWeatherIcon() {
        if (this.currentWeatherData == null) {
            throw new IllegalArgumentException("No current weather data detected.");
        }

        Object icon = this.currentWeatherData.getJSONArray("weather").getJSONObject(0).get("icon");
        String iconString = String.valueOf(icon);
        try {
            return this.weatherIconRetriever.GetWeatherIconUrlByIconId(iconString);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        return null;
    }
}
