package edu.westga.weatherapp_gui.model;

import java.util.ArrayList;

import org.json.JSONObject;

import edu.westga.weatherapp_gui.view.DayForecastPane;
import edu.westga.weatherapp_gui.view.HourlyInfoPane;
import edu.westga.weatherapp_shared.enums.MeasurementUnits;
import edu.westga.weatherapp_shared.model.WeatherLocation;

/**
 * Class to hold weather information for use across multiple pages.
 * 
 * @author Michael Pavich
 */
public class CurrentWeatherInformation {

    /**
     * The most recently searched city
     */
    private static WeatherLocation weatherLocation = null;

    /**
     * The location the user is located at
     */
    private static WeatherLocation userLocation = null;

    /**
     * The most recently fetched weather data
     */
    private static JSONObject weatherData = null;

    /**
     * The current measurement units
     */
    private static MeasurementUnits measurementUnits = MeasurementUnits.Imperial;

    /**
     * The hourly info panes
     */
    private static ArrayList<HourlyInfoPane> hourlyInfoPanes;

    /**
     * The day forecast panes
     */
    private static ArrayList<DayForecastPane> dayForecastPanes;

    /**
     * Holds whether the app has finished gerabbing the user location
     */
    private static boolean finishedFirstLoadIpGrab;

    /**
     * Holds the current selected month for the statistical weather data
     */
    private static int statisticalMonthSelected;

    /**
     * Gets the current selected month for the statistical weather data
     * 
     * @return the current selected month for the statistical weather data
     */
    public static int getStatisticalMonthSelected() {
        return CurrentWeatherInformation.statisticalMonthSelected;
    }

    /**
     * Sets the current selected month for the statistical weather data
     * 
     * @param statisticalMonthSelected - the new selected month
     */
    public static void setStatisticalMonthSelected(int statisticalMonthSelected) {
        CurrentWeatherInformation.statisticalMonthSelected = statisticalMonthSelected;
    }

    /**
     * Gets the finished first load ip grab value
     * 
     * @return the finished first load ip grab
     */
    public static boolean isFinishedFirstLoadIpGrab() {
        return CurrentWeatherInformation.finishedFirstLoadIpGrab;
    }

    /**
     * Sets the finished first load ip grab value
     * 
     * @param value - the new value
     */
    public static void setFinishedFirstLoadIpGrab(boolean value) {
        CurrentWeatherInformation.finishedFirstLoadIpGrab = value;
    }

    /**
     * Gets the stored list of day forecast pane components
     * 
     * @return the day forecast panes
     */
    public static ArrayList<DayForecastPane> getDayForecastPanes() {
        return CurrentWeatherInformation.dayForecastPanes;
    }

    /**
     * Sets the stored list of day forecast pane components
     * 
     * @param panes - the new day forecast panes
     */
    public static void setDayForecastPanes(ArrayList<DayForecastPane> panes) {
        CurrentWeatherInformation.dayForecastPanes = panes;
    }
    
    /**
     * Gets the stored list of hourly info pane components
     * 
     * @return the hourly info panes
     */
    public static ArrayList<HourlyInfoPane> getHourlyInfoPanes() {
        return CurrentWeatherInformation.hourlyInfoPanes;
    }

    /**
     * Sets the stored list of hourly info panes to the given list
     * 
     * @param panes - the new list of panes
     */
    public static void setHourlyInfoPanes(ArrayList<HourlyInfoPane> panes) {
        CurrentWeatherInformation.hourlyInfoPanes = panes;
    }

    /**
     * Gets the most recently searched weather location
     * 
     * @return the weather location
     */
    public static WeatherLocation getWeatherLocation() {
        return CurrentWeatherInformation.weatherLocation;
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

        CurrentWeatherInformation.weatherLocation = weatherLocation;
    }

    /**
     * Gets the most recently fetched weather data
     * 
     * @return the weather data
     */
    public static JSONObject getWeatherData() {
        return CurrentWeatherInformation.weatherData;
    }

    /**
     * Sets the weather data to the specifed weather data
     * 
     * @param data - the new weather data
     */
    public static void setWeatherData(JSONObject data) {
        CurrentWeatherInformation.weatherData = data;
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

    /**
     * Sets the values of this class to default values.
     */
    public static void resetInfo() {
        weatherLocation = null;
        weatherData = null;
        measurementUnits = MeasurementUnits.Imperial;
        hourlyInfoPanes = null;
        dayForecastPanes = null;
        finishedFirstLoadIpGrab = false;
    }

    /**
     * Gets the user location
     * 
     * @return the user location
     */
    public static WeatherLocation getUserLocation() {
        return userLocation;
    }

    /**
     * Sets the user location to the specified location
     * 
     * @param userLocation - the new user location
     */
    public static void setUserLocation(WeatherLocation userLocation) {
        CurrentWeatherInformation.userLocation = userLocation;
    }
}
