package edu.westga.weatherapp_gui.model;

import org.json.JSONObject;

/**
 * Class to hold weather information for use across multiple pages.
 */
public class CurrentWeatherInformation {

    /**
     * The most recently searched city name
     */
    private static String CityName = null;

    /**
     * The most recently fetched weather data
     */
    private static JSONObject WeatherData = null;

    /**
     * Gets the most recently searched city name
     * 
     * @return - the city name
     */
    public static String getCityName() {
        return CurrentWeatherInformation.CityName;
    }

    /**
     * Sets the city name to the specified name
     * 
     * @param city - the new city name
     */
    public static void setCityName(String city) {
        if (city == null) {
            throw new IllegalArgumentException("City cannot be null");
        }
        if (city.isEmpty()) {
            throw new IllegalArgumentException("City cannot be empty");
        }

        CurrentWeatherInformation.CityName = city;
    }

    /**
     * Gets the most recently fetched weather data
     * 
     * @return - the weather data
     */
    public static JSONObject getWeatherData() {
        return CurrentWeatherInformation.WeatherData;
    }

    /**
     * Sets the weather data to the specifed weather data
     * 
     * @param data - the new weather data
     */
    public static void setWeatherData(JSONObject data) {
        if (data == null) {
            throw new IllegalArgumentException("Weather data cannot be null");
        }

        CurrentWeatherInformation.WeatherData = data;
    }
}
