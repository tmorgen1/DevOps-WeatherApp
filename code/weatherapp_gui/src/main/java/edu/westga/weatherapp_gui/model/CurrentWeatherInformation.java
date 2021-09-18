package edu.westga.weatherapp_gui.model;

import org.json.JSONObject;

import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import edu.westga.weatherapp_shared.model.WeatherLocation;

/**
 * Class to hold weather information for use across multiple pages.
 */
public class CurrentWeatherInformation {

    /**
     * The most recently searched city
     */
    private static WeatherLocation WeatherLocation = null;

    /**
     * The most recently fetched weather data
     */
    private static JSONObject WeatherData = null;

    /**
     * The current measurement units
     */
    private static MeasurementUnits measurementUnits = MeasurementUnits.Imperial;

    /**
     * Gets the most recently searched weather location
     * 
     * @return - the weather location
     */
    public static WeatherLocation getWeatherLocation() {
        return CurrentWeatherInformation.WeatherLocation;
    }

    /**
     * Sets the weather location to the specified
     * 
     * @param weatherLocation - the new weather location
     */
    public static void setWeatherLocation(WeatherLocation weatherLocation) {
        if (weatherLocation == null) {
            throw new IllegalArgumentException("City cannot be null");
        }

        CurrentWeatherInformation.WeatherLocation = weatherLocation;
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
        CurrentWeatherInformation.WeatherData = data;
    }

    /**
     * Gets the current measurement units
     * 
     * @return the measurement units
     */
    public static MeasurementUnits getMeasurementUnits() {
        return CurrentWeatherInformation.measurementUnits;
    }

    /**
     * Sets the current measurement units
     * 
     * @param units - the new measurement units
     */
    public static void setMeasurementUnits(MeasurementUnits units) {
        CurrentWeatherInformation.measurementUnits = units;
    }
}
