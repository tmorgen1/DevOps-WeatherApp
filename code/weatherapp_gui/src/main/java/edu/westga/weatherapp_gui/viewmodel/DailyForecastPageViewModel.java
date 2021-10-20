package edu.westga.weatherapp_gui.viewmodel;

import java.rmi.Naming;
import java.rmi.RemoteException;

import org.json.JSONObject;

import edu.westga.weatherapp_gui.model.CurrentWeatherInformation;
import edu.westga.weatherapp_shared.interfaces.DailyWeatherDataRetriever;
import edu.westga.weatherapp_shared.interfaces.WeatherIconRetriever;
import edu.westga.weatherapp_shared.model.WeatherLocation;

/**
 * Defines the daily forecast page view model class and contains all
 * functionality for the daily forecast page view
 * 
 * @author Michael Pavich
 */
public class DailyForecastPageViewModel {

    /**
     * No daily weather data error message
     */
    private static final String NO_DAILY_WEATHER_DATA_ERROR_MESSAGE = "No daily weather data";

    /**
     * The daily weather data retreiver
     */
    private DailyWeatherDataRetriever dailyWeatherDataRetriever;

    /**
     * The weather icon retriever
     */
    private WeatherIconRetriever weatherIconRetriever;

    /**
     * The retrieved daily weather data
     */
    private JSONObject dailyWeatherData;

    /**
     * Creates a new instance of the daily forecast page view model. Binds to java
     * rmi if no data retrievers specified.
     * 
     * @param dataRetriever - the daily weather data retriever
     * @param iconRetriever - the weather icon data retriever
     */
    public DailyForecastPageViewModel(DailyWeatherDataRetriever dataRetriever, WeatherIconRetriever iconRetriever) {
        if (dataRetriever != null && iconRetriever != null) {
            this.dailyWeatherDataRetriever = dataRetriever;
            this.weatherIconRetriever = iconRetriever;
        } else {
            try {
                this.dailyWeatherDataRetriever = (DailyWeatherDataRetriever) Naming
                        .lookup("rmi://localhost:5000/daily-weather");
                this.weatherIconRetriever = (WeatherIconRetriever) Naming.lookup("rmi://localhost:5000/weather-icons");
            } catch (Exception exception) {
                System.err.println("Error looking up java rmi binding");
            }
        }
    }

    /**
     * Gets the daily weather data for the given amount of days from the weather
     * data retriever by city name
     * 
     * @param weatherLocation - the weather location
     * @param days - the number of days to retrieve
     * @return the daily weather json data
     */
    public JSONObject getWeatherDataByWeatherLocation(WeatherLocation weatherLocation, int days) {
        if (weatherLocation == null) {
            throw new IllegalArgumentException("City cannot be null");
        }
        String state = weatherLocation.getState();
        String city = weatherLocation.getCity();
        String country = weatherLocation.getCountry();

        try {
            this.dailyWeatherDataRetriever.setUnitsOfMeasurement(CurrentWeatherInformation.getMeasurementUnits());
            if (state.equals("N/A")) {
                this.dailyWeatherData = new JSONObject(this.dailyWeatherDataRetriever.getDataByCityAndCountryCode(city, country, days));
            } else {
                this.dailyWeatherData = new JSONObject(this.dailyWeatherDataRetriever.getDataByCityAndStateCodeAndCountryCode(city, state, country, days));
            }
            return this.dailyWeatherData;
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * Gets the weather icon for the specified day
     * 
     * @param day - the day index
     * @return the weather icon
     */
    public String getDayWeatherIcon(int day) {
        if (this.dailyWeatherData == null) {
            throw new IllegalArgumentException(NO_DAILY_WEATHER_DATA_ERROR_MESSAGE);
        }

        try {
            Object icon = this.dailyWeatherData.getJSONArray("list").getJSONObject(day).getJSONArray("weather")
                    .getJSONObject(0).get("icon");
            String iconString = String.valueOf(icon);

            return this.weatherIconRetriever.getWeatherIconUrlByIconId(iconString);
        } catch (RemoteException exception) {
            System.err.println("Remote Exception: Error retrieving weather icon url by icon id");
            return null;
        }
    }

    /**
     * Gets the max temperature for the specified day
     * 
     * @param day - the day index
     * @return the max temperature
     */
    public String getDayMaxTemperature(int day) {
        if (this.dailyWeatherData == null) {
            throw new IllegalArgumentException(NO_DAILY_WEATHER_DATA_ERROR_MESSAGE);
        }

        Long temperature = Math.round(
                this.dailyWeatherData.getJSONArray("list").getJSONObject(day).getJSONObject("temp").getDouble("max"));
        return String.valueOf(temperature);
    }

    /**
     * Gets the min temperature for the specified day
     * 
     * @param day - the day index
     * @return the min temperature
     */
    public String getDayMinTemperature(int day) {
        if (this.dailyWeatherData == null) {
            throw new IllegalArgumentException(NO_DAILY_WEATHER_DATA_ERROR_MESSAGE);
        }

        Long temperature = Math.round(
                this.dailyWeatherData.getJSONArray("list").getJSONObject(day).getJSONObject("temp").getDouble("min"));
        return String.valueOf(temperature);
    }

    /**
     * Gets the day utc time for the specified day
     * 
     * @param day - the day index
     * @return the day utc time
     */
    public Long getDayUtcDateTime(int day) {
        if (this.dailyWeatherData == null) {
            throw new IllegalArgumentException(NO_DAILY_WEATHER_DATA_ERROR_MESSAGE);
        }

        return Math.round(this.dailyWeatherData.getJSONArray("list").getJSONObject(day).getDouble("dt"));
    }

    /**
     * Gets the timezone for the searched location
     * 
     * @return the timezone
     */
    public Long getTimezone() {
        if (this.dailyWeatherData == null) {
            throw new IllegalArgumentException(NO_DAILY_WEATHER_DATA_ERROR_MESSAGE);
        }

        return Math.round(this.dailyWeatherData.getJSONObject("city").getDouble("timezone"));
    }
}
